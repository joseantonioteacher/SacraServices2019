package com.sacra.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sacra.ecommerce.dao.RegionDAO;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Region;
import com.sacra.ecommerce.model.Transportista;

public class RegionDAOImpl implements RegionDAO {
	
	public RegionDAOImpl() {		
	}
	
	@Override
	
	public Region findById(Connection connection, Long id) 
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          

			// Create "preparedStatement"
			String queryString = 
					"SELECT r.RegionID, r.RegionDescription" + 
							"FROM Region r  " +
							"WHERE r.RegionID = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Fill "preparedStatement"
			int i = 1;                
			preparedStatement.setLong(i++, id);

			// Execute query            
			resultSet = preparedStatement.executeQuery();

			Region r = null;

			if (resultSet.next()) {
				r = loadNext(resultSet);				
			} else {
				throw new InstanceNotFoundException("Region with id " + id + 
						"not found", Region.class.getName());
			}

			return r;

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

			String queryString = 
					"SELECT r.RegionID, r.RegionDescription" + 
					"FROM Region r  " +
					"WHERE r.RegionID = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				exist = true;
			}

		} catch (SQLException r) {
			throw new DataException(r);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}

		return exist;
	}
	
	private Region loadNext(ResultSet resultSet)
			throws SQLException {


		int i = 1;
		Long id = resultSet.getLong(i++);	                
		String regionDescripcion = resultSet.getString(i++);	                

		Region r = new Region();		
		r.setId(id);
		r.setRegion(regionDescripcion);

		return r;
	}
	
	@Override
	public List<Region> findAll(Connection connection, 
			int startIndex, int count) 
					throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// Create "preparedStatement"       
			String queryString = 
					"SELECT r.RegionID, r.RegionDescription " + 
					"FROM Region r  " +
					"ORDER BY r.RegionID asc ";
			// Debatir sobre ordenacion por ... y opciones

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Execute query.
			resultSet = preparedStatement.executeQuery();

			// Recupera la pagina de resultados
			List<Region> results = new ArrayList<Region>();                        
			Region r = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					r = loadNext(resultSet);
					results.add(r);               	
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
							+ " FROM Region";

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
	public Region create(Connection connection, Region r) 
			throws DuplicateInstanceException, DataException {


		PreparedStatement preparedStatement = null;
		try {          

			String queryString = "INSERT INTO Region(RegionID, RegionDescription) "
					+ "VALUES (?, ?)";

			preparedStatement = connection.prepareStatement(queryString);

			
			int i = 1;             
			preparedStatement.setLong(i++, r.getId());
			preparedStatement.setString(i++, r.getRegion());

			// Execute query
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Region'");
			} 


			
			return r;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	@Override
	public void update(Connection connection, Region r) 
			throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;
		try {

			// Create "preparedStatement"
			String queryString = 
					"UPDATE Region " +
					"SET RegionDescription = ? " +
					"WHERE RegionID = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			// Fill "preparedStatement"
			int i = 1;

			preparedStatement.setLong(i++, r.getId());
			preparedStatement.setString(i++, r.getRegion());

			/* Execute update. */
			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(r.getId(), Region.class.getName());
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						r.getId() + "' in table 'Region'");
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
					  "DELETE FROM Region " 
					+ "WHERE RegionID = ? ";
			
			preparedStatement = connection.prepareStatement(queryString);

			// Fill "preparedStatement"
			int i = 1;
			preparedStatement.setLong(i++, id);

			// Execute query
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(id,Region.class.getName());
			} 
			return removedRows;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}

	}
	
	
}


	
