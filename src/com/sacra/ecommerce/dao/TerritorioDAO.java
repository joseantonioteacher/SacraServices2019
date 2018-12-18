package com.sacra.ecommerce.dao;

import java.sql.Connection;
import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Territorio;

public interface TerritorioDAO {

	public Territorio findById(Connection connection, Long id) throws InstanceNotFoundException, DataException;

	public Boolean exists(Connection connection, Long id) throws DataException;

	public List<Territorio> findAll(Connection connection, int startIndex, int count) throws DataException;

	public long countAll(Connection connection) throws DataException;

	public List<Territorio> findByNombre(Connection connection, String nombre, int startIndex, int count)
			throws DataException;

	public List<Territorio> findByEmpleado(Connection connection, Long empleadoId) throws DataException;

	public Territorio create(Connection connection, Territorio t) throws DuplicateInstanceException, DataException;

	public void update(Connection connection, Territorio t) throws InstanceNotFoundException, DataException;

	public long delete(Connection connection, Long id) throws InstanceNotFoundException, DataException;
}
