package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import model.EmploymentCategory;

public class PersonTableEditor extends AbstractCellEditor implements TableCellEditor{

	private JComboBox combo;
	
	public PersonTableEditor() {
		combo = new JComboBox(EmploymentCategory.values());
	}
	
	@Override
	public Object getCellEditorValue() {
		return combo.getSelectedItem();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		combo.setSelectedItem(value);
		
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
		
		return combo;
	}

	@Override
	public boolean isCellEditable(EventObject e) {
		// TODO Auto-generated method stub
		return true;
	}
	 

}
