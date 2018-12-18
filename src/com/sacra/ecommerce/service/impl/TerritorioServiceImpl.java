package com.sacra.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.sacra.ecommerce.dao.TerritorioDAO;
import com.sacra.ecommerce.dao.impl.TerritorioDAOImpl;
import com.sacra.ecommerce.dao.util.ConnectionManager;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Territorio;
import com.sacra.ecommerce.service.TerritorioService;

public class TerritorioServiceImpl implements TerritorioService {

	private TerritorioDAO dao = null;

	public TerritorioServiceImpl() {
		dao = new TerritorioDAOImpl();
	}

	public Territorio findById(Long id) throws InstanceNotFoundException, DataException {

		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findById(connection, id);

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}

	}

	public Boolean exists(Long id) throws DataException {

		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.exists(connection, id);

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}

	}

	public List<Territorio> findAll(int startIndex, int count) throws DataException {

		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findAll(connection, startIndex, count);

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}

	}

	public long countAll() throws DataException {

		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.countAll(connection);

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}

	}

	public List<Territorio> findByNombre(String nombre, int startIndex, int count) throws DataException {

		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findByNombre(connection, nombre, startIndex, count);

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

	public Territorio create(Territorio t) throws DuplicateInstanceException, DataException {

		Connection connection = null;
		boolean commit = false;

		try {

			connection = ConnectionManager.getConnection();

			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

			connection.setAutoCommit(false);

			// Execute action
			Territorio result = dao.create(connection, t);
			commit = true;

			return result;

		} catch (SQLException e) {
			throw new DataException(e);

		} finally {
			JDBCUtils.closeConnection(connection, commit);
		}
	}

	public void update(Territorio t) throws InstanceNotFoundException, DataException {

		Connection connection = null;
		boolean commit = false;

		try {

			connection = ConnectionManager.getConnection();

			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

			connection.setAutoCommit(false);

			dao.update(connection, t);
			commit = true;

		} catch (SQLException e) {
			throw new DataException(e);

		} finally {
			JDBCUtils.closeConnection(connection, commit);
		}
	}

	public long delete(Long id) throws InstanceNotFoundException, DataException {

		Connection connection = null;
		boolean commit = false;

		try {

			connection = ConnectionManager.getConnection();

			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

			connection.setAutoCommit(false);

			long result = dao.delete(connection, id);
			commit = true;
			return result;

		} catch (SQLException e) {
			throw new DataException(e);

		} finally {
			JDBCUtils.closeConnection(connection, commit);
		}
	}
}
