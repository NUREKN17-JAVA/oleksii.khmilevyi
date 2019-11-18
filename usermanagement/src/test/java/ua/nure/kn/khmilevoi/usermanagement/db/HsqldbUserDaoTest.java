package ua.nure.kn.khmilevoi.usermanagement.db;

import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import ua.nure.kn.khmilevoi.usermanagement.User;

public class HsqldbUserDaoTest extends DatabaseTestCase {

	private static final String USER_DATA_SET_XML = "src/test/resources/usersDataSet.xml";
	private static final Date NEW_DATE = new Date();
	private static final String NEW_LAST_NAME = "Temchur";
	private static final String NEW_FIRST_NAME = "Karina";
	HsqldbUserDao dao;
	ConnectionFactory connectionFactory;

	public HsqldbUserDaoTest() {

	}

	protected void setUp() throws Exception {
		try {
			super.setUp();
			dao = new HsqldbUserDao(connectionFactory);
		} catch (Exception e) {
			System.out.println("setUp: " + e);
		}
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCreate() {
		try {
			User user = createUserForTest();
			assertEquals(3, user.getId());
			user = dao.create(user);
			assertNotNull(user);
			assertNotNull(user.getId());
		} catch (DatabaseException e) {
			fail(e.toString());
		}

	}

	public void testFindAll() {
		try {
			Collection<User> resultSet = dao.findAll();
			assertNotNull("Collection is null", resultSet);
			assertEquals("Collection has an unexpected size", 2, resultSet.size());
		} catch (DatabaseException e) {
			System.out.println("findAll: " + e);
			fail(e.toString());
		}
	}

	public void testDelete() {
		User user = createUserForTest();
		try {
			dao.create(user);
			long id = user.getId();
			dao.delete(id);
			User deletedUser = dao.find(id);
			assertNull(deletedUser);
		} catch (DatabaseException e) {
			fail(e.toString());
		}

	}

	public void testFind() {
		User user = createUserForTest();
		try {
			dao.create(user);
			long id = user.getId();
			User selectedUser = dao.find(id);
			assertNull(dao.find(id + 1));
			assertNotNull(selectedUser);
			assertEquals(id, selectedUser.getId());
		} catch (DatabaseException e) {
			fail(e.toString());
		}
	}

	public void testUpdate() {
		User user = createUserForTest();
		try {
			dao.create(user);
			long id = user.getId();

			user.setFirstName(NEW_FIRST_NAME);
			user.setLastName(NEW_LAST_NAME);
			user.setDateOfBirth(NEW_DATE);

			dao.update(user);
			User afterUpdate = dao.find(id);

			assertEquals(NEW_FIRST_NAME, afterUpdate.getFirstName());
			assertEquals(NEW_LAST_NAME, afterUpdate.getLastName());
		} catch (DatabaseException e) {
			System.out.println("update: " + e);
			fail(e.toString());
		}
	}

	protected IDatabaseConnection getConnection() throws Exception {
		Properties prop = new Properties();
		prop.load(getClass().getClassLoader().getResourceAsStream("HsqldbTestSettings.properties"));

		String user = prop.getProperty("connection.user");
		String url = prop.getProperty("connection.url");
		String driver = prop.getProperty("connection.driver");
		String password = prop.getProperty("connection.password");

		connectionFactory = new ConnectionFactoryImplementation(driver, url, user, password);
		return new DatabaseConnection(connectionFactory.createConnection());
	}

	protected IDataSet getDataSet() throws Exception {
		try {
			IDataSet dataSet = new XmlDataSet(new FileInputStream(USER_DATA_SET_XML));
			System.out.println("dateSet: " + dataSet);
			return dataSet;
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return null;
	}

	private User createUserForTest() {
		User user = new User();
		user.setId(3);
		user.setFirstName("Alex");
		user.setLastName("khmilevoi");
		Calendar calendar = Calendar.getInstance();
		calendar.set(2000, 0, 18);
		user.setDateOfBirth(calendar.getTime());
		return user;
	}

}