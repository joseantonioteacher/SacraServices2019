package com.sacra.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sacra.ecommerce.dao.CategoriaDAO;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Categoria;
import com.sacra.ecommerce.service.CategoriaCriteria;


public class CategoriaDAOImpl implements CategoriaDAO {

	public CategoriaDAOImpl() {		
	}
	
	@Override
	public Categoria findById(Connection connection, Long id) 
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          

			String queryString = 
					"SELECT cat.CategoryID, cat.CategoryName, cat.Description " + 
							"FROM Categories cat  " +
							"WHERE cat.CategoryID = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, id);

			// Execute query            
			resultSet = preparedStatement.executeQuery();

			Categoria e = null;

			if (resultSet.next()) {
				e = loadNext(connection, resultSet);				
			} else {
				throw new InstanceNotFoundException("Categories with id " + id + 
						"not found", Categoria.class.getName());
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
	public Boolean exists(Connection connection, Long id) 
			throws DataException {
		boolean exist = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT CategoryID" // Nada de *
					+ " FROM Categories "
					+ " WHERE CategoryID = ? ";
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
	public List<Categoria> findAll(Connection connection, 
			int startIndex, int count) 
					throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					"SELECT cat.CategoryID, cat.CategoryName, cat.Description " + 
					"FROM Categories cat  " +
					"ORDER BY cat.CategoryName asc ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();

			List<Categoria> results = new ArrayList<Categoria>();                        
			Categoria e = null;
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
							+ " FROM Categories";

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
	public List<Categoria> findByNombre(Connection connection, 
			String nombre, int startIndex, int count)
					throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// Create "preparedStatement"       
			String queryString = 
					"SELECT cat.CategoryID, cat.CategoryName, cat.Description " + 
							"FROM Categories cat  " +
							"WHERE UPPER(cat.CategoryName) LIKE ? "+
							"ORDER BY cat.CategoryName asc ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setString(i++, "%" + nombre.toUpperCase() + "%");


			resultSet = preparedStatement.executeQuery();

			List<Categoria> results = new ArrayList<Categoria>();                        
			Categoria e = null;
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
	public List<Categoria> findByCriteria(Connection connection, CategoriaCriteria categoria, int startIndex, int count) throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
    
			queryString = new StringBuilder(
					"SELECT cat.CategoryID, cat.CategoryName, cat.Description " + 
					" FROM Categories cat ");
			
			boolean first = true;
			
			if (categoria.getNombre()!=null) {
				addClause(queryString, first, " UPPER(cat.CategoryID) LIKE ? ");
				first = false;
			}
			
			if (categoria.getDescripcion()!=null) {
				addClause(queryString, first, " UPPER(cat.CategoryName) LIKE ? ");
				first = false;		
			
			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;       
			
			preparedStatement.setLong(i++, cc.getId());
			preparedStatement.setString(i++, "%" +  cc.getNombre() + "%");

			resultSet = preparedStatement.executeQuery();
			
			List<Categoria> results = new ArrayList<Categoria>();                        
			Categoria e = null;
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
	
	

	private Categoria loadNext(Connection connection, ResultSet resultSet) throws SQLException, DataException {


		int i = 1;
		Long categoryId = resultSet.getLong(i++);	                
		String categoryName = resultSet.getString(i++);	                
		String description = resultSet.getString(i++);

		Categoria e = new Categoria();		
		e.setId(categoryId);
		e.setNombre(categoryName);
		e.setDescripcion(description);
		return e;
	}


	@Override
	public Categoria create(Connection connection, Categoria cat) 
			throws DuplicateInstanceException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {          

			String queryString = "INSERT INTO Categories( CategoryName, Description) "
					+ "VALUES (?, ?)";

			preparedStatement = connection.prepareStatement(queryString,Statement.RETURN_GENERATED_KEYS);

			int i = 1;             
			preparedStatement.setString(i++, cat.getNombre());
			preparedStatement.setString(i++, cat.getDescripcion());

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Categories'");
			} 

			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Long pk = resultSet.getLong(1); 
				cat.setId(pk);
			} else {
				throw new DataException("Unable to fetch autogenerated primary key");
			}

			return cat;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public void update(Connection connection, Categoria cat) 
			throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;
		try {

			String queryString = 
					"UPDATE Categories " +
					"SET CategoryName = ? , Description = ? " +
					"WHERE CategoryID = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setString(i++, cat.getNombre());
			preparedStatement.setString(i++, cat.getDescripcion());
			preparedStatement.setLong(i++, cat.getId());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(cat.getId(), Categoria.class.getName());
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						cat.getId() + "' in table 'Categories'");
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

			String queryString =	
					  "DELETE FROM Categories " 
					+ "WHERE CategoryID = ? ";
	
			
			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id);

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(id,Categoria.class.getName());
			} 
			return removedRows;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}

	}
	
	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " AND ").append(clause);
	}

	
	
}