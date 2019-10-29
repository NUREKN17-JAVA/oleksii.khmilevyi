package ua.nure.kn.khmilevoi.usermanagement.db;

import java.util.Properties;

public abstract class DaoFactory {

	private static final String DAO_FACTORY = "dao.Factory";

	protected DaoFactory() {
	}

	protected static DaoFactory instance;
	protected static Properties properties;

	public static synchronized DaoFactory getInstance() {
		if (DaoFactory.instance == null) {
			Class<?> factoryClass;
			

			try {
				factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
				DaoFactory.instance = (DaoFactory) factoryClass.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return DaoFactory.instance;
	}
}
