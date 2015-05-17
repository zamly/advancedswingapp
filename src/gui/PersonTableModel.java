package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.EmploymentCategory;
import model.Person;

public class PersonTableModel extends AbstractTableModel {

	private List<Person> db;

	private String[] colNames = { "ID", "Name", "Occupation", "AgeCategory",
			"Employment", "Gender", "US Citizen", "Tax ID" };

	public void setData(List<Person> db) {
		this.db = db;
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return colNames[column];
	}

	@Override
	public int getRowCount() {
		return db.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Person person = db.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return person.getId();
		case 1:
			return person.getName();
		case 2:
			return person.getOccupation();
		case 3:
			return person.getAgeCategory();
		case 4:
			return person.getEmployment();
		case 5:
			return person.getGender();
		case 6:
			return person.isCitizen();
		case 7:
			return person.getTaxId();
		}
		return null;
	}

	/**
	 * For editable cells this particular instance the column one is allowed to
	 * be edit.
	 */
	@Override
	public boolean isCellEditable(int row, int col) {

		switch (col) {
		case 1:
			return true;
		case 4:
			return true;
		case 6:
			return true;
		default:
			return false;
		}
	}

	@Override
	public void setValueAt(Object aValue, int row, int col) {

		if (db == null)
			return;
		Person person = db.get(row);

		switch (col) {
		case 1:
			person.setName((String) aValue);
			break;
		case 4:
			person.setEmployment((EmploymentCategory) aValue);
			break;
		case 6:
			person.setCitizen((boolean) aValue);
			break;
		default:
			break;
		}
	}

	@Override
	public Class<?> getColumnClass(int col) {
		switch (col) {
		case 0:
			return Integer.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case 3:
			return String.class;
		case 4:
			return EmploymentCategory.class;
		case 5:
			return String.class;
		case 6:
			return Boolean.class;
		case 7:
			return String.class;
		default:
			return null;
		}
	}

}
