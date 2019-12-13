package ua.nure.kn.khmilevoi.usermanagement.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.kn.khmilevoi.usermanagement.User;

import java.sql.PreparedStatement;
import java.sql.CallableStatement;

public class HsqldbUserDao implements UserDao {

	private static final String SELECT_BY_NAME = "SELECT * FROM users AS u WHERE u.firstname=? AND u.lastname=?";
	private static final String SELECT_ONE_QUERY = "SELECT * FROM users AS u WHERE u.id = ?";
	private static final String DELETE_QUERY = "DELETE FROM users AS u WHERE u.id=?";
	private static final String UPDATE_QUERY = "UPDATE users AS u SET firstname=?, lastname=?, dateofbirth=? WHERE u.id=?";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM users";
	private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES(?,?,?)";
	private ConnectionFactory connectionFactory;

	public HsqldbUserDao() {

	}

	public HsqldbUserDao(ConnectionFactory connectionFactoryParam) {
		connectionFactory = connectionFactoryParam;
	}

	public User create(User user) throws DatabaseException {
		try {
			Connection dbConnection = connectionFactory.createConnection();
			PreparedStatement statement = dbConnection.prepareStatement(INSERT_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateOfBirth().getTime()));

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected != 1) {
				throw new DatabaseException("Rows affected on query - " + rowsAffected);
			}
			CallableStatement callable = dbConnection.prepareCall("call IDENTITY()");
			ResultSet keys = callable.executeQuery();
			if (keys.next()) {
				user.setId(new Long(keys.getLong(1)));
			}

			keys.close();
			callable.close();
			statement.close();
			dbConnection.close();

			return user;
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	public void update(User user) throws DatabaseException {
		try {
			Connection dbConnection = connectionFactory.createConnection();
			PreparedStatement statement = dbConnection.prepareStatement(UPDATE_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
			statement.setLong(4, user.getId());
			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated != 1) {
				throw new DatabaseException("Rows updated count was -" + rowsUpdated);
			}
			statement.close();
			dbConnection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}

	}

	public void delete(long userId) throws DatabaseException {
		try {
			Connection dbConnection = connectionFactory.createConnection();
			PreparedStatement statement = dbConnection.prepareStatement(DELETE_QUERY);
			statement.setLong(1, userId);
			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted != 1) {
				throw new DatabaseException("Deleted rows count is - " + rowsDeleted);
			}
			statement.close();
			dbConnection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	public User find(long userId) throws DatabaseException {
		try {
			Connection dbConnection = connectionFactory.createConnection();
			PreparedStatement statement = dbConnection.prepareStatement(SELECT_ONE_QUERY);
			statement.setLong(1, userId);
			ResultSet resultSet = statement.executeQuery();
			User user = null;
			if (resultSet.next()) {
				user = new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirth(resultSet.getDate(4));

			}
			resultSet.close();
			statement.close();
			dbConnection.close();
			return user;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	public Collection find(String firstName, String lastName) throws DatabaseException {
		Collection result = new LinkedList();

		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirth(resultSet.getDate(4));
				result.add(user);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		return result;
	}

	public Collection<User> findAll() throws DatabaseException {
		try {
			Collection<User> result = new LinkedList<User>();
			Connection dbConnection = connectionFactory.createConnection();
			Statement statement = dbConnection.createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
			while (resultSet.next()) {
				User user = new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirth(resultSet.getDate(4));
				result.add(user);
			}

			resultSet.close();
			statement.close();
			dbConnection.close();

			return result;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;

	}

}