package ua.nure.kn.khmilevoi.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.kn.khmilevoi.usermanagement.User;
import ua.nure.kn.khmilevoi.usermanagement.db.DatabaseException;
import ua.nure.kn.khmilevoi.usermanagement.utill.Messages;

public class EditPanel extends JPanel implements ActionListener {

	private MainFrame parent;
	private JPanel buttonPanel;
	private JButton okButton;
	private JButton cancelButton;
	private JPanel fieldPanel;
	private JTextField firstNameField;
	private JTextField lastNameField;

	private User userToUpdate;

	public void setUser(User user) {
		userToUpdate = user;
		this.getLastNameField().setText(userToUpdate.getLastName());
		this.getFirstNameField().setText(userToUpdate.getFirstName());
	}

	public EditPanel(MainFrame mainFrame, User user) {
		parent = mainFrame;
		this.setUser(user);
		this.initialize();
	}

	private void initialize() {
		this.setName("editPanel");
		this.setLayout(new BorderLayout());
		this.add(getFieldPanel(), BorderLayout.NORTH);
		this.add(getButtonPanel(), BorderLayout.SOUTH);
	}

	private JPanel getButtonPanel() {
		if (this.buttonPanel == null) {
			this.buttonPanel = new JPanel();
			this.buttonPanel.add(getOkButton(), null);
			this.buttonPanel.add(getCancelButton(), null);
		}
		return this.buttonPanel;
	}

	private JButton getOkButton() {
		if (this.okButton == null) {
			this.okButton = new JButton();
			this.okButton.setText(Messages.getString("AddPanel.ok")); //$NON-NLS-1$
			this.okButton.setName("okButton"); //$NON-NLS-1$
			this.okButton.setActionCommand("ok"); //$NON-NLS-1$
			this.okButton.addActionListener(this);
		}
		return this.okButton;
	}

	private JButton getCancelButton() {
		if (this.cancelButton == null) {
			this.cancelButton = new JButton();
			this.cancelButton.setText(Messages.getString("AddPanel.cancel")); //$NON-NLS-1$
			this.cancelButton.setName("cancelButton"); //$NON-NLS-1$
			this.cancelButton.setActionCommand("cancel"); //$NON-NLS-1$
			this.cancelButton.addActionListener(this);
		}
		return this.cancelButton;
	}

	private JPanel getFieldPanel() {
		if (this.fieldPanel == null) {
			this.fieldPanel = new JPanel();
			this.fieldPanel.setLayout(new GridLayout(3, 2));
			this.addLabelField(fieldPanel, Messages.getString("AddPanel.first_name"), getFirstNameField()); //$NON-NLS-1$
			this.addLabelField(fieldPanel, Messages.getString("AddPanel.last_name"), getLastNameField()); //$NON-NLS-1$
		}
		return this.fieldPanel;
	}

	private void addLabelField(JPanel panel, String text, JTextField textField) {
		JLabel label = new JLabel(text);
		label.setLabelFor(textField);
		panel.add(label);
		panel.add(textField);

	}

	private JTextField getFirstNameField() {
		if (this.firstNameField == null) {
			this.firstNameField = new JTextField();
			this.firstNameField.setName("firstNameField"); //$NON-NLS-1$
			this.firstNameField.setText(userToUpdate.getFirstName());
		}

		return this.firstNameField;
	}

	private JTextField getLastNameField() {
		if (this.lastNameField == null) {
			this.lastNameField = new JTextField();
			this.lastNameField.setName("lastNameField"); //$NON-NLS-1$
			this.lastNameField.setText(userToUpdate.getLastName());
		}

		return this.lastNameField;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("ok")) {
			this.userToUpdate.setFirstName(getFirstNameField().getText());
			this.userToUpdate.setLastName(getLastNameField().getText());
			try {
				this.parent.getDao().update(userToUpdate);
			} catch (DatabaseException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		this.setVisible(false);
		this.parent.showBrowsePanel();
	}
}