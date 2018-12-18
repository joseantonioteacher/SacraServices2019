package com.sacra.ecommerce.service;

import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Pedido;

public interface PedidoService {
	
	public Pedido findById(Long id) 
			throws InstanceNotFoundException, DataException;
    
    public Boolean exists(Long id) 
    		throws DataException;

     public List<Pedido> findAll (int startIndex, int count) 
    	throws DataException;
     
     public long countAll() 
     		throws DataException;          
     
     public List<Pedido> findByCriteria(PedidoCriteria p, int startIndex, int count)
     		throws DataException;
     
     public Pedido create(Pedido p) 
     		throws DuplicateInstanceException, DataException;

     public void update(Pedido p) 
     		throws InstanceNotFoundException, DataException;
         
     public long delete(Long id) 
     		throws InstanceNotFoundException, DataException;
	
}
