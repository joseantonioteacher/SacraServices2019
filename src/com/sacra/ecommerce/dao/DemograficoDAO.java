/**
 * 
 */
package com.sacra.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Demografico;

/**
 * @author Hector
 *
 */
public interface DemograficoDAO {
	
	public Demografico findById(Connection connection, String id) 
    		throws InstanceNotFoundException, DataException;

	public Boolean exists(Connection connection, String id) 
    		throws DataException;

    public List<Demografico> findAll(Connection connection,int startIndex, int count) 
    	throws DataException;
    
    public long countAll(Connection connection) 
     		throws DataException;   
     
    public List<Demografico> findByNombre(Connection connection,String nombre, int startIndex, int count)
    	throws DataException;
      
    public List<Demografico> findByCliente(Connection connection, String clienteID) 
        	throws DataException;

    
    public Demografico create(Connection connection, Demografico c) 
    		throws DuplicateInstanceException, DataException;

    public void update(Connection connection, Demografico c) 
    		throws InstanceNotFoundException, DataException;
        
    public long delete(Connection connection, String id) 
    		throws InstanceNotFoundException, DataException;
}
