package com.sacra.ecommerce.service;

import com.sacra.ecommerce.service.impl.MailServiceImpl;

public class MailServiceTest {
	
	//Datos
	private String subject = "Test message from My Company E-commerce Mail Service";
	private String message = "Hi, Yes you can!";
	private String[] to = new String [] {
			"topomusero@gmail.com",
			"mralejandro98@gmail.com",
			"dromaycobelas@gmail.com",
			"hector.ledo.doval@gmail.com",
			"joseantoniolp.teacher@gmail.com"
	};
		
	private MailService mailService = null;
	
	public MailServiceTest() {
		mailService = new MailServiceImpl();
	}
		
	protected void testSendMail() {
		System.out.println("Testing email service...");
		try {
			mailService.sendMail(subject, message, to);
		} catch (Throwable t) {
	
			t.printStackTrace();
		}
	System.out.println("MailService test finished");
	}
	
	public static void main(String args[]) {
		MailServiceTest test = new MailServiceTest();
		test.testSendMail();	
	}
		
			
}		
