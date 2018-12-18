package com.sacra.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sacra.ecommerce.dao.TerritorioDAO;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Territorio;

public class TerritorioDAOImpl implements TerritorioDAO {

	public TerritorioDAOImpl() {
	}

	@Override
	public Territorio findById(Connection connection, Long id) throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = "SELECT t.TerritoryID, t.TerritoryDescription " + "FROM territories t "
					+ "WHERE t.TerritoryID = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, id);

			resultSet = preparedStatement.executeQuery();

			Territorio t = null;

			if (resultSet.next()) {
				t = loadNext(resultSet);
			} else {
				throw new InstanceNotFoundException("Customer with id " + id + "not found", Territorio.class.getName());
			}

			return t;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public Boolean exists(Connection connection, Long id) throws DataException {
		boolean exist = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT t.TerritoryID, t.TerritoryDescription " + "FROM territories t "
					+ "WHERE t.TerritoryID = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				exist = true;
			}

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}

		return exist;
	}

	@Override
	public List<Territorio> findAll(Connection connection, int startIndex, int count) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT t.TerritoryID, t.TerritoryDescription " + "FROM territories t "
					+ "ORDER BY t.TerritoryID ASC ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();

			List<Territorio> results = new ArrayList<Territorio>();
			Territorio t = null;
			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					t = loadNext(resultSet);
					results.add(t);
					currentCount++;
				} while ((currentCount < count) && resultSet.next());
			}

			return results;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	public List<Territorio> findByCliente(Connection connection, String clienteID) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT t.TerritoryID, t.TerritoryDescription " + "FROM territories t "
					+ " INNER JOIN EmployeeTerritories et ON t.TerritoryID = et.TerritoryID "
					+ " INNER JOIN Employees e " + " 	ON e.EmployeeId = et.EmployeeId AND e.EmployeeId = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();

			List<Territorio> results = new ArrayList<Territorio>();

			Territorio t = null;

			while (resultSet.next()) {
				results.add(t);
			}
			return results;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public long countAll(Connection connection) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = " SELECT count(*) " + " FROM Territories";

			preparedStatement = connection.prepareStatement(queryString);

			resultSet = preparedStatement.executeQuery();

			int i = 1;
			if (resultSet.next()) {
				return resultSet.getLong(i++);
			} else {
				throw new DataException("Unexpected condition trying to retrieve count value");
			}

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public List<Territorio> findByNombre(Connection connection, String nombre, int startIndex, int count)
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT t.TerritoryID, t.TerritoryDescription " + "FROM territories t "
					+ "WHERE UPPER(t.TerritoryID) LIKE ? " + "ORDER BY t.TerritoryDescription ASC ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setString(i++, "%" + nombre.toUpperCase() + "%");

			resultSet = preparedStatement.executeQuery();

			List<Territorio> results = new ArrayList<Territorio>();
			Territorio t = null;
			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					t = loadNext(resultSet);
					results.add(t);
					currentCount++;
				} while ((currentCount < count) && resultSet.next());
			}

			return results;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	private Territorio loadNext(ResultSet resultSet) throws SQLException {

		int i = 1;
		Long TerritoryID = resultSet.getLong(i++);
		String TerritoryDescription = resultSet.getString(i++);

		Territorio t = new Territorio();
		t.setId(TerritoryID);
		t.setDescripcion(TerritoryDescription);

		return t;
	}

	@Override
	public Territorio create(Connection connection, Territorio t) throws DuplicateInstanceException, DataException {

		PreparedStatement preparedStatement = null;
		try {

			if (exists(connection, t.getId())) {
				throw new DuplicateInstanceException(t.getId(), Territorio.class.getName());
			}

			String queryString = "INSERT INTO Employees(TerritoryID, EmployeesDesc) " + "VALUES (?, ?)";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, t.getId());
			preparedStatement.setString(i++, t.getDescripcion());

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Territories'");
			}

			return t;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public void update(Connection connection, Territorio t) throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;
		try {

			String queryString = "UPDATE Territories " + "SET TerritoryDescription = ? " + "WHERE TerritoryID = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, t.getId());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(t.getId(), Territorio.class.getName());
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + t.getId() + "' in table 'Employees'");
			}

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public long delete(Connection connection, Long id) throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;

		try {

			String queryString = "DELETE FROM Territories " + "WHERE TerritoryID = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id);

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(id, Territorio.class.getName());
			}
			return removedRows;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}

	}

	@Override
	public List<Territorio> findByEmpleado(Connection connection, Long empleadoId) throws DataException {
		return null;
	}
}
