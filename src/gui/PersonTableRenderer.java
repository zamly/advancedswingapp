package gui;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import model.EmploymentCategory;

public class PersonTableRenderer implements TableCellRenderer {

	private JComboBox combo;
	
	public PersonTableRenderer() {
		combo = new JComboBox(EmploymentCategory.values());
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		combo.setSelectedItem(value);
		return combo;
	}

}
