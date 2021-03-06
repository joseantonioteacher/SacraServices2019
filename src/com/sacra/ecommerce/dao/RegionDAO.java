package com.sacra.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Region;

public interface RegionDAO {
	
    public Region findById(Connection connection, Long id) 
    				throws InstanceNotFoundException, DataException;
    
    public Boolean exists(Connection connection, Long id) 
    				throws DataException;

    public List<Region> findAll(Connection connection, 
    		int startIndex, int count) 
    				throws DataException;
     
    public long countAll(Connection connection) 
    				throws DataException;          
     
    public Region create(Connection connection, Region r) 
    		throws DuplicateInstanceException, DataException;

    public void update(Connection connection, Region r) 
    		throws InstanceNotFoundException, DataException;
         
    public long delete(Connection connection, Long id) 
     		throws InstanceNotFoundException, DataException;
}
