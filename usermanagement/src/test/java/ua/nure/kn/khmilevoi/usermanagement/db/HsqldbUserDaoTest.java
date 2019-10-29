package ua.nure.kn.khmilevoi.usermanagement.db;

import java.util.Collection;
import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import ua.nure.kn.khmilevoi.usermanagement.User;

public class HsqldbUserDaoTest extends DatabaseTestCase {

	private static final String LAST_NAME = "Khmilevoi";
	private static final String FIRST_NAME = "Alex";
	private HsqldbUserDao dao;
	private ConnectionFactory connectionFactory;

	public void testCreate() throws DatabaseException {
		User user = new User();

		user.setFirstName(FIRST_NAME);
		user.setLastName(LAST_NAME);
		user.setDateOfBirth(new Date());
		assertNull(user.getId());
		User userToCheck = dao.create(user);
		assertNotNull(userToCheck);
		assertNotNull(userToCheck.getId());
	}

	protected void setUp() throws Exception {
		super.setUp();

		dao = new HsqldbUserDao(this.connectionFactory);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testFindAll() throws DatabaseException {
		Collection<User> items = dao.findAll();
		assertNotNull(items);
		assertEquals(2, items.size());
	}

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		this.connectionFactory = new ConnectionFactoryImplementation();

		return new DatabaseConnection(this.connectionFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {

		IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));

		return null;
	}

}
