package com.sacra.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.LineaPedido;
import com.sacra.ecommerce.model.LineaPedidoId;

public interface LineaPedidoDAO {
	
	public LineaPedido findById(Connection connection, LineaPedidoId id) 
        	throws DataException;
	
	public Boolean exists(Connection connection, LineaPedidoId id) 
    		throws DataException;
	    
    public List<LineaPedido> findByPedido(Connection connection, Long pedidoId) 
        	throws DataException;
  
    public LineaPedido create(Connection connection, LineaPedido c) 
    		throws DuplicateInstanceException, DataException;
        
    public long delete(Connection connection, LineaPedidoId id) 
    		throws InstanceNotFoundException, DataException;

}
