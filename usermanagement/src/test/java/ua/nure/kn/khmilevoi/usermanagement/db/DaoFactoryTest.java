package ua.nure.kn.khmilevoi.usermanagement.db;

import junit.framework.TestCase;

public class DaoFactoryTest extends TestCase {

	DaoFactory factory;
	
	protected void setUp() throws Exception {
		super.setUp();
		factory = DaoFactory.getInstance();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetUserDao() {		
		try {
			assertNotNull("Factory instance is null", factory);
			UserDao userDao = factory.getUserDao();
			assertNotNull("Factory returned null", userDao);
		} catch (Exception e) {
			System.out.println(e);
			fail(e.toString());
		}
	}

}
