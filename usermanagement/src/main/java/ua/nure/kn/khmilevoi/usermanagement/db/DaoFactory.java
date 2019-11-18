package ua.nure.kn.khmilevoi.usermanagement.db;

import java.io.IOException;
import java.util.Properties;

public class DaoFactory {

	private static final String USER_DAO = "dao.UserDao";

	protected final static DaoFactory instance = new DaoFactory();
	protected static Properties properties;

	public static synchronized DaoFactory getInstance() {
		return DaoFactory.instance;
	}

	private DaoFactory() {
		DaoFactory.properties = new Properties();
		try {
			DaoFactory.properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private ConnectionFactory getConnectionFactory() {
		
		String user = properties.getProperty("connection.user");
		String password = properties.getProperty("connection.password");
		String url = properties.getProperty("connection.url");
		String driver = properties.getProperty("connection.driver");
		return new ConnectionFactoryImplementation(driver, url, user, password);
	}

	public UserDao getUserDao() {
		UserDao userDao = null;
		try {
			Class type = Class.forName(properties.getProperty(USER_DAO));
			userDao = (UserDao)type.newInstance();
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
