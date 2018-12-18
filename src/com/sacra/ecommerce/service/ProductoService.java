package com.sacra.ecommerce.service;

import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Producto;
import com.sacra.ecommerce.service.ProductoCriteria;


public interface ProductoService {
	
    public Boolean exists(Long id) throws DataException;
    
    public List<Producto> findAll(int startIndex, int count) throws DataException;

    public long countAll() throws DataException;              
	
    public Producto findById(Long id) throws InstanceNotFoundException, DataException;
    
    public List<Producto> findByNombre(String nombre, int startIndex, int count) throws DataException;
    
    public List<Producto> findByCategoria(Integer idCategoria, int startIndex, int count) throws DataException;
    
    public List<Producto> findByCriteria(ProductoCriteria pc, int startIndex, int count) throws DataException;
    
    public Producto create(Producto pro) throws DuplicateInstanceException, DataException;

    public void update(Producto pro) throws InstanceNotFoundException, DataException;
        
    public long delete(Long id) throws InstanceNotFoundException, DataException;
     
}