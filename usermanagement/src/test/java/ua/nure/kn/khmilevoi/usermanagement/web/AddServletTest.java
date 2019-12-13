package ua.nure.kn.khmilevoi.usermanagement.web;

import java.text.DateFormat;
import java.util.Date;

import ua.nure.kn.khmilevoi.usermanagement.User;

public class AddServletTest extends MockServletTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		createServlet(AddServlet.class);
	}

	User createUser() {
		User user = new User();
		user.setId(1);
		user.setFirstName("Alex");
		user.setLastName("Khmilevoi");
		user.setDateOfBirth(new Date());

		return user;
	}

	public void testAdd() {
		Date date = new Date();
		User user = this.createUser();
		user.setId(-1);
		User createdUser = this.createUser();
		getMockUserDao().expectAndReturn("createUser", user, createdUser);

		addRequestParameter("firstName", "Alex");
		addRequestParameter("lastName", "Khmilevoi");
		addRequestParameter("date", DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton");
		doPost();
	}

	public void testAddEmptyFirstName() {
		Date date = new Date();

		addRequestParameter("id", "1");
		addRequestParameter("lastName", "Khmileoi");
		addRequestParameter("date", DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton");
		doPost();
		String errorMsg = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Cant find a message in session scope", errorMsg);
	}

	public void testAddEmptyLastName() {
		Date date = new Date();

		addRequestParameter("id", "1");
		addRequestParameter("firstName", "Alex");
		addRequestParameter("date", DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton");
		doPost();
		String errorMsg = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Cant find a message in session scope", errorMsg);
	}

	public void testAddEmptyDate() {
		addRequestParameter("id", "1");
		addRequestParameter("firstName", "Alex");
		addRequestParameter("lastName", "Khmilevoi");
		addRequestParameter("okButton");
		doPost();
		String errorMsg = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Cant find a message in session scope", errorMsg);
	}

	public void testAddDateValidate() {
		Date date = new Date();

		addRequestParameter("id", "1");
		addRequestParameter("firstName", "Alex");
		addRequestParameter("lastName", "Khmilevoi");
		addRequestParameter("date", "wrongFormatDate");
		addRequestParameter("okButton");
		doPost();
		String errorMsg = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Cant find a message in session scope", errorMsg);
	}
}