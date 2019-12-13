package ua.nure.kn.khmilevoi.usermanagement.web;

import java.text.DateFormat;
import java.util.Date;

import ua.nure.kn.khmilevoi.usermanagement.User;

public class EditServletTest extends MockServletTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		createServlet(EditServlet.class);
	}

	User createUser() {
		User user = new User();
		user.setId(1);
		user.setFirstName("Alex");
		user.setLastName("Khmilevoi");
		user.setDateOfBirth(new Date());

		return user;
	}

	public void testEdit() {
		Date date = new Date();
		User user = this.createUser();
		getMockUserDao().expect("updateUser", user);

		addRequestParameter("id", "1");
		addRequestParameter("firstName", "Alex");
		addRequestParameter("lastName", "Kjmilevoi");
		addRequestParameter("date", DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton");
		doPost();
	}

	public void testEditEmptyFirstName() {
		Date date = new Date();

		addRequestParameter("id", "1");
		addRequestParameter("lastName", "Khmilevoi");
		addRequestParameter("date", DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton");
		doPost();
		String errorMsg = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Cant find a message in session scope", errorMsg);
	}

	public void testEditEmptyLastName() {
		Date date = new Date();

		addRequestParameter("id", "1");
		addRequestParameter("firstName", "Alex");
		addRequestParameter("date", DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton");
		doPost();
		String errorMsg = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Cant find a message in session scope", errorMsg);
	}

	public void testEditEmptyDate() {
		addRequestParameter("id", "1");
		addRequestParameter("firstName", "Alex");
		addRequestParameter("lastName", "Khmilevoi");
		addRequestParameter("okButton");
		doPost();
		String errorMsg = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Cant find a message in session scope", errorMsg);
	}

	public void testEditDateValidate() {
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