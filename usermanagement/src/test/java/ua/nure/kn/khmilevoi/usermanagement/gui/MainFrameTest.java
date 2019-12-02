package ua.nure.kn.khmilevoi.usermanagement.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn.khmilevoi.usermanagement.User;
import ua.nure.kn.khmilevoi.usermanagement.db.DaoFactory;
import ua.nure.kn.khmilevoi.usermanagement.db.MockDaoFactory;
import ua.nure.kn.khmilevoi.usermanagement.db.UserDao;

public class MainFrameTest extends JFCTestCase {

	private static final Long ID = new Long(1);
	private static final Date DATE_OF_BIRTH = new Date();
	private static final String LAST_NAME = "Khmilevoi";
	private static final String FIRST_NAME = "Alex";
	private MainFrame mainFrame;
	private Mock mockUserDao;
	private ArrayList<User> users;

	protected void setUp() throws Exception {
		super.setUp();
		try {
			Properties properties = new Properties();
			properties.setProperty("dao.factory", MockDaoFactory.class.getName());
			DaoFactory.init(properties);
			mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).GetMockUserDao();

			users = new ArrayList<User>();

			User user = createUser();
			users.add(user);
			mockUserDao.expectAndReturn("findAll", users);

			setHelper(new JFCTestHelper());
			mainFrame = new MainFrame();
			mainFrame.setDao((UserDao) mockUserDao.proxy());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainFrame.setVisible(true);
	}

	protected void tearDown() throws Exception {
		try {
			mainFrame.setVisible(false);
			getHelper();
			TestHelper.cleanUp(this);
			super.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Component find(Class componentClass, String name) {
		NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
		finder.setWait(0);
		Component component = finder.find(this.mainFrame, 0);
		assertNotNull("Component not find component: " + name, component);
		return component;
	}

	public void testBrowseControls() {
		this.find(JPanel.class, "browsePanel");
		JTable table = (JTable) this.find(JTable.class, "userTable");
		assertEquals(3, table.getColumnCount());
		assertEquals("ID", table.getColumnName(0));
		assertEquals("Name", table.getColumnName(1));
		assertEquals("LastName", table.getColumnName(2));

		this.find(JButton.class, "addButton");
		this.find(JButton.class, "editButton");
		this.find(JButton.class, "deleteButton");
		this.find(JButton.class, "detailsButton");
	}

	public void testAddUser() {
		User expectedUser = createUser();

		mockUserDao.expectAndReturn("CreateUser", expectedUser, expectedUser);
		users.add(expectedUser);
		mockUserDao.expectAndReturn("findAll", users);

		JTable table = (JTable) this.find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());

		JButton addButton = (JButton) this.find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

		this.find(JPanel.class, "addPanel");

		JTextField firstNameField = (JTextField) this.find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) this.find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) this.find(JTextField.class, "dateOfBirthField");
		JButton okButton = (JButton) this.find(JButton.class, "okButton");
		this.find(JButton.class, "cancelButton");

		DateFormat formatter = DateFormat.getDateInstance();
		String dateStr = formatter.format(DATE_OF_BIRTH);
		getHelper().sendString(new StringEventData(this, firstNameField, FIRST_NAME));
		getHelper().sendString(new StringEventData(this, lastNameField, LAST_NAME));
		getHelper().sendString(new StringEventData(this, dateOfBirthField, dateStr));

		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

		this.find(JPanel.class, "browsePanel");
		table = (JTable) this.find(JTable.class, "userTable");
		assertEquals(2, table.getRowCount());
	}

	public void testDetailsUser() {
		User expectedUser = createUser();

		mockUserDao.expectAndReturn("GetUser", expectedUser.getId(), expectedUser);
		ArrayList users = new ArrayList(this.users);
		mockUserDao.expectAndReturn("GetAll", users);

		JTable table = (JTable) this.find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());

		JButton detailsButton = (JButton) this.find(JButton.class, "detailButton");
		getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
		getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));

		this.find(JPanel.class, "detailsPanel");

		this.find(JLabel.class, "idLabel");
		this.find(JLabel.class, "firstNameLabel");
		this.find(JLabel.class, "lastNameLabel");
		this.find(JLabel.class, "birthDateLabel");
		this.find(JLabel.class, "ageLabel");

		JButton cancelButton = (JButton) this.find(JButton.class, "cancelButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

		this.find(JPanel.class, "browsePanel");
		table = (JTable) this.find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());
	}

	public void testDeleteUser() {
		User expectedUser = createUser();

		ArrayList expectedUsers = new ArrayList();
		mockUserDao.expectAndReturn("GetAll", expectedUsers);
		mockUserDao.expect("DeleteUser", expectedUser.getId());

		JTable table = (JTable) this.find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());

		JButton deleteButton = (JButton) this.find(JButton.class, "deleteButton");
		getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
		getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));

		this.find(JPanel.class, "browsePanel");
		table = (JTable) this.find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());
	}

	private User createUser() {
		User expectedUser = new User();
		expectedUser.setId(ID);
		expectedUser.setFirstName(FIRST_NAME);
		expectedUser.setLastName(LAST_NAME);
		expectedUser.setDateOfBirth(DATE_OF_BIRTH);

		return expectedUser;
	}

	public void testEditUser() {
		User expectedUser = createUser();

		mockUserDao.expectAndReturn("GetUser", expectedUser.getId(), expectedUser);
		mockUserDao.expect("UpdateUser", expectedUser);
		ArrayList users = new ArrayList(this.users);
		mockUserDao.expectAndReturn("GetAll", users);

		JTable table = (JTable) this.find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());

		JButton editButton = (JButton) this.find(JButton.class, "editButton");
		getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
		getHelper().enterClickAndLeave(new MouseEventData(this, editButton));

		this.find(JPanel.class, "editPanel");

		JTextField firstNameField = (JTextField) this.find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) this.find(JTextField.class, "lastNameField");

		getHelper().sendString(new StringEventData(this, firstNameField, "newFirst"));
		getHelper().sendString(new StringEventData(this, lastNameField, "newLast"));

		JButton okButton = (JButton) this.find(JButton.class, "okButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

		this.find(JPanel.class, "browsePanel");

		table = (JTable) this.find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());
	}
}
