package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import model.Message;
import controller.MessageServer;

class ServerInfo {
	private String name;
	private int id;
	private boolean checked;
	
	public ServerInfo(String name, int id, boolean checked) {
		this.name = name;
		this.id = id;
		this.checked = checked;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public String toString() {
		return name;
	}
}

public class MessagePanel extends JPanel implements ProgressDialogListener{
	
	private JTree serverTree;
	//private DefaultTreeCellRenderer treeCellRenderer;
	private ServerTreeCellRenderer treeCellRenderer;
	
	private ServerTreeCellEditor treeCellEditor;
	
	private Set<Integer> selectedServers;
	private MessageServer messageServer;
	
	private ProgressDialog progressDialog;
	
	SwingWorker<List<Message>, Integer> worker;
	
	private TextPanel textPanel;
	/**
	 * JList requires DefualtListModel object in its constructor.
	 */
	private JList messageList;
	private JSplitPane upperPane;
	private JSplitPane lowerPane;
	
	private DefaultListModel messageListModel;
	
	public MessagePanel(JFrame parent) {
		setLayout(new BorderLayout());
		
		messageListModel = new DefaultListModel();
		
		selectedServers = new TreeSet<Integer>();
		selectedServers.add(0);
		selectedServers.add(1);
		selectedServers.add(3);
		messageServer = new MessageServer();
		
		progressDialog = new ProgressDialog(parent, "Messages Downloading...");
		progressDialog.setProgressDialogListener(this);
		
//		treeCellRenderer = new DefaultTreeCellRenderer();
//		treeCellRenderer.setLeafIcon(Util.createIcon("/images/149407.gif"));
//		treeCellRenderer.setOpenIcon(Util.createIcon("/images/147347.gif"));
//		treeCellRenderer.setClosedIcon(Util.createIcon("/images/145477.gif"));
		treeCellRenderer = new ServerTreeCellRenderer();
		treeCellEditor = new ServerTreeCellEditor();
		
		serverTree = new JTree(createTree());
		serverTree.setCellRenderer(treeCellRenderer);
		serverTree.setCellEditor(treeCellEditor);
		serverTree.setEditable(true);
		
		//restriction to select only one node at a time
		serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
//		serverTree.addTreeSelectionListener(new TreeSelectionListener() {
//			public void valueChanged(TreeSelectionEvent e) {
//				DefaultMutableTreeNode node =  (DefaultMutableTreeNode) serverTree.getLastSelectedPathComponent();
//				
//				Object userObject = node.getUserObject();
//				if (userObject instanceof ServerInfo) {
//					int id = ((ServerInfo) userObject).getId();
//					System.out.println(id);
//				}
//			}
//		});
		
		messageServer.setSelectedServers(selectedServers);
				
		treeCellEditor.addCellEditorListener(new CellEditorListener() {
			public void editingStopped(ChangeEvent e) {
				ServerInfo info = (ServerInfo) treeCellEditor.getCellEditorValue();
				//System.out.println(info+" "+info.isChecked());
				
				int serverId = info.getId();
				if (info.isChecked()) {
					selectedServers.add(serverId);
				} else {
					selectedServers.remove(serverId);
				}
				
				messageServer.setSelectedServers(selectedServers);
				
				retrieveMessages();
			}
			
			public void editingCanceled(ChangeEvent e) {
				
			}
		});
		
		
		textPanel = new TextPanel();
		
		messageList = new JList(messageListModel);
		
		/**
		 * custom list renderer for the list
		 * Try without it.
		 */
		messageList.setCellRenderer(new MessageListRenderer());
		
		/**
		 * Listener for message list selection
		 */
		messageList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Message message = (Message) messageList.getSelectedValue();
				
				textPanel.setText(message.getContents());
			}
		});
		
		
		lowerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(messageList), textPanel);
		upperPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(serverTree),lowerPane);

		textPanel.setMinimumSize(new Dimension(10, 150));
		messageList.setMinimumSize(new Dimension(10, 100));
		
		upperPane.setResizeWeight(0.5);
		lowerPane.setResizeWeight(0.5);
		//add(new JScrollPane(serverTree), BorderLayout.CENTER);
		add(upperPane, BorderLayout.CENTER);
	}
	
	
	private DefaultMutableTreeNode createTree() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Server");
		
		DefaultMutableTreeNode branchOne = new DefaultMutableTreeNode("USA");
		DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(new ServerInfo("New York", 0, selectedServers.contains(0)));
		DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(new ServerInfo("Boston",1, selectedServers.contains(1)));
		DefaultMutableTreeNode server3 = new DefaultMutableTreeNode(new ServerInfo("Los Angeles", 2, selectedServers.contains(2)));
		branchOne.add(server1);
		branchOne.add(server2);
		branchOne.add(server3);
		
		DefaultMutableTreeNode branchTwo = new DefaultMutableTreeNode("UK");
		DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(new ServerInfo("London",3, selectedServers.contains(3)));
		DefaultMutableTreeNode server5 = new DefaultMutableTreeNode(new ServerInfo("Edinburgh",4, selectedServers.contains(4)));
		branchTwo.add(server4);
		branchTwo.add(server5);
		
		top.add(branchOne);
		top.add(branchTwo);
		
		return top;
	}
	
	
	public void refresh() {
		retrieveMessages();
	}
	
	
	public void retrieveMessages() {
		
		System.out.println("Messages Waiting: "+messageServer.getMessageCount());
		progressDialog.setMaximum(messageServer.getMessageCount());
		
		
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				progressDialog.setVisible(true);
//			}
//		});
		progressDialog.setVisible(true);
		
		worker = new SwingWorker<List<Message>, Integer>() {

			@Override
			protected void process(List<Integer> counts) {
				int retrieved = counts.get(counts.size()-1);
				//System.out.println("Got: "+retrieved+" messages.");
				progressDialog.setValue(retrieved);
			}

			@Override
			protected void done() {

				progressDialog.setVisible(false);
				if (isCancelled()) {
					return;
				}
				
				try {
					List<Message> retMessages = get();
					//System.out.println("Got Total: "+ retMessages.size());
				
//					messageListModel.removeAllElements();
//					for(Message message: retMessages) {
//						messageListModel.addElement(message.getTitle());
//					}
					
					messageList.setSelectedIndex(0);
					
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//progressDialog.setVisible(false);
			}

			@Override
			protected List<Message> doInBackground() throws Exception {
				List<Message> retrieveMessages = new ArrayList<Message>();
				int count = 0;
				
				messageListModel.removeAllElements();
				for(Message message: messageServer) {
					
					if(isCancelled()) break;
					
					messageListModel.addElement(message);
					//System.out.println(message.getTitle());
					retrieveMessages.add(message);
					count++;
					publish(count);
				}
				return retrieveMessages;
			}
			
		};
		
		worker.execute();
	}


	@Override
	public void progressDialogCanceled() {
		//System.out.println("Cancelled clicked..");
		if (worker != null) {
			worker.cancel(true);
		}
	}
	
}


