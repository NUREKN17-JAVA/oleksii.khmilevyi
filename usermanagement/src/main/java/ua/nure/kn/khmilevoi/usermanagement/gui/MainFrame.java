package ua.nure.kn.khmilevoi.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ua.nure.kn.khmilevoi.usermanagement.User;
import ua.nure.kn.khmilevoi.usermanagement.db.DaoFactory;
import ua.nure.kn.khmilevoi.usermanagement.db.UserDao;
import ua.nure.kn.khmilevoi.usermanagement.utill.Messages;

public class MainFrame extends JFrame {

	private static final int FRAME_HEIGHT = 600;
	private static final int FRAME_WIDTH = 800;

	private JPanel contentPanel;
	private JPanel browsePanel;
	private JPanel addPanel;
	private JPanel editPanel;
	private JPanel detailsPanel;

	private UserDao dao;

	public MainFrame() {
		super();
		this.setDao(DaoFactory.getInstance().getUserDao());
		this.initialize();
	}

	private void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(MainFrame.FRAME_WIDTH, MainFrame.FRAME_HEIGHT);
		this.setTitle(Messages.getString("MainFrame.user_management")); //$NON-NLS-1$
		this.setContentPane(this.getContentPanel());
	}

	private Container getContentPanel() {
		if (this.contentPanel == null) {
			this.contentPanel = new JPanel();
			this.contentPanel.setLayout(new BorderLayout());
			this.contentPanel.add(this.getBrowsePanel(), BorderLayout.CENTER);
		}
		return contentPanel;
	}

	private JPanel getBrowsePanel() {
		if (this.browsePanel == null) {
			this.browsePanel = new BrowsePanel(this);
		}
		((BrowsePanel) this.browsePanel).initTable();
		return this.browsePanel;
	}

	private JPanel getAddPanel() {
		if (this.addPanel == null) {
			this.addPanel = new AddPanel(this);
		}
		return this.addPanel;
	}

	private JPanel getEditPanel(User user) {
		if (this.editPanel == null) {
//			editPanel = new EditPanel(this, user);
		}
//		((EditPanel)this.editPanel).SetUser(user);
		return this.editPanel;
	}

	private JPanel getDetailsPanel(User user) {
		if (this.detailsPanel == null) {
//			this.detailsPanel = new DetailsPanel(this, user);
		} else {
//			((DetailsPanel) this.detailsPanel).setUser(user);
		}
		return this.detailsPanel;
	}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}

	public void showAddPanel() {
		this.showPanel(this.getAddPanel());
	}

	public void showEditPanel(User user) {
		this.showPanel(this.getEditPanel(user));
	}

	public void showDetailsPanel(User user) {
		this.showPanel(this.getDetailsPanel(user));
	}

	public void showBrowsePanel() {
		this.showPanel(this.getBrowsePanel());
	}

	private void showPanel(JPanel panel) {
		this.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.repaint();
	}

	public UserDao getDao() {
		return this.dao;
	}

	public void setDao(UserDao dao) {
		this.dao = dao;
	}

}
