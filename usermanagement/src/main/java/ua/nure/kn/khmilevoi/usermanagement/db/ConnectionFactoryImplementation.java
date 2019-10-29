package ua.nure.kn.khmilevoi.usermanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactoryImplementation implements ConnectionFactory {

	private static final String DRIVER = "org.hsqldb.jdbcDriver";
	private String url = "jdbc:hsqldb:file:db/usermanagement";
	private String user = "sa";
	private String password = "";

	public Connection createConnection() throws DatabaseException {
		
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			throw new DatabaseException(e);
		}
	}

}
