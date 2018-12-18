package com.sacra.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Cliente;
import com.sacra.ecommerce.service.ClienteCriteria;

/**
 * @author hector.ledo.doval
 *
 */

public interface ClienteDAO {

	public Cliente findById(Connection connection, String id) 
    		throws InstanceNotFoundException, DataException;

	public Boolean exists(Connection connection, String id) 
    		throws DataException;

    public List<Cliente> findAll(Connection connection,int startIndex, int count) 
    	throws DataException;
    
    public long countAll(Connection connection) 
     		throws DataException;   
     
    public List<Cliente> findByNombre(Connection connection,String nombre, int startIndex, int count)
    	throws DataException;
    
    public List<Cliente> findByCriteria(Connection connection, ClienteCriteria c, int startIndex, int count)
    	throws DataException;
    
    public Cliente create(Connection connection, Cliente c) 
    		throws DuplicateInstanceException, DataException;

    public void update(Connection connection, Cliente c) 
    		throws InstanceNotFoundException, DataException;
        
    public long delete(Connection connection, String id) 
    		throws InstanceNotFoundException, DataException;
}