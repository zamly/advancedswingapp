package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.Controller;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	// private TextPanel textPanel;
	private Toolbar toolbar;
	private FormPanel formPanel;
	private TablePanel tablePanel;
	private JFileChooser fileChooser;
	private PrefsDialog dialog;
	private JSplitPane splitPane;
	private JTabbedPane tabbedPane;
	private MessagePanel messagePanel;

	private Preferences prefs;

	private Controller controller;

	public MainFrame() {
		super("JAVA Swing Application");

		setLayout(new BorderLayout());

		toolbar = new Toolbar();
		// textPanel = new TextPanel();
		formPanel = new FormPanel();
		tablePanel = new TablePanel();
		dialog = new PrefsDialog(this);
		messagePanel = new MessagePanel(this);

		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Person Database", tablePanel);
		tabbedPane.addTab("Messages", messagePanel);

		// adds formpanel on the left and tablepanel on the right.
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel,
				tabbedPane);
		splitPane.setOneTouchExpandable(true);

		prefs = Preferences.userRoot().node("db");

		controller = new Controller();

		// Passing around the list of peoples to the JTable's model.
		tablePanel.setData(controller.getPeople());

		tablePanel.setPersonTableListener(new PersonTableListener() {
			public void rowDeleted(int row) {
				controller.removePerson(row);
			}
		});

		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new PersonFileFilter());

		setJMenuBar(createMenuBar());

		toolbar.setToolbarListener(new ToolbarListener() {

			public void saveEventOccurred() {

				dbConnect();
				try {
					controller.save();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this,
							"Cannot save to the DB", "DB Save Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			public void refreshEventOccurred() {
				refresh();
			}
		});

		formPanel.setFormListener(new FormListener() {
			public void formEventOccurred(FormEvent e) {
				// String name = e.getName();
				// String occ = e.getOccupation();
				// int age = e.getAgeCategory();
				// String emp = e.getEmployment();
				// String tax = e.getTaxId();
				// String gender = e.getGender();

				// textPanel.appendText(name+": "+occ+": "+age+": "+emp+": "+tax+": "+gender+"\n");
				controller.addPerson(e);
				tablePanel.refresh();
			}
		});

		dialog.setPrefsListener(new PrefsListener() {
			public void preferencesSet(String username, String password,
					int port) {
				// System.out.println(username + password);
				prefs.put("username", username);
				prefs.put("password", password);
				prefs.putInt("port", port);

				try {
					controller.configure(port, username, password);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(MainFrame.this,
							"Unable to connect",
							"The username/password does not match.",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		String username = prefs.get("username", "");
		String pass = prefs.get("password", "");
		int port = prefs.getInt("port", 3306);
		dialog.setDefaults(username, pass, port);

		try {
			controller.configure(port, username, pass);
			// System.out.println(username+ pass);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(MainFrame.this,
					"Unable to establish DB Connection",
					"The username/password does not match.",
					JOptionPane.ERROR_MESSAGE);
		}

		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index = tabbedPane.getSelectedIndex();

				if (index == 1) {
					messagePanel.refresh();
				}
			}
		});

		addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				System.out.println("Window Closing");
				controller.disconnect();
				System.out.println("Disconnected from Database");
				dispose();
				System.gc();
			}

			public void windowOpened(WindowEvent e) {
			}

			public void windowClosed(WindowEvent e) {
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowDeiconified(WindowEvent e) {
			}

			public void windowActivated(WindowEvent e) {
			}

			public void windowDeactivated(WindowEvent e) {
			}

		});

		// add(toolbar, BorderLayout.NORTH);
		/**
		 * to add toolbars as dragable panel in the gui
		 */
		add(toolbar, BorderLayout.PAGE_START);
		// add(textPanel, BorderLayout.CENTER);

		// add(tablePanel, BorderLayout.CENTER);
		// add(formPanel, BorderLayout.WEST);
		// it adds the split pane into the center of the layout.
		add(splitPane, BorderLayout.CENTER);

		setMinimumSize(new Dimension(600, 450));
		setSize(950, 500);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		refresh();
		
		setVisible(true);
	}

	public void dbConnect() {
		try {
			controller.connect();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.this,
					"Cannot connet to the DB", "DB Connection Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void refresh() {
		dbConnect();
		try {
			controller.load();
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(MainFrame.this,
					"Cannot Load from the DB:\n" + e.getMessage(),
					"DB Refresh Error", JOptionPane.ERROR_MESSAGE);
		}
		tablePanel.refresh();
	}
	

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileJMenu = new JMenu("File");
		JMenuItem expDataItem = new JMenuItem("Export Data...");
		JMenuItem impDataItem = new JMenuItem("Import Data...");
		JMenuItem exit = new JMenuItem("Exit");
		fileJMenu.add(expDataItem);
		fileJMenu.add(impDataItem);
		fileJMenu.addSeparator();
		fileJMenu.add(exit);

		JMenu windowJMenu = new JMenu("Window");
		JMenu subMenu = new JMenu("Show");
		JMenuItem showForm = new JCheckBoxMenuItem("Person Form");
		JMenuItem prefs = new JMenuItem("Preferences");
		showForm.setSelected(true);
		subMenu.add(showForm);
		windowJMenu.add(subMenu);
		windowJMenu.add(prefs);

		menuBar.add(fileJMenu);
		menuBar.add(windowJMenu);

		impDataItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.loadFromFile(fileChooser.getSelectedFile());
						tablePanel.refresh();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainFrame.this,
								"Could not load data from file", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		expDataItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.saveToFile(fileChooser.getSelectedFile());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainFrame.this,
								"Could not Save data to file", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		showForm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem check = (JCheckBoxMenuItem) e.getSource();
				if (check.isSelected()) {
					splitPane.setDividerLocation((int) formPanel
							.getMinimumSize().getWidth());
				}
				formPanel.setVisible(check.isSelected());
			}
		});

		prefs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(true);
			}
		});

		fileJMenu.setMnemonic(KeyEvent.VK_F);
		exit.setMnemonic(KeyEvent.VK_X);

		prefs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.CTRL_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));
		impDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				ActionEvent.CTRL_MASK));

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// String uname = JOptionPane.showInputDialog(
				// MainFrame.this,
				// "Enter Username",
				// "Username",
				// JOptionPane.OK_CANCEL_OPTION|JOptionPane.QUESTION_MESSAGE);

				int action = JOptionPane.showConfirmDialog(MainFrame.this,
						"Do you really need to exit?", "Confirm Exit",
						JOptionPane.OK_CANCEL_OPTION);
				if (action == JOptionPane.OK_OPTION) {
					// System.exit(0);
					WindowListener[] wl = getWindowListeners();

					for (WindowListener lis : wl) {
						lis.windowClosing(new WindowEvent(MainFrame.this, 0));
					}
				}

			}
		});

		return menuBar;
	}

}
