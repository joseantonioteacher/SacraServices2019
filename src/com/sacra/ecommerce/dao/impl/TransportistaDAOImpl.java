package com.sacra.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sacra.ecommerce.dao.TransportistaDAO;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Transportista;
import com.sacra.ecommerce.service.Results;

/**
 * 
 *
 * @author https://www.linkedin.com/in/joseantoniolopezperez
 * @version 0.2
 */
public class TransportistaDAOImpl implements TransportistaDAO {

	private static Logger logger = LogManager.getLogger(TransportistaDAOImpl.class.getName());
	
	public TransportistaDAOImpl() {		
	}
	
	@Override
	public Transportista findById(Connection connection, Long id) 
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          

			// Create "preparedStatement"
			String queryString = 
					"SELECT s.ShipperID, s.CompanyName, s.Phone " + 
							"FROM Shippers s  " +
							"WHERE s.ShipperID = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Fill "preparedStatement"
			int i = 1;                
			preparedStatement.setLong(i++, id);

			// Execute query            
			resultSet = preparedStatement.executeQuery();

			Transportista e = null;

			if (resultSet.next()) {
				e = loadNext(resultSet);				
			} else {
				throw new InstanceNotFoundException("Shipper with id " + id + 
						"not found", Transportista.class.getName());
			}

			return e;

		} catch (SQLException e) {
			logger.error("id: "+id, e);
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
			String queryString = "SELECT ShipperID" // Nada de *
					+ " FROM Shippers "
					+ " WHERE ShipperID = ? ";
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
			logger.error("id: "+id, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}

		return exist;
	}

	@Override
	public Results<Transportista> findAll(Connection connection, 
			int startIndex, int count) 
					throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// Create "preparedStatement"       
			String queryString = 
					"SELECT s.ShipperID, s.CompanyName, s.Phone " + 
					"FROM Shippers s  " +
					"ORDER BY s.CompanyName asc ";
			// Debatir sobre ordenacion por ... y opciones

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Execute query.
			resultSet = preparedStatement.executeQuery();

			// Recupera la pagina de resultados
			List<Transportista> pageEntites = new ArrayList<Transportista>();                        
			Transportista e = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					e = loadNext(resultSet);
					pageEntites.add(e);               	
					currentCount++;                	
				} while ((currentCount < count) && resultSet.next()) ;
			}
			
			
			int totalRows = JDBCUtils.getTotalRows(resultSet);
			
			if (logger.isDebugEnabled()) {
				logger.debug("Total rows: "+ totalRows + ". Query: "+queryString);
			}
			
			Results<Transportista> results = new Results<Transportista>(pageEntites, startIndex, totalRows);

			return results;

		} catch (SQLException e) {
			logger.error("startIndex: "+startIndex + ", count: "+count, e);
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
							+ " FROM Shippers";

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
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}	    	 
	}

	@Override
	public Results<Transportista> findByNombre(Connection connection, 
			String nombre, int startIndex, int count)
					throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// Create "preparedStatement"       
			String queryString = 
					"SELECT s.ShipperID, s.CompanyName, s.Phone " + 
							"FROM Shippers s  " +
							"WHERE UPPER(s.CompanyName) LIKE ? "+
							"ORDER BY s.CompanyName asc ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			if (logger.isDebugEnabled()) logger.debug("Query: "+queryString);
				 
			// Fill parameters
			int i = 1;                
			preparedStatement.setString(i++, "%" + nombre.toUpperCase() + "%");
			// Vease: SQL Injection:
			// http://en.wikipedia.org/wiki/SQL_injection
			// http://unixwiz.net/techtips/sql-injection.html


			// Execute query.
			resultSet = preparedStatement.executeQuery();
			
			
			// Recupera la pagina de resultados
			List<Transportista> pageEntities = new ArrayList<Transportista>();                        
			Transportista e = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					e = loadNext(resultSet);
					pageEntities.add(e);               	
					currentCount++;                	
				} while ((currentCount < count) && resultSet.next()) ;
			}
					
			int totalRows = JDBCUtils.getTotalRows(resultSet);			
			if (logger.isDebugEnabled()) {
				logger.debug("Total rows: "+ totalRows);
			}
			
			Results<Transportista> results = new Results<Transportista>(pageEntities, startIndex, totalRows); 
			return results;

		} catch (SQLException e) {
			logger.error("name: "+nombre + ", startIndex: "+startIndex + ", count: "+count, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}




	/**
	 * Factoriza la recuperación de todos los atributos de
	 * cada fila del ResultSet y el rellenado del objeto, 
	 * evitando la repetición del mismo código en todos los finders.
	 * Se requiere que los SELECT tengan todos los atributos 
	 * siempre en el mismo orden para su recuperación.
	 * De no ser así o bien obtener agregados (avg, min, max...)
	 * deberín implementar la recuperación en cada método. 
	 */
	private Transportista loadNext(ResultSet resultSet)
			throws SQLException {

		// Recupera los atributos asumiendo un orden comun
		int i = 1;
		Long shipperId = resultSet.getLong(i++);	                
		String companyName = resultSet.getString(i++);	                
		String phone = resultSet.getString(i++);

		// Rellena el objeto
		Transportista e = new Transportista();		
		e.setId(shipperId);
		e.setNombre(companyName);
		e.setTelefono(phone);
		return e;
	}


	@Override
	public Transportista create(Connection connection, Transportista t) 
			throws DuplicateInstanceException, DataException {

		// En esta tabla la PK es autogenerada.
		// Si no fuese asi deberíamos:
		// (1) Comprobar que no existe ya en BD con:
		//   if (exists(connection, t.getId())) {
		//         throw new DuplicateInstanceException(t.getId(), Transportista.class.getName());
		//   }
		// (2) Usar un par�metro más en el INSERT, para el ShipperID
		// (3) Establecer su valor al rellenar el preparedStatement

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {          

			// Create the "preparedStatement"
			String queryString = "INSERT INTO Shippers(CompanyName, Phone) "
					+ "VALUES (?, ?)";

			preparedStatement = connection.prepareStatement(queryString,
									Statement.RETURN_GENERATED_KEYS);

			// Fill the "preparedStatement"
			int i = 1;             
			preparedStatement.setString(i++, t.getNombre());
			preparedStatement.setString(i++, t.getTelefono());

			// Execute query
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Shippers'");
			} // else if (insertedRows > 1) ... // innecesario en este caso.

			// Recuperamos la PK generada
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Long pk = resultSet.getLong(1); 
				t.setId(pk);
			} else {
				throw new DataException("Unable to fetch autogenerated primary key");
			}

			// Return the DTO
			return t;

		} catch (SQLException e) {
			logger.error(""+t, e);			
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public void update(Connection connection, Transportista t) 
			throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;
		try {

			// Create "preparedStatement"
			String queryString = 
					"UPDATE Shippers " +
					"SET CompanyName = ? , Phone = ? " +
					"WHERE ShipperID = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			// Fill "preparedStatement"
			int i = 1;
			preparedStatement.setString(i++, t.getNombre());
			preparedStatement.setString(i++, t.getTelefono());
			preparedStatement.setLong(i++, t.getId());

			/* Execute update. */
			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(t.getId(), Transportista.class.getName());
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						t.getId() + "' in table 'Shippers'");
			}                          

		} catch (SQLException e) {
			logger.error(""+t, e);			
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
					  "DELETE FROM Shippers " 
					+ "WHERE ShipperID = ? ";
			
			preparedStatement = connection.prepareStatement(queryString);

			// Fill "preparedStatement"
			int i = 1;
			preparedStatement.setLong(i++, id);

			// Execute query
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(id,Transportista.class.getName());
			} 
			return removedRows;

		} catch (SQLException e) {
			logger.error("id: "+id, e);			
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}

	}

}	
