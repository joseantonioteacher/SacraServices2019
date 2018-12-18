package com.sacra.ecommerce.model;

import java.util.Date;

/**
 * Usuario registrado en el sistema.
 *
 * @author https://www.linkedin.com/in/joseantoniolopezperez
 * @version 0.2
 */
public class User {
	private String userName = null;
	private String email = null;;
	private String encryptedPassword = null;;
	private String firstName = null;
	private String lastName = null;
	private Date bornDate = null;
	
	public User() {		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

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

	public Date getBornDate() {
		return bornDate;
	}

	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}
}