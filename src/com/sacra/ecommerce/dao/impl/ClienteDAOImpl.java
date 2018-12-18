/**
 * 
 */
package com.sacra.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sacra.ecommerce.dao.ClienteDAO;
import com.sacra.ecommerce.dao.DemograficoDAO;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Cliente;
import com.sacra.ecommerce.model.Demografico;
import com.sacra.ecommerce.service.ClienteCriteria;

/**
 * @author hector.ledo.doval
 *
 */
public class ClienteDAOImpl implements ClienteDAO {

	private static Logger logger = LogManager.getLogger(ClienteDAOImpl.class.getName());
	
	private DemograficoDAO demograficoDAO = null;
	
	public ClienteDAOImpl() {
		demograficoDAO  = new DemograficoDAOImpl();
	}
	
	@Override
	public Cliente findById(Connection connection, String id) 
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT c.CustomerID, c.CompanyName, c.ContactName, c.ContactTitle, c.Address, c.City, c.Region, c.PostalCode, c.Country, c.Phone, c.Fax " + 
							"FROM Customers c  " +
							"WHERE c.CustomerID = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setNString(i++, id);

			resultSet = preparedStatement.executeQuery();

			Cliente e = null;

			if (resultSet.next()) {
				e = loadNext(connection, resultSet);				
			} else {
				throw new InstanceNotFoundException("Customer with id " + id + 
						"not found", Cliente.class.getName());
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
	public Boolean exists(Connection connection, String id) 
			throws DataException {
		boolean exist = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					"SELECT c.CustomerID, c.CompanyName, c.ContactName, c.ContactTitle, c.Address, c.City, c.Region, c.PostalCode, c.Country, c.Phone, c.Fax " + 
					"FROM Customers c  " +
					"WHERE c.CustomerID = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setNString(i++, id);

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
	public List<Cliente> findAll(Connection connection,int startIndex, int count) 
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					"SELECT c.CustomerID, c.CompanyName, c.ContactName, c.ContactTitle, c.Address, c.City, c.Region, c.PostalCode, c.Country, c.Phone, c.Fax " + 
					"FROM Customers c  " +
					"ORDER BY c.CompanyName ASC";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();

			List<Cliente> results = new ArrayList<Cliente>();                        
			Cliente e = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					e = loadNext(connection, resultSet);
					results.add(e);               	
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

			String queryString = 
					" SELECT count(*) "
							+ " FROM Customers";

			preparedStatement = connection.prepareStatement(queryString);

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
	public List<Cliente> findByNombre(Connection connection,String nombre, int startIndex, int count)
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					"SELECT c.CustomerID, c.CompanyName, c.ContactName, c.ContactTitle, c.Address, c.City, c.Region, c.PostalCode, c.Country, c.Phone, c.Fax " + 
					"FROM Customers c  " +
					"WHERE UPPER(c.CompanyName) LIKE ? "+
					"ORDER BY c.CompanyName ASC ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

						int i = 1;                
			preparedStatement.setString(i++, "%" + nombre.toUpperCase() + "%");

			resultSet = preparedStatement.executeQuery();

			List<Cliente> results = new ArrayList<Cliente>();                        
			Cliente e = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					e = loadNext(connection, resultSet);
					results.add(e);               	
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
	public List<Cliente> findByCriteria(Connection connection, ClienteCriteria cliente, int startIndex, int count)
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
    
			queryString = new StringBuilder(
					" SELECT c.CustomerID, c.CompanyName, c.ContactName, c.ContactTitle, c.Address, c.City, c.Region, c.PostalCode, c.Country, c.Phone, c.Fax " + 
					" FROM Customers c ");
			
			// Marca (flag) de primera clausula, que se desactiva en la primera
			boolean first = true;
			
			if (cliente.getNombreCompania()!=null) {
				addClause(queryString, first, " UPPER(c.CompanyName) LIKE ? ");
				first = false;
			}
			
			if (cliente.getNombre()!=null) {
				addClause(queryString, first, " UPPER(c.ContactName) LIKE ? ");
				first = false;
			}

			if (cliente.getTitulo()!=null) {
				addClause(queryString, first, " UPPER(c.ContactTitle) LIKE ? ");
				first = false;
			}			
			// ... y as� para el resto de atributos
			
			logger.debug("Criteria query: "+queryString);
			
			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;       
			
			preparedStatement.setString(i++, "%" +  cliente.getNombreCompania() + "%");
			preparedStatement.setString(i++, "%" +  cliente.getNombre() + "%");
			preparedStatement.setString(i++, "%" +  cliente.getTitulo() + "%");

			resultSet = preparedStatement.executeQuery();
			
			List<Cliente> results = new ArrayList<Cliente>();                        
			Cliente e = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					e = loadNext(connection, resultSet);
					results.add(e);               	
					currentCount++;                	
				} while ((currentCount < count) && resultSet.next()) ;
			}

			return results;
	
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeResultSet(resultSet);
				JDBCUtils.closeStatement(preparedStatement);
		}
	}
	


	@Override
	public Cliente create(Connection connection, Cliente c) 
			throws DuplicateInstanceException, DataException {

		PreparedStatement preparedStatement = null;
		try {          
			
			//Check if the primary key already exists
			if (exists(connection, c.getId())) {
				throw new DuplicateInstanceException(c.getId(), Cliente.class.getName());
			}
			
			//We establish the primary key based on the company name or the contact name depending if the first one has more than 5 letters. 
			String id= (c.getNombreCompania().length()>=5) ? c.getNombreCompania().substring(0, 5).toUpperCase()
															: c.getNombre().substring(0, 5).toUpperCase();
			if (id.length()>5)
				throw new DataException("Unable to create primary key");

			
			String queryString = "INSERT INTO Customers(CustomerId, CompanyName, ContactName, ContactTitle, Address, City, Region, PostalCode, Country, Phone, Fax) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(queryString);
			
			int i = 1;     
			preparedStatement.setString(i++, id);
			preparedStatement.setString(i++, c.getNombreCompania());
			preparedStatement.setString(i++, c.getNombre());
			preparedStatement.setString(i++, c.getTitulo());
			preparedStatement.setString(i++, c.getDireccion());
			preparedStatement.setString(i++, c.getCiudad());
			preparedStatement.setString(i++, c.getRegion());
			preparedStatement.setString(i++, c.getCodigoPostal());
			preparedStatement.setString(i++, c.getPais());
			preparedStatement.setString(i++, c.getTelefono());
			preparedStatement.setString(i++, c.getFax());

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Customers'");
			}
			
			// Inserta los demograficos
			createDemograficos(connection, id, c.getDemograficos());
			
			c.setId(id); 
			
			return c;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public void update(Connection connection, Cliente c) 
			throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;
		try {
			// Borra los demograficos de este cliente
			deleteDemograficos(connection, c.getId());
			

			String queryString = 
					"UPDATE Customers " +
					"SET CompanyName = ? , ContactName = ? " +
					"WHERE CustomerID = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setString(i++, c.getNombreCompania());
			preparedStatement.setString(i++, c.getNombre());
			preparedStatement.setString(i++, c.getId());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(c.getId(), Cliente.class.getName());
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						c.getId() + "' in table 'Customers'");
			}     
			
			// Inserta los demograficos
			createDemograficos(connection, c.getId(), c.getDemograficos());

		} catch (SQLException e) {
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}              		
	}

	@Override
	public long delete(Connection connection, String id) 
			throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;

		try {
			
			deleteDemograficos(connection, id);

			String queryString =	
					  "DELETE FROM Customers " 
					+ "WHERE CustomerID = ? ";
			
			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setString(i++, id);

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(id,Cliente.class.getName());
			} 
			

			return removedRows;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
		
	/**
	 * Evita indexOf cada vez, simplemente con un marca booleana.
	 * @param queryString Consulta en elaboraci�n
	 * @param first Marca de primera clausula a�adida
	 * @param clause clausula a a�adir.
	 */
	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " AND ").append(clause);
	}
	
	private Cliente loadNext(Connection connection, ResultSet resultSet)
		throws SQLException, DataException {

			int i = 1;
			String customerId = resultSet.getString(i++);	                
			String companyName = resultSet.getString(i++);	                
			String contactName = resultSet.getString(i++);
			String contactTitle = resultSet.getString(i++);
			String address = resultSet.getString(i++);	                
			String city = resultSet.getString(i++);	                
			String region = resultSet.getString(i++);
			String postalCode = resultSet.getString(i++);
			String country = resultSet.getString(i++);	                
			String phone = resultSet.getString(i++);
			String fax = resultSet.getString(i++);
	
			Cliente e = new Cliente();		
			e.setId(customerId);
			e.setNombreCompania(companyName);
			e.setNombre(contactName);
			e.setTitulo(contactTitle);
			e.setDireccion(address);
			e.setCiudad(city);
			e.setRegion(region);
			e.setCodigoPostal(postalCode);
			e.setPais(country);
			e.setTelefono(phone);
			e.setFax(fax);
			
			// Nota Jose A: Esta estrategia de recuperaci�n genera N+1 consultas.
			// Hibernate permite otra estrategia de recuperacion m�s eficiente:
			// Ejecutar los JOIN y recuperar tambi�n la entidad
			// padre del resultSet de todos ellos, con lo que es solamente 1 consulta.
			
			List<Demografico> demograficos = demograficoDAO.findByCliente(connection, customerId);
			e.setDemograficos(demograficos);

			return e;
		}
		
	
	protected void createDemograficos(Connection connection, String clienteId,  List<Demografico> demograficos)
			throws SQLException, DataException {

		PreparedStatement preparedStatement = null;
		try {          
			String queryString = null;
			int i;
			for (Demografico d: demograficos) {
				queryString = "INSERT INTO CustomerCustomerDemo (CustomerId, CustomerTypeId) VALUES (?,  ?)";
				preparedStatement = connection.prepareStatement(queryString);				

				i = 1;     
				preparedStatement.setString(i++, clienteId);
				preparedStatement.setString(i++, d.getClienteTipoId());

				int insertedRows = preparedStatement.executeUpdate();

				if (insertedRows == 0) {
					throw new SQLException("Can not add row to table 'CustomerCustomerDemo'");
				}	

				JDBCUtils.closeStatement(preparedStatement);
			} 
		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	protected void deleteDemograficos(Connection c, String clienteId)
		throws SQLException, DataException {

		PreparedStatement preparedStatement = null;

		try {

			String queryString =	
					  "DELETE FROM CustomerCustomerDemo " 
					+ "WHERE CustomerID = ? ";
			
			preparedStatement = c.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setString(i++, clienteId);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
}