package ua.nure.kn.khmilevoi.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ua.nure.kn.khmilevoi.usermanagement.User;
import ua.nure.kn.khmilevoi.usermanagement.db.DatabaseException;
import ua.nure.kn.khmilevoi.usermanagement.utill.Messages;

public class BrowsePanel extends JPanel implements ActionListener {

	private MainFrame parent;
	private JPanel buttonPanel;
	private JScrollPane tablePanel;
	private JTable userTable;

	private JButton addButton;
	private JButton editButton;
	private JButton detailButton;
	private JButton deleteButton;

	public BrowsePanel(MainFrame mainFrame) {
		parent = mainFrame;
		initialize();
	}

	private void initialize() {
		this.setName("browsePanel"); //$NON-NLS-1$
		this.setLayout(new BorderLayout());
		this.add(this.getTablePanel(), BorderLayout.CENTER);
		this.add(this.getButtonsPanel(), BorderLayout.SOUTH);
	}

	private JPanel getButtonsPanel() {
		if (this.buttonPanel == null) {
			this.buttonPanel = new JPanel();
			this.buttonPanel.add(this.getAddButton(), null);
			this.buttonPanel.add(this.getEditButton(), null);
			this.buttonPanel.add(this.getDeleteButton(), null);
			this.buttonPanel.add(this.getDetailsButton(), null);
		}
		return this.buttonPanel;
	}

	private JButton getDetailsButton() {
		if (this.detailButton == null) {
			this.detailButton = new JButton();
			this.detailButton.setText(Messages.getString("BrowsePanel.details")); //$NON-NLS-1$
			this.detailButton.setName("detailsButton"); //$NON-NLS-1$
			this.detailButton.setActionCommand("details"); //$NON-NLS-1$
			this.detailButton.addActionListener(this);
		}
		return this.detailButton;
	}

	private JButton getDeleteButton() {
		if (this.deleteButton == null) {
			this.deleteButton = new JButton();
			this.deleteButton.setText(Messages.getString("BrowsePanel.delete")); //$NON-NLS-1$
			this.deleteButton.setName("deleteButton"); //$NON-NLS-1$
			this.deleteButton.setActionCommand("delete"); //$NON-NLS-1$
			this.deleteButton.addActionListener(this);
		}
		return this.deleteButton;
	}

	private JButton getEditButton() {
		if (this.editButton == null) {
			this.editButton = new JButton();
			this.editButton.setText(Messages.getString("BrowsePanel.edit")); //$NON-NLS-1$
			this.editButton.setName("editButton"); //$NON-NLS-1$
			this.editButton.setActionCommand("edit"); //$NON-NLS-1$
			this.editButton.addActionListener(this);
		}
		return this.editButton;
	}

	private JButton getAddButton() {
		if (this.addButton == null) {
			this.addButton = new JButton();
			this.addButton.setText(Messages.getString("BrowsePanel.add")); //$NON-NLS-1$
			this.addButton.setName("addButton"); //$NON-NLS-1$
			this.addButton.setActionCommand("add"); //$NON-NLS-1$
			this.addButton.addActionListener(this);
		}
		return this.addButton;
	}

	private JScrollPane getTablePanel() {
		if (this.tablePanel == null) {
			this.tablePanel = new JScrollPane(this.getUserTable());
		}
		return this.tablePanel;
	}

	private JTable getUserTable() {
		if (this.userTable == null) {
			this.userTable = new JTable();
			this.userTable.setName("userTable"); //$NON-NLS-1$
		}
		return this.userTable;
	}

	public void initTable() {
		UserTableModel model;
		try {
			model = new UserTableModel(parent.getDao().findAll());
		} catch (DatabaseException e) {
			model = new UserTableModel(new ArrayList());
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		this.getUserTable().setModel(model);
	}

	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if (actionCommand.equalsIgnoreCase("add")) { //$NON-NLS-1$
			this.setVisible(false);
			parent.showAddPanel();
		}
		if (actionCommand.equalsIgnoreCase("edit")) {
			int[] selectedRows = getUserTable().getSelectedRows();
			if (selectedRows.length != 1) {
				JOptionPane.showMessageDialog(this, "Select user", "Alert", JOptionPane.WARNING_MESSAGE);
				return;
			} else {
				int rowIndex = selectedRows[0];
				Long userId = (Long) getUserTable().getValueAt(rowIndex, 0);
				User user = null;
				try {
					user = parent.getDao().find(userId.longValue());
				} catch (DatabaseException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				this.setVisible(false);
				parent.showEditPanel(user);
			}
		}
		if (actionCommand.equalsIgnoreCase("details")) {
			int[] selectedRows = getUserTable().getSelectedRows();
			if (selectedRows.length != 1) {
				JOptionPane.showMessageDialog(this, "Select ONE user to see information.", "Alert",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else {
				int rowIndex = selectedRows[0];
				Long userId = (Long) getUserTable().getValueAt(rowIndex, 0);
				User user = null;
				try {
					user = parent.getDao().find(userId.longValue());
				} catch (DatabaseException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				this.setVisible(false);
				parent.showDetailsPanel(user);
			}
		}
		if (actionCommand.equalsIgnoreCase("delete")) {
			int[] selectedRows = getUserTable().getSelectedRows();
			if (selectedRows.length != 1) {
				JOptionPane.showMessageDialog(this, "Select ONE user to delete.", "Alert", JOptionPane.WARNING_MESSAGE);
				return;
			} else {
				int rowIndex = selectedRows[0];
				Long userId = (Long) getUserTable().getValueAt(rowIndex, 0);
				try {
					parent.getDao().delete(userId.longValue());
					this.initTable();
					this.repaint();
				} catch (DatabaseException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

}