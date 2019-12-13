package ua.nure.kn.khmilevoi.usermanagement.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ua.nure.kn.khmilevoi.usermanagement.User;

public class MockUserDao implements UserDao {

	private long id = 0;
	private Map users = new HashMap();

	public User create(User user) throws DatabaseException {
		Long currId = new Long(++id);
		user.setId(currId);
		this.users.put(currId.longValue(), user);
		return user;
	}

	public void update(User user) throws DatabaseException {
		Long currId = user.getId();
		users.remove(currId);
		users.put(currId.longValue(), user);
	}

	public void delete(long userId) throws DatabaseException {
		Long currId = new Long(userId);
		users.remove(currId.longValue());
	}

	public User find(long userId) throws DatabaseException {
		Long currId = new Long(userId);
		return (User) users.get(currId.longValue());
	}

	public Collection find(String firstName, String lastName) throws DatabaseException {
		throw new UnsupportedOperationException();
	}

	public Collection findAll() throws DatabaseException {
		return users.values();
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {

	}

}
