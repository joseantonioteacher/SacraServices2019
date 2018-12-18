package com.sacra.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Categoria;
import com.sacra.ecommerce.service.CategoriaCriteria;

public interface CategoriaDAO {
	
	public Categoria findById(Connection connection, Long id) 
		throws InstanceNotFoundException, DataException;
    
    public Boolean exists(Connection connection, Long id) 
    		throws DataException;

     public List<Categoria> findAll(Connection connection, int startIndex, int count) 
    	throws DataException;
     
     public long countAll(Connection connection) 
     		throws DataException;          
    
     public List<Categoria> findByNombre(Connection connection, String nombre, int startIndex, int count)
    		throws DataException;
     
     public List<Categoria> findByCriteria(Connection connection, CategoriaCriteria categoria, int startIndex, int count)
    	    	throws DataException;
     
     public Categoria create(Connection connection, Categoria cat) 
     		throws DuplicateInstanceException, DataException;

     public void update(Connection connection, Categoria cat) 
     		throws InstanceNotFoundException, DataException;
         
     public long delete(Connection connection, Long id) 
     		throws InstanceNotFoundException, DataException;


     
}
