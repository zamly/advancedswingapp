package gui;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

public class ServerTreeCellEditor extends AbstractCellEditor implements TreeCellEditor {

	private ServerTreeCellRenderer treeCellRenderer;
	private JCheckBox checkBox;
	private ServerInfo info;
	
	public ServerTreeCellEditor() {
		treeCellRenderer = new ServerTreeCellRenderer();
	}
	
	public Object getCellEditorValue() {
		info.setChecked(checkBox.isSelected());
		return info;
	}

	public Component getTreeCellEditorComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row) {
		
		Component component = treeCellRenderer.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, true);

		if (leaf) {
			
			DefaultMutableTreeNode treeNode =  (DefaultMutableTreeNode) value;
			info = (ServerInfo) treeNode.getUserObject();
			
			checkBox = (JCheckBox) component;
			checkBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					fireEditingStopped();
					checkBox.removeItemListener(this);
				}
			});
		}
		return component;
	}

	public boolean isCellEditable(EventObject event) {
		if (!(event instanceof MouseEvent)) return false;
		
		MouseEvent mouseEvent = (MouseEvent) event;
		JTree jTree = (JTree) event.getSource();
		
		TreePath treePath = jTree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
		if (treePath == null) return false;
		
		Object lastComponent = treePath.getLastPathComponent();
		if (lastComponent == null) {
			return false;
		}
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) lastComponent;

		return node.isLeaf();
	}

}
