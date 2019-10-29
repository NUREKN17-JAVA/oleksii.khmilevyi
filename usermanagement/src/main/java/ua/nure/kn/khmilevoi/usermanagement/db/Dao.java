package ua.nure.kn.khmilevoi.usermanagement.db;

import java.util.Collection;

import ua.nure.kn.khmilevoi.usermanagement.User;

public interface Dao {
	User create(User entity) throws DatabaseExeption;

	void update(User entity) throws DatabaseExeption;

	void delete(User entity) throws DatabaseExeption;

	User find(long id) throws DatabaseExeption;

	Collection<User> findAll() throws DatabaseExeption;
}
