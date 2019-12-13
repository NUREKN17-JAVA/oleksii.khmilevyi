package ua.nure.kn.khmilevoi.usermanagement.db;

import java.util.Collection;

import ua.nure.kn.khmilevoi.usermanagement.User;

public interface UserDao {
	User create(User entity) throws DatabaseException;

	void update(User entity) throws DatabaseException;

	void delete(long userId) throws DatabaseException;

	User find(long userId) throws DatabaseException;

	Collection<User> findAll() throws DatabaseException;

	void setConnectionFactory(ConnectionFactory connectionFactory);

	Collection find(String firstName, String lastName);
}
