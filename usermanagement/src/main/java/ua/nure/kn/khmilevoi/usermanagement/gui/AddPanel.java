package ua.nure.kn.khmilevoi.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.kn.khmilevoi.usermanagement.User;
import ua.nure.kn.khmilevoi.usermanagement.db.DatabaseException;
import ua.nure.kn.khmilevoi.usermanagement.utill.Messages;

public class AddPanel extends JPanel implements ActionListener {
	private MainFrame parent;

	private JPanel buttonPanel;
	private JPanel fieldPanel;
	private JButton okButton;
	private JButton cancelButton;
	private JTextField birthDateField;
	private JTextField firstNameField;
	private JTextField lastNameField;

	public AddPanel(MainFrame mainFrame) {
		this.parent = mainFrame;
		this.initialize();
	}

	private void initialize() {
		this.setName("addPanel"); //$NON-NLS-1$
		this.setLayout(new BorderLayout());
		this.add(this.getFieldPanel(), BorderLayout.NORTH);
		this.add(this.getButtonPanel(), BorderLayout.SOUTH);
	}

	private JPanel getFieldPanel() {
		if (this.fieldPanel == null) {
			this.fieldPanel = new JPanel();
			this.fieldPanel.setLayout(new GridLayout(3, 7));
			this.addLabelField(fieldPanel, Messages.getString("AddPanel.first_name"), this.getFirstNameField()); //$NON-NLS-1$
			this.addLabelField(fieldPanel, Messages.getString("AddPanel.last_name"), this.getLastNameField()); //$NON-NLS-1$
			this.addLabelField(fieldPanel, Messages.getString("AddPanel.birth_date"), this.getBirthDateField()); //$NON-NLS-1$
		}
		return this.fieldPanel;
	}

	private JTextField getBirthDateField() {
		if (this.birthDateField == null) {
			this.birthDateField = new JTextField();
			this.birthDateField.setName("dateOfBirthField"); //$NON-NLS-1$
		}
		return this.birthDateField;
	}

	private JTextField getFirstNameField() {
		if (this.firstNameField == null) {
			this.firstNameField = new JTextField();
			this.firstNameField.setName("firstNameField"); //$NON-NLS-1$
		}
		return this.firstNameField;
	}

	private JTextField getLastNameField() {
		if (this.lastNameField == null) {
			this.lastNameField = new JTextField();
			this.lastNameField.setName("lastNameField"); //$NON-NLS-1$
		}
		return this.lastNameField;
	}

	private void addLabelField(JPanel panel, String text, JTextField textField) {
		JLabel label = new JLabel(text);
		label.setLabelFor(textField);
		panel.add(label);
		panel.add(textField);
	}

	private JPanel getButtonPanel() {
		if (this.buttonPanel == null) {
			this.buttonPanel = new JPanel();
			this.buttonPanel.add(getOkButton(), null);
			this.buttonPanel.add(getCancelButton(), null);
		}
		return this.buttonPanel;
	}

	private JButton getCancelButton() {
		if (this.cancelButton == null) {
			this.cancelButton = new JButton();
			this.cancelButton.setText(Messages.getString("AddPanel.cancel")); //$NON-NLS-1$
			this.cancelButton.setName("cancelButton"); //$NON-NLS-1$
			this.cancelButton.setActionCommand("cancel"); //$NON-NLS-1$
			this.cancelButton.addActionListener(this);
		}
		return cancelButton;
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

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("ok")) { //$NON-NLS-1$
			User user = new User();
			user.setFirstName(this.getFirstNameField().getText());
			user.setLastName(this.getLastNameField().getText());
			DateFormat formatter = DateFormat.getDateInstance();
			try {
				user.setDateOfBirth(formatter.parse(this.getBirthDateField().getText()));
			} catch (ParseException er) {
				this.getBirthDateField().setBackground(Color.red);
				return;
			}
			try {
				this.parent.getDao().create(user);
			} catch (DatabaseException er) {
				JOptionPane.showMessageDialog(this, er.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
			}
		}
		this.setVisible(false);
		this.parent.showBrowsePanel();
	}

	private void clearFields() {
		this.getBirthDateField().setText("");
		this.getFirstNameField().setText("");
		this.getLastNameField().setText("");
	}
}
