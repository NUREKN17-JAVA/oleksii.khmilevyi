package ua.nure.kn.khmilevoi.usermanagement;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

	private static final long oneDayInMillis = 24 * 60 * 60 * 1000;
	private static final long serialVersionUID = -6181972498369266938L;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFullName() {
		return this.firstName + ", " + this.lastName;
	}

	public int getAge() {
		return this._getAge(new Date());
	}

	public int getAge(Date current) {
		return this._getAge(current);
	}

	private int _getAge(Date current) {
		final Date currentDate = current;

		final long div = currentDate.getTime() - this.dateOfBirth.getTime() + oneDayInMillis;

		return new Date(div).getYear() - new Date(0).getYear();
	}
}