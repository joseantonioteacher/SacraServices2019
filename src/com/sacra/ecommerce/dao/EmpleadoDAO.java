package com.sacra.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Empleado;
import com.sacra.ecommerce.service.EmpleadoCriteria;

public interface EmpleadoDAO {

	public Empleado findById(Connection connection, Long id) throws InstanceNotFoundException, DataException;

	public Boolean exists(Connection connection, Long id) throws DataException;

	public List<Empleado> findAll(Connection connection, int startIndex, int count) throws DataException;

	public long countAll(Connection connection) throws DataException;

	public List<Empleado> findByNombre(Connection connection, String nombre, int startIndex, int count)
			throws DataException;

	public List<Empleado> findByCriteria(Connection connection, EmpleadoCriteria c, int startIndex, int count)
			throws DataException;

	public Empleado create(Connection connection, Empleado e) throws DuplicateInstanceException, DataException;

	public void update(Connection connection, Empleado e) throws InstanceNotFoundException, DataException;

	public long delete(Connection connection, Long id) throws InstanceNotFoundException, DataException;

}
