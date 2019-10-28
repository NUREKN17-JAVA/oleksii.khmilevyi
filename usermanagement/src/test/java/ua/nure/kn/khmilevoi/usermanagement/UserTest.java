package ua.nure.kn.khmilevoi.usermanagement;

import junit.framework.TestCase;
import java.util.GregorianCalendar;

public class UserTest extends TestCase {

	private User user;

	protected void setUp() throws Exception {
		super.setUp();
		user = new User();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetFullName() {
		user.setFirstName("Alex");
		user.setLastName("Khmilevoi");
		System.out.println(user.getFullName());
		assertEquals("Alex, Khmilevoi", user.getFullName());
	}

	public void testGetAge() {
		GregorianCalendar currentDate = new GregorianCalendar(2019, 9, 28);
		GregorianCalendar dateOfBirth = new GregorianCalendar(2000, 0, 18);

		user.setDateOfBirth(dateOfBirth.getTime());
		System.out.println(user.getAge(currentDate.getTime()));

		assertEquals(19, user.getAge(currentDate.getTime()));
	}

	public void testCorrectAgeTomorrow() {
		GregorianCalendar currentDate = new GregorianCalendar(2019, 9, 28);
		GregorianCalendar dateOfBirth = new GregorianCalendar(2000, 9, 29);

		user.setDateOfBirth(dateOfBirth.getTime());
		System.out.println(user.getAge(currentDate.getTime()));

		assertEquals(18, user.getAge(currentDate.getTime()));
	}

	public void testCorrectAgeYesterday() {
		GregorianCalendar currentDate = new GregorianCalendar(2019, 9, 26);
		GregorianCalendar dateOfBirth = new GregorianCalendar(2000, 9, 27);

		user.setDateOfBirth(dateOfBirth.getTime());
		System.out.println(user.getAge(currentDate.getTime()));

		assertEquals(18, user.getAge(currentDate.getTime()));
	}

	public void testCorrectAgeToday() {
		GregorianCalendar currentDate = new GregorianCalendar(2019, 9, 27);
		GregorianCalendar dateOfBirth = new GregorianCalendar(2000, 9, 27);

		user.setDateOfBirth(dateOfBirth.getTime());
		System.out.println(user.getAge(currentDate.getTime()));

		assertEquals(19, user.getAge(currentDate.getTime()));
	}
	
	public void testLeafYear() {
		GregorianCalendar currentDate = new GregorianCalendar(2019, 9, 27);
		GregorianCalendar dateOfBirth = new GregorianCalendar(2000, 1, 29);

		user.setDateOfBirth(dateOfBirth.getTime());
		System.out.println(user.getAge(currentDate.getTime()));

		assertEquals(19, user.getAge(currentDate.getTime()));
	}
}