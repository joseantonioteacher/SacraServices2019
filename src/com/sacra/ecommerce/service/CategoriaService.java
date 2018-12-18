package com.sacra.ecommerce.service;

import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Categoria;

public interface CategoriaService {
	
    public Categoria findById(Long id) 
    		throws InstanceNotFoundException, DataException;
    
    public Boolean exists(Long id) 
    		throws DataException;

     public List<Categoria> findAll(int startIndex, int count) 
    		 throws DataException;
     
     public long countAll() 
     		throws DataException;          
    
     public List<Categoria> findByNombre(String nombre, int startIndex, int count)
    		throws DataException;
     
     public List<Categoria> findByCategoria(Categoria categoria, int startIndex, int count)
 	    	throws DataException;
               
    public Categoria create(Categoria cat) 
    		throws DuplicateInstanceException, DataException;

    public void update(Categoria cat) 
    		throws InstanceNotFoundException, DataException;
        
    public long delete(Long id) 
    		throws InstanceNotFoundException, DataException;
     
}