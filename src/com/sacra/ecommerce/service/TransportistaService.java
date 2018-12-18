package com.sacra.ecommerce.service;

import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Transportista;

/**
 * 
 * 
 * @author https://www.linkedin.com/in/joseantoniolopezperez
 * @version 0.2
 */
public interface TransportistaService {
	
    public Transportista findById(Long id) 
    		throws InstanceNotFoundException, DataException;
    
    public Boolean exists(Long id) 
    		throws DataException;

     public Results<Transportista> findAll(int startIndex, int count) 
    		 throws DataException;
     
     public long countAll() 
     		throws DataException;          
    
     public Results<Transportista> findByNombre(String nombre, int startIndex, int count)
    		throws DataException;
               
    public Transportista create(Transportista t) 
    		throws DuplicateInstanceException, DataException;

    public void update(Transportista t) 
    		throws InstanceNotFoundException, DataException;
        
    public long delete(Long id) 
    		throws InstanceNotFoundException, DataException;
     
}