package com.sacra.ecommerce.service;

import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Cliente;

public interface ClienteService {

	public Cliente findById(String id) 
    		throws InstanceNotFoundException, DataException;
	
	 public Boolean exists(String id) 
	    		throws DataException;

     public List<Cliente> findAll(int startIndex, int count) 
    		 throws DataException;       
     
     public long countAll() 
      		throws DataException;  
    
     public List<Cliente> findByNombre(String nombre, int startIndex, int count)
     		throws DataException;
     
     public List<Cliente> findByCriteria(ClienteCriteria cliente, int startIndex, int count)
    	    	throws DataException;
     
     public Cliente create(Cliente c) 
     		throws DuplicateInstanceException, DataException;

     public void update(Cliente c) 
     		throws InstanceNotFoundException, DataException;
         
     public long delete(String id) 
     		throws InstanceNotFoundException, DataException;

}