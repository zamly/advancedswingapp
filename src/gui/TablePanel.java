package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.EmploymentCategory;
import model.Person;

public class TablePanel extends JPanel {
	
	private JTable table;
	private PersonTableModel personTableModel;
	private JPopupMenu popup;
	private PersonTableListener listener;
	
	public TablePanel() {
		personTableModel = new PersonTableModel();
		table = new JTable(personTableModel);
		popup = new JPopupMenu();
		
		JMenuItem remItem = new JMenuItem("Delete Item");
		popup.add(remItem);
		
		table.setDefaultRenderer(EmploymentCategory.class, new PersonTableRenderer());
		table.setDefaultEditor(EmploymentCategory.class, new PersonTableEditor());
		table.setRowHeight(20);
		
		/**
		 * with mouseadapter class you can overide the 
		 * methods you only want.
		 */
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				
				//this method selects the entire row in the table
				table.getSelectionModel().setSelectionInterval(row, row);
				
				if (e.getButton() == MouseEvent.BUTTON3) {
					popup.show(table, e.getX(), e.getY());
				}
			}
		});
		
		remItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (listener != null) {
					listener.rowDeleted(row);
					personTableModel.fireTableRowsDeleted(row, row);
				}
			}
		});
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	public void setPersonTableListener(PersonTableListener listener) {
		this.listener = listener;
	}
	
	public void setData(List<Person> db) {
		personTableModel.setData(db);
	}

	public void refresh() {
		personTableModel.fireTableDataChanged();
	}

}
