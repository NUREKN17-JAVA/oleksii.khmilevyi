package ua.nure.kn.khmilevoi.usermanagement.web;

import java.util.Properties;

import com.mockobjects.dynamic.Mock;
import com.mockrunner.servlet.BasicServletTestCaseAdapter;

import ua.nure.kn.khmilevoi.usermanagement.db.DaoFactory;
import ua.nure.kn.khmilevoi.usermanagement.db.MockDaoFactory;

public abstract class MockServletTestCase extends BasicServletTestCaseAdapter {

	private Mock mockUserDao;

	protected void setUp() throws Exception {
		Properties prop = new Properties();
		prop.setProperty("dao.factory", MockDaoFactory.class.getName());
		DaoFactory.init(prop);
		setMockUserDao(((MockDaoFactory) DaoFactory.getInstance()).GetMockUserDao());
		super.setUp();
	}

	protected void tearDown() throws Exception {
		getMockUserDao().verify();
		super.tearDown();
	}

	public Mock getMockUserDao() {
		return mockUserDao;
	}

	public void setMockUserDao(Mock mockUserDao) {
		this.mockUserDao = mockUserDao;
	}

}
