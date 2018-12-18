package com.sacra.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Proveedor;
import com.sacra.ecommerce.service.ProveedorCriteria;

public interface ProveedorDAO {
	
	public Proveedor findById(Connection connection, Long id) 
		throws InstanceNotFoundException, DataException;
    
    public Boolean exists(Connection connection, Long id) 
    		throws DataException;

     public List<Proveedor> findAll(Connection connection, 
    		 	int startIndex, int count) 
    	throws DataException;
     
     public long countAll(Connection connection) 
     		throws DataException;          
     
     public List<Proveedor> findByCriteria(Connection connection, 
    		 ProveedorCriteria p, int startIndex, int count)
    		throws DataException;
     
     public Proveedor create(Connection connection, Proveedor p) 
     		throws DuplicateInstanceException, DataException;

     public void update(Connection connection, Proveedor p) 
     		throws InstanceNotFoundException, DataException;
         
     public long delete(Connection connection, Long id) 
     		throws InstanceNotFoundException, DataException;
     
}
