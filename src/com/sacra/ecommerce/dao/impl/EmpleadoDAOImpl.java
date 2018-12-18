package com.sacra.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;

import com.sacra.ecommerce.dao.EmpleadoDAO;
import com.sacra.ecommerce.dao.TerritorioDAO;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Empleado;
import com.sacra.ecommerce.model.Territorio;
import com.sacra.ecommerce.service.EmpleadoCriteria;

public class EmpleadoDAOImpl implements EmpleadoDAO {

	private TerritorioDAO territorioDAO = null;

	public EmpleadoDAOImpl() {
		territorioDAO = new TerritorioDAOImpl();
	}

	@Override
	public Empleado findById(Connection connection, Long id) throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = "SELECT e.EmployeeID, e.LastName, e.FirstName, e.Title, e.TitleOfCourtesy, e.BirthDate, e.HireDate, e.Address, e.City, e.Region, e.PostalCode, e.Country, e.HomePhone, e.Extension, e.Notes , e.ReportsTo, e.PhotoPath, e.Salary "
					+ "FROM Employees e  " + "WHERE e.EmployeeID = ? ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, id);

			resultSet = preparedStatement.executeQuery();

			Empleado e = null;

			if (resultSet.next()) {
				e = loadNext(connection, resultSet);
			} else {
				throw new InstanceNotFoundException("Employee with id " + id + "not found", Empleado.class.getName());
			}

			return e;

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

			String queryString = "SELECT e.EmployeeID, e.LastName, e.FirstName, e.Title, e.TitleOfCourtesy, e.BirthDate, e.HireDate, e.Address, e.City, e.Region, e.PostalCode, e.Country, e.HomePhone, e.Extension, e.Notes , e.ReportsTo, e.PhotoPath, e.Salary "
					+ "FROM Employees e  " + "WHERE e.EmployeeID = ? ";

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
	public List<Empleado> findAll(Connection connection, int startIndex, int count) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT e.EmployeeID, e.LastName, e.FirstName, e.Title, e.TitleOfCourtesy, e.BirthDate, e.HireDate, e.Address, e.City, e.Region, e.PostalCode, e.Country, e.HomePhone, e.Extension, e.Notes , e.ReportsTo, e.PhotoPath, e.Salary "
					+ "FROM Employees e  ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();

			List<Empleado> results = new ArrayList<Empleado>();
			Empleado e = null;
			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					e = loadNext(connection, resultSet);
					results.add(e);
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

	@Override
	public long countAll(Connection connection) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = " SELECT count(*) " + " FROM Employees";

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
	public List<Empleado> findByNombre(Connection connection, String nombre, int startIndex, int count)
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT e.EmployeeID, e.LastName, e.FirstName, e.Title, e.TitleOfCourtesy, e.BirthDate, e.HireDate, e.Address, e.City, e.Region, e.PostalCode, e.Country, e.HomePhone, e.Extension, e.Notes , e.ReportsTo, e.PhotoPath, e.Salary "
					+ "FROM Employees e  " + "WHERE UPPER(e.FirstName) LIKE ? " + "ORDER BY e.FirstName ASC ";

			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setString(i++, "%" + nombre.toUpperCase() + "%");

			resultSet = preparedStatement.executeQuery();

			List<Empleado> results = new ArrayList<Empleado>();
			Empleado e = null;
			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					e = loadNext(connection, resultSet);
					results.add(e);
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

	@Override
	public List<Empleado> findByCriteria(Connection connection, EmpleadoCriteria empleado, int startIndex, int count)
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {

			queryString = new StringBuilder(
					"SELECT e.EmployeeID, e.LastName, e.FirstName, e.Title, e.TitleOfCourtesy, e.BirthDate, e.HireDate, e.Address, e.City, e.Region, e.PostalCode, e.Country, e.HomePhone, e.Extension, e.Notes , e.ReportsTo, e.PhotoPath, e.Salary "
							+ "FROM Employees e  ");

			// Marca (flag) de primera clausula, que se desactiva en la primera
			boolean first = true;

			if (empleado.getNombre() != null) {
				addClause(queryString, first, " UPPER(e.FirstName) LIKE ? ");
				first = false;
			}

			if (empleado.getApellido() != null) {
				addClause(queryString, first, " UPPER(e.LastName) LIKE ? ");
				first = false;
			}

			if (empleado.getTitulo() != null) {
				addClause(queryString, first, " UPPER(e.Title) LIKE ? ");
				first = false;
			}

			if (empleado.getTituloCortesia() != null) {
				addClause(queryString, first, " UPPER(e.TitleOfCourtesy) LIKE ? ");
				first = false;
			}
			if (empleado.getNacimiento() != null) {
				addClause(queryString, first, " UPPER(e.BirthDate) LIKE ? ");
				first = false;
			}
			if (empleado.getAlta() != null) {
				addClause(queryString, first, " UPPER(e.HireDate) LIKE ? ");
				first = false;
			}
			if (empleado.getDireccion() != null) {
				addClause(queryString, first, " UPPER(e.Adress) LIKE ? ");
				first = false;
			}
			if (empleado.getCiudad() != null) {
				addClause(queryString, first, " UPPER(e.City) LIKE ? ");
				first = false;
			}
			if (empleado.getRegion() != null) {
				addClause(queryString, first, " UPPER(e.Region) LIKE ? ");
				first = false;
			}
			if (empleado.getCodigoPostal() != null) {
				addClause(queryString, first, " UPPER(e.PostalCode) LIKE ? ");
				first = false;
			}
			if (empleado.getPais() != null) {
				addClause(queryString, first, " UPPER(e.Country) LIKE ? ");
				first = false;
			}
			if (empleado.getTelefonoFijo() != null) {
				addClause(queryString, first, " UPPER(e.HomePhone) LIKE ? ");
				first = false;
			}
			if (empleado.getExtension() != null) {
				addClause(queryString, first, " UPPER(e.Extension) LIKE ? ");
				first = false;
			}
			if (empleado.getNotas() != null) {
				addClause(queryString, first, " UPPER(e.Notes) LIKE ? ");
				first = false;
			}
			if (empleado.getLlamarA() != null) {
				addClause(queryString, first, " UPPER(e.ReportsTo LIKE ? ");
				first = false;
			}
			if (empleado.getRutaFoto() != null) {
				addClause(queryString, first, " UPPER(e.PhotoPath) LIKE ? ");
				first = false;
			}
			if (empleado.getSalario() != null) {
				addClause(queryString, first, " UPPER(e.Salary) LIKE ? ");
				first = false;
			}

			preparedStatement = connection.prepareStatement(queryString.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			if (empleado.getNombre() != null)
				preparedStatement.setString(i++, "%" + empleado.getNombre() + "%");
			if (empleado.getApellido() != null)
				preparedStatement.setString(i++, "%" + empleado.getApellido() + "%");
			if (empleado.getTitulo() != null)
				preparedStatement.setString(i++, "%" + empleado.getTitulo() + "%");
			if (empleado.getTituloCortesia() != null)
				preparedStatement.setString(i++, "%" + empleado.getTituloCortesia() + "%");
			if (empleado.getNacimiento() != null)
				preparedStatement.setString(i++, "%" + empleado.getNacimiento() + "%");
			if (empleado.getAlta() != null)
				preparedStatement.setString(i++, "%" + empleado.getAlta() + "%");
			if (empleado.getDireccion() != null)
				preparedStatement.setString(i++, "%" + empleado.getDireccion() + "%");
			if (empleado.getCiudad() != null)
				preparedStatement.setString(i++, "%" + empleado.getCiudad() + "%");
			if (empleado.getRegion() != null)
				preparedStatement.setString(i++, "%" + empleado.getRegion() + "%");
			if (empleado.getCodigoPostal() != null)
				preparedStatement.setString(i++, "%" + empleado.getCodigoPostal() + "%");
			if (empleado.getPais() != null)
				preparedStatement.setString(i++, "%" + empleado.getPais() + "%");
			if (empleado.getTelefonoFijo() != null)
				preparedStatement.setString(i++, "%" + empleado.getTelefonoFijo() + "%");
			if (empleado.getExtension() != null)
				preparedStatement.setString(i++, "%" + empleado.getExtension() + "%");
			if (empleado.getNotas() != null)
				preparedStatement.setString(i++, "%" + empleado.getNotas() + "%");
			if (empleado.getLlamarA() != null)
				preparedStatement.setString(i++, "%" + empleado.getLlamarA() + "%");
			if (empleado.getRutaFoto() != null)
				preparedStatement.setString(i++, "%" + empleado.getRutaFoto() + "%");
			if (empleado.getSalario() != null)
				preparedStatement.setString(i++, "%" + empleado.getSalario() + "%");

			resultSet = preparedStatement.executeQuery();

			List<Empleado> results = new ArrayList<Empleado>();
			Empleado e = null;
			int currentCount = 0;

			if ((startIndex >= 1) && resultSet.absolute(startIndex)) {
				do {
					e = loadNext(connection, resultSet);
					results.add(e);
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

	/*
	 * private String anadir(StringBuilder sb) { return (sb.indexOf("WHERE")>=0) ?
	 * " AND " : "WHERE"; }
	 */

	/**
	 * Evita indexOf cada vez, simplemente con un marca booleana.
	 * 
	 * @param queryString
	 *            Consulta en elaboración
	 * @param first
	 *            Marca de primera clausula añadida
	 * @param clause
	 *            clausula a añadir.
	 */
	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first ? " WHERE " : " AND ").append(clause);
	}

	private Empleado loadNext(Connection connection, ResultSet resultSet) throws SQLException, DataException {

		int i = 1;
		Long employeeId = resultSet.getLong(i++);
		String lastName = resultSet.getString(i++);
		String firstName = resultSet.getString(i++);
		String title = resultSet.getString(i++);
		String titleOfCourtesy = resultSet.getString(i++);
		Date birthDate = resultSet.getDate(i++);
		Date hireDate = resultSet.getDate(i++);
		String address = resultSet.getString(i++);
		String city = resultSet.getString(i++);
		String region = resultSet.getString(i++);
		String postalCode = resultSet.getString(i++);
		String country = resultSet.getString(i++);
		String homePhone = resultSet.getString(i++);
		String extension = resultSet.getString(i++);
		String notes = resultSet.getString(i++);
		Long reportsTo = resultSet.getLong(i++);
		String photoPath = resultSet.getString(i++);
		Long salary = resultSet.getLong(i++);

		Empleado e = new Empleado();
		e.setId(employeeId);
		e.setApellido(lastName);
		e.setNombre(firstName);
		e.setTitulo(title);
		e.setTituloCortesia(titleOfCourtesy);
		e.setNacimiento(birthDate);
		e.setAlta(hireDate);
		e.setDireccion(address);
		e.setCiudad(city);
		e.setRegion(region);
		e.setCodigoPostal(postalCode);
		e.setPais(country);
		e.setTelefonoFijo(homePhone);
		e.setExtension(extension);
		e.setNotas(notes);
		e.setLlamarA(reportsTo);
		e.setRutaFoto(photoPath);
		e.setSalario(salary);

		List<Territorio> territorios = territorioDAO.findByEmpleado(connection, employeeId);
		e.setTerritorio(territorios);

		return e;
	}

	@Override
	public Empleado create(Connection connection, Empleado e) throws DuplicateInstanceException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			String queryString = "INSERT INTO Employees(EmployeeID, LastName, FirstName, Title, TitleOfCourtesy, BirthDate, HireDate, Address, City, Region, PostalCode, Country, HomePhone, Extension, Notes , ReportsTo, PhotoPath, Salary) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);

			int i = 1;
			preparedStatement.setString(i++, e.getNombre());
			preparedStatement.setString(i++, e.getApellido());
			preparedStatement.setString(i++, e.getTitulo());
			preparedStatement.setString(i++, e.getTituloCortesia());
			preparedStatement.setDate(i++, new java.sql.Date(e.getNacimiento().getTime()));
			preparedStatement.setDate(i++, new java.sql.Date(e.getAlta().getTime()));
			preparedStatement.setString(i++, e.getDireccion());
			preparedStatement.setString(i++, e.getCiudad());
			preparedStatement.setString(i++, e.getRegion());
			preparedStatement.setString(i++, e.getCodigoPostal());
			preparedStatement.setString(i++, e.getPais());
			preparedStatement.setString(i++, e.getTelefonoFijo());
			preparedStatement.setString(i++, e.getExtension());
			preparedStatement.setString(i++, e.getNotas());
			preparedStatement.setLong(i++, e.getLlamarA());
			preparedStatement.setString(i++, e.getRutaFoto());
			preparedStatement.setLong(i++, e.getSalario());

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Customers'");
			}

			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Long pk = resultSet.getLong(1);
				e.setId(pk);
			} else {
				throw new DataException("Unable to fetch autogenerated primary key");
			}

			// Inserta los Territorios
			createTerritorios(connection, e.getId(), e.getTerritorio());

			return e;

		} catch (SQLException s) {
			throw new DataException(s);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public void update(Connection connection, Empleado e) throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;
		try {

			String queryString = "UPDATE Employees " + "SET FirstName = ? , LastName = ? " + "WHERE EmployeeID = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setString(i++, e.getNombre());
			preparedStatement.setString(i++, e.getApellido());
			preparedStatement.setLong(i++, e.getId());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(e.getId(), Empleado.class.getName());
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + e.getId() + "' in table 'Employees'");
			}

		} catch (SQLException s) {
			throw new DataException(s);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public long delete(Connection connection, Long id) throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;

		try {

			String queryString = "DELETE FROM Employees " + "WHERE EmployeeID = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id);

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(id, Empleado.class.getName());
			}
			return removedRows;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}

	}

	protected void createTerritorios(Connection connection, Long empleadoId, List<Territorio> territorios)
			throws SQLException, DataException {

		PreparedStatement preparedStatement = null;
		try {
			String queryString = null;
			int i;
			for (Territorio t : territorios) {
				queryString = "INSERT INTO EmployeeTerritories (EmployeeID, TerritoryID) VALUES (?,  ?)";
				preparedStatement = connection.prepareStatement(queryString);

				i = 1;
				preparedStatement.setLong(i++, empleadoId);
				preparedStatement.setLong(i++, t.getId());

				int insertedRows = preparedStatement.executeUpdate();

				if (insertedRows == 0) {
					throw new SQLException("Can not add row to table 'EmployeeTerritories'");
				}

				JDBCUtils.closeStatement(preparedStatement);
			}
		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	protected void deleteTerritorios(Connection c, Long empleadoId) throws SQLException, DataException {

		PreparedStatement preparedStatement = null;

		try {

			String queryString = "DELETE FROM EmployeeTerritories " + "WHERE EmployeeID = ? ";

			preparedStatement = c.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, empleadoId);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

}
