package com.sacra.ecommerce.service;

import com.sacra.ecommerce.model.User;
import com.sacra.ecommerce.service.impl.MockUserServiceImpl;

public class UserServiceTest {
				
	private UserService userService = null;
	
	public UserServiceTest() {
		userService = new MockUserServiceImpl();
	}
		
	protected void testUserNotFound() {
		System.out.println("Testing user not found...");
		try {
			User u = userService.findUserById("someone@somewhere.com");
			if (u == null) {
				System.out.println("OK: User not found");
			}
		} catch (Throwable t) {	
			t.printStackTrace();
		}
	}
	
	protected void testUserFound() {
		System.out.println("Testing user found...");
		try {
			User u = userService.findUserById("joseantoniolp.teacher@gmail.com");
			if (u == null) {
				System.out.println("NOK: User NOT found");
			} else {
				System.out.println("OK: User found: "+u.getFirstName());				
			}
		} catch (Throwable t) {	
			t.printStackTrace();
		}
	}	
	
	public static void main(String args[]) {
		UserServiceTest test = new UserServiceTest();
		test.testUserNotFound();	
		test.testUserNotFound();
	}
		
			
}		
