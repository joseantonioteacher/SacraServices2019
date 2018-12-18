package com.sacra.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sacra.ecommerce.dao.ProveedorDAO;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Proveedor;
import com.sacra.ecommerce.service.ProveedorCriteria;

public class ProveedorDAOImpl implements ProveedorDAO {
	
	public ProveedorDAOImpl() {		
	}
	
	@Override
	public Proveedor findById(Connection connection, Long id) 
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          

			// Create "preparedStatement"
			String queryString = 
					"SELECT s.SupplierID, s.CompanyName, s.ContactName, s.ContactTitle, s.Address, s.City, s.Region, s.PostalCode, s.Country, s.Phone, s.Fax, s.HomePage " + 
							"FROM Suppliers s  " +
							"WHERE s.SupplierID = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Fill "preparedStatement"
			int i = 1;                
			preparedStatement.setLong(i++, id);

			// Execute query            
			resultSet = preparedStatement.executeQuery();

			Proveedor p = null;

			if (resultSet.next()) {
				p = loadNext(resultSet);				
			} else {
				throw new InstanceNotFoundException("Supplier with id " + id + 
						"not found", Proveedor.class.getName());
			}

			return p;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  
	}
	
	
	@Override
	public Boolean exists(Connection connection, Long id) 
			throws DataException {
		boolean exist = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// Create preparedStatement
			String queryString = "SELECT SupplierID"
					+ " FROM Suppliers "
					+ " WHERE SupplierID = ? ";
			preparedStatement = connection.prepareStatement(queryString);

			// Fill preparedStatement
			int i = 1;
			preparedStatement.setLong(i++, id);

			// Execute query
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
	public List<Proveedor> findAll(Connection connection, 
			int startIndex, int count) 
					throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// Create "preparedStatement"       
			String queryString = 
					"SELECT s.SupplierID, s.CompanyName, s.ContactName, s.ContactTitle, s.Address, s.City, s.Region, s.PostalCode, s.Country, s.Phone, s.Fax, s.HomePage " + 
							"FROM Suppliers s  " +
							"ORDER BY s.CompanyName asc ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Execute query.
			resultSet = preparedStatement.executeQuery();

			// Recupera la pagina de resultados
			List<Proveedor> results = new ArrayList<Proveedor>();                        
			Proveedor p = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					p = loadNext(resultSet);
					results.add(p);               	
					currentCount++;                	
				} while ((currentCount < count) && resultSet.next()) ;
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
	public long countAll(Connection connection) 
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// Create "preparedStatement"
			String queryString = 
					" SELECT count(*) "
							+ " FROM Suppliers";

			preparedStatement = connection.prepareStatement(queryString);

			// Execute query.
			resultSet = preparedStatement.executeQuery();

			int i=1;
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
	public List<Proveedor> findByCriteria(Connection connection, 
			ProveedorCriteria c, int startIndex, int count)
					throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {

			// Create "preparedStatement"       
			queryString = new StringBuilder(
					"SELECT s.SupplierID, s.CompanyName, s.ContactName, s.ContactTitle, s.Address, s.City, s.Region, s.PostalCode, s.Country, s.Phone, s.Fax, s.HomePage " + 
							" FROM Suppliers s  ");
			
			boolean first = true;
								
			if (c.getNombreCompania()!=null) {
				addClause(queryString, first, " UPPER (s.CompanyName) LIKE ? ");
				first = false;
			}
			
			if (c.getNombreContacto()!=null) {
				addClause(queryString, first, " UPPER (s.ContactName) LIKE ? ");
				first = false;
			}
			
			if (c.getPuestoContacto()!=null) {
				addClause(queryString, first, " UPPER (s.ContactTitle) LIKE ? ");
				first = false;
			}
			
			if (c.getDireccion()!=null) {
				addClause(queryString, first, " UPPER (s.Address) LIKE ? ");
				first = false;
			}
			
			if (c.getCiudad()!=null) {
				addClause(queryString, first, " UPPER (s.City) LIKE ? ");
				first = false;
			}
			
			if (c.getComunidad()!=null) {
				addClause(queryString, first, " UPPER (s.Region) LIKE ? ");
				first = false;
			}
			
			if (c.getCodigoPostal()!=null) {
				addClause(queryString, first, " UPPER (s.PostalCode) LIKE ? ");
				first = false;
			}
			
			if (c.getPais()!=null) {
				addClause(queryString, first, " UPPER (s.Country) LIKE ? ");
				first = false;
			}
			
			if (c.getTelefono()!=null) {
				addClause(queryString, first, " UPPER (s.Phone) LIKE ? ");
				first = false;
			}
			
			if (c.getFax()!=null) {
				addClause(queryString, first, " UPPER (s.Fax) LIKE ? ");
				first = false;
			}
			
			if (c.getPaginaInicio()!=null) {
				addClause(queryString, first, " UPPER (s.HomePage) LIKE ? ");
				first = false;
			}
			
			
			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Fill parameters
			int i = 1;
         
			if (c.getNombreCompania()!=null) {
				preparedStatement.setString(i++, "%" + c.getNombreCompania().toUpperCase() + "%");
			}
			
			if (c.getNombreContacto()!=null) {
				preparedStatement.setString(i++, "%" + c.getNombreContacto().toUpperCase() + "%");
			}
			
			if (c.getPuestoContacto()!=null) {
				preparedStatement.setString(i++, "%" + c.getPuestoContacto().toUpperCase() + "%");
			}
			
			if (c.getDireccion()!=null) {
				preparedStatement.setString(i++, "%" + c.getDireccion().toUpperCase() + "%");
			}
			
			if (c.getCiudad()!=null) {
				preparedStatement.setString(i++, "%" + c.getCiudad().toUpperCase() + "%");
			}
			
			if (c.getComunidad()!=null) {
				preparedStatement.setString(i++, "%" + c.getComunidad().toUpperCase() + "%");
			}
			
			if (c.getCodigoPostal()!=null) {
				preparedStatement.setString(i++, "%" + c.getCodigoPostal().toUpperCase() + "%");
			}
			
			if (c.getPais()!=null) {
				preparedStatement.setString(i++, "%" + c.getPais().toUpperCase() + "%");
			}
			
			if (c.getTelefono()!=null) {
				preparedStatement.setString(i++, "%" + c.getTelefono().toUpperCase() + "%");
			}
			
			if (c.getFax()!=null) {
				preparedStatement.setString(i++, "%" + c.getFax().toUpperCase() + "%");
			}
			
			if (c.getPaginaInicio()!=null) {
				preparedStatement.setString(i++, "%" + c.getPaginaInicio().toUpperCase() + "%");
			}
			
			// Execute query.
			resultSet = preparedStatement.executeQuery();

			// Recupera la pagina de resultados
			List<Proveedor> results = new ArrayList<Proveedor>();                        
			Proveedor p = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					p = loadNext(resultSet);
					results.add(p);               	
					currentCount++;                	
				} while ((currentCount < count) && resultSet.next()) ;
			}

			return results;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
			
	}
			
	private Proveedor loadNext(ResultSet resultSet)
			throws SQLException {

		// Recupera los atributos asumiendo un orden comun
		int i = 1;
		Long supplierId = resultSet.getLong(i++);	                
		String companyName = resultSet.getString(i++);	                
		String contactName = resultSet.getString(i++);
		String contactTitle = resultSet.getString(i++);
		String address = resultSet.getString(i++);
		String city = resultSet.getString(i++);
		String region = resultSet.getString(i++);
		String postalCode = resultSet.getString(i++);
		String country = resultSet.getString(i++);
		String phone= resultSet.getString(i++);
		String fax = resultSet.getString(i++);
		String homePage = resultSet.getString(i++);
	

		// Rellena el objeto
		Proveedor p = new Proveedor();		
		p.setId(supplierId);
		p.setNombreCompania(companyName);
		p.setNombreContacto(contactName);
		p.setPuestoContacto(contactTitle);
		p.setDireccion(address);
		p.setCiudad(city);
		p.setComunidad(region);
		p.setCodigoPostal(postalCode);
		p.setCiudad(country);
		p.setTelefono(phone);
		p.setFax(fax);
		p.setPaginaInicio(homePage);
		
		return p;
	}
	
	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " AND ").append(clause);
	}
	
	@Override
	public Proveedor create(Connection connection, Proveedor p) 
			throws DuplicateInstanceException, DataException {


		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {          

			// Create the "preparedStatement"
			String queryString = "INSERT INTO Suppliers(CompanyName, ContactName, ContactTitle, Address, City, Region, PostalCode, Country, Phone, Fax, HomePage ) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(queryString,
									Statement.RETURN_GENERATED_KEYS);

			// Fill the "preparedStatement"
			int i = 1;             
			preparedStatement.setString(i++, p.getNombreCompania());
			preparedStatement.setString(i++, p.getNombreContacto());
			preparedStatement.setString(i++, p.getPuestoContacto());
			preparedStatement.setString(i++, p.getDireccion());
			preparedStatement.setString(i++, p.getCiudad());
			preparedStatement.setString(i++, p.getComunidad());
			preparedStatement.setString(i++, p.getCodigoPostal());
			preparedStatement.setString(i++, p.getPais());
			preparedStatement.setString(i++, p.getTelefono());
			preparedStatement.setString(i++, p.getFax());
			preparedStatement.setString(i++, p.getPaginaInicio());
			

			// Execute query
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Shippers'");
			} 

			// Recuperamos la PK generada
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Long pk = resultSet.getLong(1); 
				p.setId(pk);
			} else {
				throw new DataException("Unable to fetch autogenerated primary key");
			}

			// Return the DTO
			return p;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	@Override
	public void update(Connection connection, Proveedor p) 
			throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;
		try {

			// Create "preparedStatement"
			String queryString = 
					"UPDATE Suppliers " +
					"SET CompanyName = ? , ContactName = ? , ContactTitle = ? , Address = ? , City = ?, Region = ?, PostalCode = ?, Country = ?, Phone = ?, Fax = ?, HomePage = ?  " +
					"WHERE SupplierID = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			// Fill "preparedStatement"
			int i = 1;
			preparedStatement.setString(i++, p.getNombreCompania());
			preparedStatement.setString(i++, p.getNombreContacto());
			preparedStatement.setString(i++, p.getPuestoContacto());
			preparedStatement.setString(i++, p.getDireccion());
			preparedStatement.setString(i++, p.getCiudad());
			preparedStatement.setString(i++, p.getComunidad());
			preparedStatement.setString(i++, p.getCodigoPostal());
			preparedStatement.setString(i++, p.getPais());
			preparedStatement.setString(i++, p.getTelefono());
			preparedStatement.setString(i++, p.getFax());
			preparedStatement.setString(i++, p.getPaginaInicio());
			preparedStatement.setLong(i++, p.getId());
			
			/* Execute update. */
			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(p.getId(), Proveedor.class.getName());
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						p.getId() + "' in table 'Suppliers'");
			}                          

		} catch (SQLException e) {
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}              		
	}
	
	@Override
	public long delete(Connection connection, Long id) 
			throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;

		try {

			// Create "preparedStatement"
			String queryString =	
					  "DELETE FROM Suppliers " 
					+ "WHERE SupplierID = ? ";
			
			preparedStatement = connection.prepareStatement(queryString);

			// Fill "preparedStatement"
			int i = 1;
			preparedStatement.setLong(i++, id);

			// Execute query
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(id,Proveedor.class.getName());
			} 
			return removedRows;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}

	}

}
