package com.sacra.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Transportista;
import com.sacra.ecommerce.service.Results;

/**
 * 
 * 
 * @author https://www.linkedin.com/in/joseantoniolopezperez
 * @version 0.2
 */
public interface TransportistaDAO {
	
    public Transportista findById(Connection connection, Long id) 
    		throws InstanceNotFoundException, DataException;
    
    public Boolean exists(Connection connection, Long id) 
    		throws DataException;
    /** 
     * Recordad que es MALA pr�ctica sin usar paginaci�n.
     * En este momento es solo para hacer un ejemplo.
     */
     // public List<Transportista> findAll(Connection connection) 
     //	throws BusinessException; 

     public Results<Transportista> findAll(Connection connection, 
    		 	int startIndex, int count) 
    	throws DataException;
     
     public long countAll(Connection connection) 
     		throws DataException;          
    
     public Results<Transportista> findByNombre(Connection connection, 
    		 String nombre, int startIndex, int count)
    		throws DataException;
               
    public Transportista create(Connection connection, Transportista t) 
    		throws DuplicateInstanceException, DataException;

    public void update(Connection connection, Transportista t) 
    		throws InstanceNotFoundException, DataException;
        
    public long delete(Connection connection, Long id) 
    		throws InstanceNotFoundException, DataException;
     
}