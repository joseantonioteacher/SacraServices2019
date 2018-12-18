package com.sacra.ecommerce.util;

/**
 * Test de la utilidad de encriptación de password.
 * (Pronto descubriemos JUnit para hacer mejor los tests).
 *
 * @author https://www.linkedin.com/in/joseantoniolopezperez
 * @version 0.2
 */
public class PasswordEntryptionUtilTest {

	protected void testCorrectPassword() {
		String password = "abc.,123";
		String encryptedPassword = PasswordEncryptionUtil.encryptPassword(password);
		System.out.println("Encrypted password: "+encryptedPassword);	
		System.out.println("Password " + password + " valid: "+PasswordEncryptionUtil.checkPassword(password,encryptedPassword));		
	}

	protected void testIncorrectPassword() {
		String password = "abc.,123";
		String encryptedPassword = PasswordEncryptionUtil.encryptPassword(password);
		System.out.println("Encrypted password: "+encryptedPassword);

		String invalidPassword = "ABC.,123";
		System.out.println("Password " + invalidPassword + " valid: "+PasswordEncryptionUtil.checkPassword(invalidPassword,encryptedPassword));
	}

	public static void main(String args[]) {
		PasswordEntryptionUtilTest test = new PasswordEntryptionUtilTest();
		test.testCorrectPassword();
		test.testIncorrectPassword();
	}

}
