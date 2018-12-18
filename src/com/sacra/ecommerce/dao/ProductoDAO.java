package com.sacra.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Producto;
import com.sacra.ecommerce.service.ProductoCriteria;

public interface ProductoDAO {
	
	public Boolean exists(Connection connection, Long id) throws DataException;
	
    public List<Producto> findAll(Connection connection, int startIndex, int count) throws DataException;

	public long countAll(Connection connection) throws DataException;

	public Producto findById(Connection connection, Long id) throws InstanceNotFoundException, DataException;

	public List<Producto> findByNombre(Connection connection, String nombre, int startIndex, int count) throws DataException;

	public List<Producto> findByCategoria(Connection connection, int idCategoria, int startIndex, int count) throws DataException;
	
    public List<Producto> findByCriteria(Connection connection, ProductoCriteria pc, int startIndex, int count) throws DataException;
    
	public Producto create(Connection connection, Producto pro) throws DuplicateInstanceException, DataException;

	public void update(Connection connection, Producto pro) throws InstanceNotFoundException, DataException;

	public long delete(Connection connection, Long id) throws InstanceNotFoundException, DataException;
	     
	}