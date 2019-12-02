package ua.nure.kn.khmilevoi.usermanagement.db;

import java.io.IOException;
import java.util.Properties;

public abstract class DaoFactory {

	protected static final String USER_DAO = "dao.UserDao";
	private static final String DAO_FACTORY = "dao.factory";

	protected static DaoFactory instance;
	protected static Properties properties;

	static {
		DaoFactory.properties = new Properties();
		try {
			DaoFactory.properties.load(DaoFactory.class.getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static synchronized DaoFactory getInstance() {
		if (DaoFactory.instance == null) {
			Class factoryClass;
			try {
				factoryClass = Class.forName(DaoFactory.properties.getProperty(DaoFactory.DAO_FACTORY));
				DaoFactory.instance = (DaoFactory) factoryClass.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return DaoFactory.instance;
	}

	protected DaoFactory() {
		DaoFactory.properties = new Properties();
		try {
			DaoFactory.properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void init(Properties propParam) {
		DaoFactory.properties = propParam;
		DaoFactory.instance = null;
	}

	protected ConnectionFactory getConnectionFactory() {
		return new ConnectionFactoryImplementation(DaoFactory.properties);
	}

	public abstract UserDao getUserDao();
}
