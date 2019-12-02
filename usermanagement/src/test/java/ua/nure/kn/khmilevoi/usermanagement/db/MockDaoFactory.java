package ua.nure.kn.khmilevoi.usermanagement.db;

import com.mockobjects.dynamic.Mock;

public class MockDaoFactory extends DaoFactory {

	private Mock mockUserDao;

	public MockDaoFactory() {
		mockUserDao = new Mock(UserDao.class);
	}

	public Mock GetMockUserDao() {
		return mockUserDao;
	}

	public UserDao getUserDao() {
		return (UserDao) mockUserDao.proxy();
	}

}
