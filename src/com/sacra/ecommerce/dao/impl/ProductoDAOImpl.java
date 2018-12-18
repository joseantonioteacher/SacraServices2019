package com.sacra.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sacra.ecommerce.dao.ProductoDAO;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Producto;
import com.sacra.ecommerce.service.ProductoCriteria;

public class ProductoDAOImpl implements ProductoDAO {

	public ProductoDAOImpl() {		
	}
	
	@Override
	public Boolean exists(Connection connection, Long id) 
			throws DataException {
		boolean exist = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = "SELECT ProductID"
					+ " FROM Products "
					+ " WHERE ProductID = ? ";
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
	public List<Producto> findAll(Connection connection, int startIndex, int count) throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// Create "preparedStatement"       
			String queryString = 
					"SELECT pro.ProductID, pro.ProductName, pro.SupplierID, pro.CategoryID, pro.QuantityPerUnit, pro.UnitPrice, pro.UnitsInStock, pro.UnitsOnOrder, pro.ReorderLevel, pro.Discontinued " + 
							"FROM Products pro  " +
							"ORDER BY pro.ProductName asc ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Execute query.
			resultSet = preparedStatement.executeQuery();

			// Recupera la pagina de resultados
			List<Producto> results = new ArrayList<Producto>();                        
			Producto pro = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					pro = loadNext(connection, resultSet);
					results.add(pro);               	
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
							+ " FROM Products";

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
	public Producto findById(Connection connection, Long id) 
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          

			String queryString = 
					"SELECT pro.ProductID, pro.ProductName, pro.SupplierID, pro.CategoryID, pro.QuantityPerUnit, pro.UnitPrice, pro.UnitsInStock, pro.UnitsOnOrder, pro.ReorderLevel, pro.Discontinued " + 
							"FROM Products pro  " +
							"WHERE pro.ProductID = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, id);

			// Execute query            
			resultSet = preparedStatement.executeQuery();

			Producto e = null;

			if (resultSet.next()) {
				e = loadNext(connection, resultSet);				
			} else {
				throw new InstanceNotFoundException("Products with id " + id + 
						"not found", Producto.class.getName());
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
	public List<Producto> findByNombre(Connection connection, String nombre, int startIndex, int count)throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					"SELECT pro.ProductID, pro.ProductName, pro.SupplierID, pro.CategoryID, pro.QuantityPerUnit, pro.UnitPrice, pro.UnitsInStock, pro.UnitsOnOrder, pro.ReorderLevel, pro.Discontinued " + 
							"FROM Products pro " +
							"WHERE UPPER(pro.ProductName) LIKE ? "+
							"ORDER BY pro.ProductName asc ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setString(i++, "%" + nombre.toUpperCase() + "%");


			resultSet = preparedStatement.executeQuery();

			List<Producto> results = new ArrayList<Producto>();                        
			Producto e = null;
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
	public List<Producto> findByCategoria(Connection connection, int idCategoria, int startIndex, int count) throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					"SELECT pro.ProductID, pro.ProductName, pro.SupplierID, pro.CategoryID, pro.QuantityPerUnit, pro.UnitPrice, pro.UnitsInStock, pro.UnitsOnOrder, pro.ReorderLevel, pro.Discontinued " + 
							"FROM Products pro " +
							"WHERE pro.CategoryID LIKE ? "+
							"ORDER BY pro.ProductName asc ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, idCategoria);


			resultSet = preparedStatement.executeQuery();

			List<Producto> results = new ArrayList<Producto>();                        
			Producto e = null;
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
	public List<Producto> findByCriteria(Connection connection, ProductoCriteria pc, int startIndex, int count) throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
    
			queryString = new StringBuilder(
					"SELECT pro.ProductID, pro.ProductName, pro.SupplierID, pro.CategoryID, pro.QuantityPerUnit, pro.UnitPrice, pro.UnitsInStock, pro.UnitsOnOrder, pro.ReorderLevel, pro.Discontinued " + 
					" FROM Products pro ");
			
			boolean first = true;
			
			if (pc.getNombre()!=null) {
				addClause(queryString, first, " UPPER(pro.ProductName) LIKE ? ");
				first = false;
			}
			
			if (pc.getUnidadesEnStock()!=null) {
				addClause(queryString, first, " UPPER(pro.UnitsInStock) LIKE ? ");
				first = false;
			}
			
			/*
			if (pc.getPrecioUnitario()!=null) {
				addClause(queryString, first, " UPPER(pro.UnitPrice) LIKE ? ");
				first = false;
			}
			 */
			
			if (pc.getIdProveedor()!=null) {
				addClause(queryString, first, " UPPER(pro.SupplierID) LIKE ? ");
				first = false;
			}			
			
			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;       
			
			preparedStatement.setString(i++, "%" +  pc.getNombre() + "%");
			preparedStatement.setInt(i++, pc.getUnidadesEnStock());
			//preparedStatement.setDouble(i++, pc.getPrecioUnitario());
			preparedStatement.setInt(i++, pc.getIdProveedor());

			resultSet = preparedStatement.executeQuery();
			
			List<Producto> results = new ArrayList<Producto>();                        
			Producto e = null;
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
	public Producto create(Connection connection, Producto pro) throws DuplicateInstanceException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {          

			String queryString = "INSERT INTO Products( ProductName, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel)"
					+ "VALUES (?, ?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);

			int i = 1;             
			preparedStatement.setString(i++, pro.getNombre());
			preparedStatement.setString(i++, pro.getCantidadPorUnidad());
			preparedStatement.setDouble(i++, pro.getPrecioUnitario());
			preparedStatement.setInt(i++, pro.getUnidadesEnStock());
			preparedStatement.setInt(i++, pro.getUnidadesEnPedido());
			preparedStatement.setInt(i++, pro.getNivelDeReordenacion());


			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Products'");
			} 

			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Long pk = resultSet.getLong(1); 
				pro.setId(pk);
			} else {
				throw new DataException("Unable to fetch autogenerated primary key");
			}

			return pro;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public void update(Connection connection, Producto pro) throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;
		try {

			String queryString = 
					"UPDATE Products " +
					"SET ProductName = ? ,  QuantityPerUnit = ?, UnitPrice = ?, UnitsInStock = ? , UnitsOnOrder = ?, ReorderLevel = ? " +
					"WHERE ProductID = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setString(i++, pro.getNombre());
			preparedStatement.setString(i++, pro.getCantidadPorUnidad());
			preparedStatement.setDouble(i++, pro.getPrecioUnitario());
			preparedStatement.setInt(i++, pro.getUnidadesEnStock());
			preparedStatement.setInt(i++, pro.getUnidadesEnPedido());
			preparedStatement.setInt(i++, pro.getNivelDeReordenacion());
			preparedStatement.setLong(i++, pro.getId());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(pro.getId(), Producto.class.getName());
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						pro.getId() + "' in table 'Products'");
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
					  "DELETE FROM Products " 
					+ "WHERE ProductID = ? ";
	
			
			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id);

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(id,Producto.class.getName());
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

	private Producto loadNext(Connection connection, ResultSet resultSet) throws SQLException, DataException {


		
		int i = 1;
		Long ProductId = resultSet.getLong(i++);	                
		String ProductName = resultSet.getString(i++);	                
		Integer SupplierID = resultSet.getInt(i++);
		Integer CategoryID = resultSet.getInt(i++);	
		String QuantityPerUnit = resultSet.getString(i++);	
		Double UnitPrice = resultSet.getDouble(i++);	
		Integer UnitsInStock = resultSet.getInt(i++);
		Integer UnitsOnOrder = resultSet.getInt(i++);
		Integer ReorderLevel = resultSet.getInt(i++);
		Boolean Discontinued = resultSet.getBoolean(i++);
		
		

		Producto e = new Producto();		
		e.setId(ProductId);
		e.setNombre(ProductName);
		e.setIdProveedor(SupplierID);
		e.setIdCategoria(CategoryID);
		e.setCantidadPorUnidad(QuantityPerUnit);
		e.setPrecioUnitario(UnitPrice);
		e.setUnidadesEnStock(UnitsInStock);
		e.setUnidadesEnPedido(UnitsOnOrder);
		e.setNivelDeReordenacion(ReorderLevel);
		e.setDescontinuado(Discontinued);		

		return e;
	}
	
	
}