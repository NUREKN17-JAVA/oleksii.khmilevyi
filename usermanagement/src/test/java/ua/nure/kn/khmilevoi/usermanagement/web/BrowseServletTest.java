package ua.nure.kn.khmilevoi.usermanagement.web;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ua.nure.kn.khmilevoi.usermanagement.User;

public class BrowseServletTest extends MockServletTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		createServlet(BrowseServlet.class);
	}

	User createUser() {
		User user = new User();
		user.setId(1);
		user.setFirstName("Alex");
		user.setLastName("Khmilevoi");
		user.setDateOfBirth(new Date());

		return user;
	}

	public void testBrowse() {
		User user = this.createUser();

		List list = Collections.singletonList(user);
		getMockUserDao().expectAndReturn("findAll", list);
		doGet();
		Collection collection = (Collection) getWebMockObjectFactory().getMockSession().getAttribute("users");
		assertNotNull("Couldn`t find list of users in session", collection);
		assertSame(list, collection);
	}

	public void testEdit() {
		User user = this.createUser();
		getMockUserDao().expectAndReturn("find", new Long(1).longValue(), user);
		addRequestParameter("editButton", "edit");
		addRequestParameter("id", "1");
		doPost();
		User userInsession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
		assertNotNull("No user in session", userInsession);
		assertSame(user, userInsession);
	}

	public void testDetails() {
		User user = this.createUser();
		getMockUserDao().expectAndReturn("find", new Long(1).longValue(), user);
		addRequestParameter("detailsButton", "details");
		addRequestParameter("id", "1");
		doPost();
		User userInsession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
		assertNotNull("No user in session", userInsession);
		assertSame(user, userInsession);
	}

	public void testDelete() {
		User user = this.createUser();
		getMockUserDao().expect("delete", user.getId());
		addRequestParameter("deleteButton", "delete");
		addRequestParameter("id", "1");
		doPost();
	}
}