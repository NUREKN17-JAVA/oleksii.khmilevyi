package ua.nure.kn.khmilevoi.usermanagement.db;

import ua.nure.kn.khmilevoi.usermanagement.User;

public interface UserDao {
	User create(User entity) throws DatabaseExeption;
}
