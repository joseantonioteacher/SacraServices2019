package com.sacra.ecommerce.service;

import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Proveedor;

public interface ProveedorService {

		public Proveedor findById(Long id) 
				throws InstanceNotFoundException, DataException;
	    
	    public Boolean exists(Long id) 
	    		throws DataException;

	     public List<Proveedor> findAll (int startIndex, int count) 
	    	throws DataException;
	     
	     public long countAll() 
	     		throws DataException;          
	     
	     public List<Proveedor> findByCriteria(ProveedorCriteria p, int startIndex, int count)
	     		throws DataException;
	     
	     public Proveedor create(Proveedor p) 
	     		throws DuplicateInstanceException, DataException;

	     public void update(Proveedor p) 
	     		throws InstanceNotFoundException, DataException;
	         
	     public long delete(Long id) 
	     		throws InstanceNotFoundException, DataException;
}
