/**
 * 
 */
package com.sacra.ecommerce.service;

import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Demografico;

/**
 * @author Hector
 *
 */
public interface DemograficoService {
	
	public Demografico findById(String id) 
    		throws InstanceNotFoundException, DataException;
	
	 public Boolean exists(String id) 
	    		throws DataException;

     public List<Demografico> findAll(int startIndex, int count) 
    		 throws DataException;       
     
     public long countAll() 
      		throws DataException;  
    
     public List<Demografico> findByNombre(String nombre, int startIndex, int count)
     		throws DataException;
          
     public Demografico create(Demografico c) 
     		throws DuplicateInstanceException, DataException;

     public void update(Demografico c) 
     		throws InstanceNotFoundException, DataException;
         
     public long delete(String id) 
     		throws InstanceNotFoundException, DataException;

}