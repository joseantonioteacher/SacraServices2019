package com.sacra.ecommerce.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.ServiceException;
import com.sacra.ecommerce.model.User;
import com.sacra.ecommerce.service.UserService;

/**
 * Ejemplo de implementacion mock. 
 *
 * @author https://www.linkedin.com/in/joseantoniolopezperez
 * @version 0.2
 */
public class MockUserServiceImpl implements UserService {

	// Datos del usuario que acepta
	private static final String TEST_USER_NAME = "joseantoniolp";
	private static final String TEST_EMAIL = "joseantoniolp.teacher@gmail.com";
	private static final String TEST_ENCRYPTED_PASSWORD = "w94It5/GJ9Edk1YMOD9ipJzzHIskjB06Pj07wiLgeMeFGzlCtyWH/Re7bfLnmQaM";
	private static final String TEST_FIRST_NAME = "Jose Antonio";
	private static final String TEST_LAST_NAME = "Lopez";
	private static Date TEST_BORN_DATE = null;
	static {
		Calendar c = Calendar.getInstance();
		c.set(1947, Calendar.APRIL, 7, 0, 0, 0);
		TEST_BORN_DATE = c.getTime();		
		// Desde Java 8, tambien con java.util.LocalDate.of o parse 		
	}		
	
	public MockUserServiceImpl() {		
	}
	
	@Override
	public User findUserById(String id)
			throws DataException, ServiceException {
		
		User user = null;		 
		
		if (StringUtils.isNotEmpty(id)) {			
			
			id = id.trim(); // O antes si se evalua !=null	  
			
			// Aqui obtendria la conexion e invocaria al DAO en la implementacion real
			// que como mock es...
			if (TEST_USER_NAME.equalsIgnoreCase(id)
					|| TEST_EMAIL.equalsIgnoreCase(id)) {
				
				user = new User();
				user.setUserName(TEST_USER_NAME);
				user.setEmail(TEST_EMAIL);
				user.setEncryptedPassword(TEST_ENCRYPTED_PASSWORD);
				user.setBornDate(TEST_BORN_DATE);
				user.setFirstName(TEST_FIRST_NAME);
				user.setLastName(TEST_LAST_NAME);
			}
		}
		return user;
	}

	
}
