package com.sacra.ecommerce.service;

import com.sacra.ecommerce.exceptions.MailException;

public interface MailService {
	
	public void sendMail(String subject, String message, String... to)
		throws MailException;

}
