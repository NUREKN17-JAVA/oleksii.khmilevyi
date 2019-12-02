package ua.nure.kn.khmilevoi.usermanagement.gui;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.table.AbstractTableModel;

import ua.nure.kn.khmilevoi.usermanagement.User;

public class UserTableModel extends AbstractTableModel {

	private static final String[] COLUMN_NAMES = { "ID", "Name", "LastName" };
	private static final Class[] COLUMN_CLASSES = { Long.class, String.class, String.class };
	private List users = null;

	public UserTableModel(Collection users) {
		this.users = new ArrayList(users);
	}

	public int getRowCount() {
		return this.users.size();
	}

	public int getColumnCount() {
		return UserTableModel.COLUMN_NAMES.length;
	}

	public Class getColumnClass(int columnIndex) {
		return UserTableModel.COLUMN_CLASSES[columnIndex];
	}

	public String getColumnName(int columnIndex) {
		return UserTableModel.COLUMN_NAMES[columnIndex];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		User user = (User) this.users.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return user.getId();
		case 1:
			return user.getFirstName();
		case 2:
			return user.getLastName();
		}
		return null;
	}

}
