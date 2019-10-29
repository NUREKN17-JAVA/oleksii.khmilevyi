package ua.nure.kn.khmilevoi.usermanagement.db;

import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.kn.khmilevoi.usermanagement.User;

public class HsqldbUserDao implements Dao<User> {

	private static final String SELECT_ALL_USERS = "SELECT id, firstname, lastname, dateofbirth FROM users";
	private static final String CALL_IDENTITY = "CALL IDENTITY()";
	private static final String INSERT_USER_QUERY = "INSERT INTO users(firstname, lastname, dateofbirth) VALUES(?, ?, ?)";
	private ConnectionFactory connectionFactory;

	public HsqldbUserDao(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public User create(User entity) throws DatabaseException {

		Connection connection = connectionFactory.createConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_QUERY);
			preparedStatement.setString(2, entity.getFirstName());
			preparedStatement.setString(2, entity.getLastName());
			preparedStatement.setDate(3, new Date(entity.getDateOfBirth().getTime()));

			int numberOfRows = preparedStatement.executeUpdate();

			if (numberOfRows != 1) {
				throw new DatabaseException("Number of rows: " + numberOfRows);
			}

			CallableStatement callableStatement = connection.prepareCall(CALL_IDENTITY);
			ResultSet keys = callableStatement.executeQuery();

			if (keys.next()) {
				entity.setId(keys.getLong(1));
			}

			keys.close();
			callableStatement.close();
			preparedStatement.close();
			connection.close();

			return entity;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		} catch (DatabaseException e) {
			throw e;
		}

	}

	public void update(User entity) throws DatabaseException {
		// TODO Auto-generated method stub

	}

	public void delete(User entity) throws DatabaseException {
		// TODO Auto-generated method stub

	}

	public User find(long id) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<User> findAll() throws DatabaseException {

		Collection<User> result = new LinkedList<User>();
		try {
			Connection connection = connectionFactory.createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS);

			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getLong(1));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirth(resultSet.getDate(4));
				result.add(user);
			}

			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}

		return result;

	}

}
