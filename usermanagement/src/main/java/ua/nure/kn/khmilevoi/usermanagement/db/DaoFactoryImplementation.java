package ua.nure.kn.khmilevoi.usermanagement.db;

public class DaoFactoryImplementation extends DaoFactory {

	@Override
	public UserDao getUserDao() {
		UserDao userDao = null;
		try {
			Class type = Class.forName(properties.getProperty(USER_DAO));
			userDao = (UserDao) type.newInstance();
			userDao.setConnectionFactory(getConnectionFactory());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		
		return userDao;
	}

}
