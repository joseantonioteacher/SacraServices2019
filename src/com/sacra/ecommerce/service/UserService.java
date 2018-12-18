package com.sacra.ecommerce.service;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.ServiceException;
import com.sacra.ecommerce.model.User;

public interface UserService {

	/**
	 * Busca un usuario por alguno de sus identificadores.
	 * 
	 * @param id Nombre de usuario (login, nick) o email.
	 * @return Null si no lo encuentra.
	 */
	public User findUserById(String id)
		throws DataException, ServiceException;
	
	
	
}
