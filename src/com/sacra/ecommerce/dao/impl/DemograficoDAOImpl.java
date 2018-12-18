/**
 * 
 */
package com.sacra.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sacra.ecommerce.dao.DemograficoDAO;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Demografico;

/**
 * @author hector.ledo.doval
 *
 */

public class DemograficoDAOImpl implements DemograficoDAO{

public DemograficoDAOImpl() {}
	
	@Override
	public Demografico findById(Connection connection, String id) 
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT d.CustomerTypeID, d.CustomerDesc " + 
							"FROM customerdemographics d " +
							"WHERE d.CustomerTypeID = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setNString(i++, id);

			resultSet = preparedStatement.executeQuery();

			Demografico e = null;

			if (resultSet.next()) {
				e = loadNext(resultSet);				
			} else {
				throw new InstanceNotFoundException("Customer with id " + id + 
						"not found", Demografico.class.getName());
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
					"SELECT d.CustomerTypeID, d.CustomerDesc " + 
							"FROM customerdemographics d " +
							"WHERE d.CustomerTypeID = ? ";

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
	public List<Demografico> findAll(Connection connection,int startIndex, int count) 
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					"SELECT d.CustomerTypeID, d.CustomerDesc " + 
					"FROM customerdemographics d " +
					"ORDER BY d.CustomerTypeID ASC";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();

			List<Demografico> results = new ArrayList<Demografico>();                        
			Demografico e = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					e = loadNext(resultSet);
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
	
	 public List<Demografico> findByCliente(Connection connection, String clienteID) 
	        	throws DataException {
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {

				String queryString = 
						"SELECT d.CustomerTypeID, d.CustomerDesc " + 
						"FROM CustomerDemographics d " 
							+ " INNER JOIN CustomerCustomerDemo ccd ON d.CustomerTypeId = ccd.CustomerTypeId " 
							+ " INNER JOIN Customers c " 
							+ " 	ON c.CustomerId = ccd.CustomerId AND c.CustomerId = ? ";
										
				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				int i = 1;
				preparedStatement.setNString(i++, clienteID);
				
				resultSet = preparedStatement.executeQuery();

				List<Demografico> results = new ArrayList<Demografico>();
				
				Demografico e = null;
				
				while (resultSet.next()) {
						results.add(e);               	
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
							+ " FROM CustomersDemographics";

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
	public List<Demografico> findByNombre(Connection connection,String nombre, int startIndex, int count)
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					"SELECT d.CustomerTypeID, d.CustomerDesc " + 
					"FROM customerdemographics d " +
					"WHERE UPPER(d.CustomerTypeID) LIKE ? "+
					"ORDER BY d.CustomerTypeID ASC ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

						int i = 1;                
			preparedStatement.setString(i++, "%" + nombre.toUpperCase() + "%");

			resultSet = preparedStatement.executeQuery();

			List<Demografico> results = new ArrayList<Demografico>();                        
			Demografico e = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					e = loadNext(resultSet);
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
	
	
	
	private Demografico loadNext(ResultSet resultSet)
		throws SQLException {

			int i = 1;
			String CustomerTypeID = resultSet.getString(i++);	                
			String CustomerDesc = resultSet.getString(i++);	                

	
			Demografico e = new Demografico();		
			e.setClienteTipoId(CustomerTypeID);
			e.setClienteDescripcion(CustomerDesc);
			
			return e;
		}
	

	@Override
	public Demografico create(Connection connection, Demografico c) 
			throws DuplicateInstanceException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {          
			
			//La generacion de clave primaria esta mal!!!!!!!
			//Check if the primary key already exists
			if (exists(connection, c.getClienteTipoId())) {
				throw new DuplicateInstanceException(c.getClienteTipoId(), Demografico.class.getName());
			}
			
			String queryString = "INSERT INTO CustomerDemographics(CustomerTypeID, CustomerDesc) "
					+ "VALUES (?, ?)";

			preparedStatement = connection.prepareStatement(queryString,
									Statement.RETURN_GENERATED_KEYS);
			
			int i = 1;     
			preparedStatement.setString(i++, c.getClienteTipoId());
			preparedStatement.setString(i++, c.getClienteDescripcion());

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'CustomersDemographics'");
			}
			
			return c;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public void update(Connection connection, Demografico d) 
			throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;
		try {

			String queryString = 
					"UPDATE CustomerDemographics " +
					"SET CustomerDesc = ? " +
					"WHERE CustomerTypeID = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setString(i++, d.getClienteDescripcion());
			preparedStatement.setString(i++, d.getClienteTipoId());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(d.getClienteTipoId(), Demografico.class.getName());
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						d.getClienteTipoId() + "' in table 'CustomerDemographics'");
			}                          

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

			String queryString =	
					  "DELETE FROM Customerdemographics " 
					+ "WHERE CustomerTypeID = ? ";
			
			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setString(i++, id);

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(id,Demografico.class.getName());
			} 
			return removedRows;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}

	}
}
