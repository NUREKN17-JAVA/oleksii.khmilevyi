package ua.nure.kn.khmilevoi.usermanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryImplementation implements ConnectionFactory {

	private String driver;
	private String url;
	private String user;
	private String password;

	public ConnectionFactoryImplementation(String driver, String url, String user, String password) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
	}

	public Connection createConnection() throws DatabaseException {

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}

		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

}
