package ua.nure.kn.khmilevoi.usermanagement.db;

import java.util.Collection;

public interface Dao<T> {
	T create(T entity) throws DatabaseExeption;

	void update(T entity) throws DatabaseExeption;

	void delete(T entity) throws DatabaseExeption;

	T find(long id) throws DatabaseExeption;

	Collection<T> findAll() throws DatabaseExeption;
}
