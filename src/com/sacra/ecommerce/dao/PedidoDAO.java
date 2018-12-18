package com.sacra.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Pedido;
import com.sacra.ecommerce.service.PedidoCriteria;

public interface PedidoDAO {
	
	public Pedido findById(Connection connection, Long id) 
			throws InstanceNotFoundException, DataException;
	    
	    public Boolean exists(Connection connection, Long id) 
	    		throws DataException;

	     public List<Pedido> findAll(Connection connection, 
	    		 	int startIndex, int count) 
	    	throws DataException;
	     
	     public long countAll(Connection connection) 
	     		throws DataException;          
	     
	     public List<Pedido> findByCriteria(Connection connection, 
	    		 PedidoCriteria p, int startIndex, int count)
	    		throws DataException;
	     
	     public Pedido create(Connection connection, Pedido p) 
	     		throws DuplicateInstanceException, DataException;

	     public void update(Connection connection, Pedido p) 
	     		throws InstanceNotFoundException, DataException;
	         
	     public long delete(Connection connection, Long id) 
	     		throws InstanceNotFoundException, DataException;

}
