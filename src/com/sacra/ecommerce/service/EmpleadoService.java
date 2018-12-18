package com.sacra.ecommerce.service;


import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Empleado;

public interface EmpleadoService {
	
	
	public Empleado findById(Long id) 
    		throws InstanceNotFoundException, DataException;

	public Boolean exists(Long id) 
    		throws DataException;

    public List<Empleado> findAll(int startIndex, int count) 
    	throws DataException;
    
    public long countAll() 
     		throws DataException;   
     
    public List<Empleado> findByNombre(String nombre, int startIndex, int count)
    	throws DataException;
    
    public List<Empleado> findByCriteria(EmpleadoCriteria c, int startIndex, int count)
    	throws DataException;
    
    public Empleado create(Empleado e) 
    		throws DuplicateInstanceException, DataException;

    public void update(Empleado e) 
    		throws InstanceNotFoundException, DataException;
        
    public long delete(Long id) 
    		throws InstanceNotFoundException, DataException;
    
	

}
