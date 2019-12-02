package ua.nure.kn.khmilevoi.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ua.nure.kn.khmilevoi.usermanagement.User;
import ua.nure.kn.khmilevoi.usermanagement.utill.Messages;

public class DetailsPanel extends JPanel implements ActionListener {

	private MainFrame parent;
	private User user;

	private JLabel idLabel;
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel birthDateLabel;
	private JLabel ageLabel;
	private JButton cancelButton;

	private JPanel infoPanel;

	public void SetUser(User user) {
		this.user = user;
		this.setLabelsText();
	}

	public DetailsPanel(MainFrame mainFrame, User user) {
		this.parent = mainFrame;
		this.user = user;
		this.initialize();
	}

	private void initialize() {
		this.setName("detailsPanel");
		this.setLayout(new BorderLayout());
		this.add(getInfoPanel(), BorderLayout.CENTER);
		this.add(getCancelButton(), BorderLayout.AFTER_LAST_LINE);
		this.setLabelsText();
	}

	private JPanel getInfoPanel() {
		if (this.infoPanel == null) {
			this.infoPanel = new JPanel();
			this.infoPanel.setLayout(new GridLayout(5, 2));
			this.addLabelField(infoPanel, "ID", getIdLabel());
			this.addLabelField(infoPanel, "First name", getFirstNameLabel());
			this.addLabelField(infoPanel, "Last name", getLastNameLabel());
			this.addLabelField(infoPanel, "Birth date", getBirthDateLabel());
			this.addLabelField(infoPanel, "Age", getAgeLabel());
		}
		return this.infoPanel;
	}

	private JLabel getIdLabel() {
		if (this.idLabel == null) {
			this.idLabel = new JLabel();
			this.idLabel.setName("idLabel");

		}
		return this.idLabel;
	}

	private JLabel getFirstNameLabel() {
		if (this.firstNameLabel == null) {
			this.firstNameLabel = new JLabel();
			this.firstNameLabel.setName("firstNameLabel");

		}
		return this.firstNameLabel;
	}

	private JLabel getLastNameLabel() {
		if (this.lastNameLabel == null) {
			this.lastNameLabel = new JLabel();
			this.lastNameLabel.setName("lastNameLabel");

		}
		return this.lastNameLabel;
	}

	private JLabel getBirthDateLabel() {
		if (this.birthDateLabel == null) {
			this.birthDateLabel = new JLabel();
			this.birthDateLabel.setName("birthDateLabel");

		}
		return this.birthDateLabel;
	}

	private JLabel getAgeLabel() {
		if (this.ageLabel == null) {
			this.ageLabel = new JLabel();
			this.ageLabel.setName("ageLabel");

		}
		return this.ageLabel;
	}

	private void addLabelField(JPanel panel, String text, JLabel outLabel) {
		JLabel label = new JLabel(text);
		label.setLabelFor(outLabel);
		panel.add(label);
		panel.add(outLabel);
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

	private void setLabelsText() {
		String age = "" + user.getAge();
		this.ageLabel.setText(age);

		String date = "";
		DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		date = formatter.format(user.getDateOfBirth());
		this.birthDateLabel.setText(date);

		this.lastNameLabel.setText(user.getLastName());

		this.firstNameLabel.setText(user.getFirstName());

		this.idLabel.setText(Long.toString(user.getId()));
	}

	public void actionPerformed(ActionEvent e) {
		this.setVisible(false);
		parent.showBrowsePanel();
	}
}