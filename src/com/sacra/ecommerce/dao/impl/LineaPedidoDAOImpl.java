package com.sacra.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sacra.ecommerce.dao.LineaPedidoDAO;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Cliente;
import com.sacra.ecommerce.model.LineaPedido;
import com.sacra.ecommerce.model.LineaPedidoId;

public class LineaPedidoDAOImpl implements LineaPedidoDAO {
	
	public LineaPedidoDAOImpl() {}
	
	@Override
	public LineaPedido findById(Connection connection, LineaPedidoId id) 
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT o.OrderID, o.ProductID, o.UnitPrice, o.Quantity, o.Discount " + 
							"FROM OrderDetails o  " +
							"WHERE o.OrderID = ? AND o.ProductID = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, id.getIdPedido());
			preparedStatement.setLong(i++, id.getIdProducto());

			resultSet = preparedStatement.executeQuery();

			LineaPedido lp = null;

			if (resultSet.next()) {
				lp = loadNext(connection, resultSet);				
			} else {
				throw new InstanceNotFoundException("OrderDetails not found", Cliente.class.getName());
			}

			return lp;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  
	}
	
	@Override
	public Boolean exists(Connection connection, LineaPedidoId id) 
			throws DataException {
		
		boolean exist = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					"SELECT od.OrderID, od.ProductID " + 
							"FROM OrderDetails od " +
							"WHERE od.OrderID = ? AND od.ProductID = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id.getIdPedido());
			preparedStatement.setLong(i++, id.getIdProducto());

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
	public List<LineaPedido> findByPedido (Connection connection, Long idPedido)
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					"SELECT od.OrderID, od.ProductID, od.UnitPrice, od.Quantity, od.Discount " + 
					"FROM OrderDetails od  " +
						"INNER JOIN Orders o "+
						"ON od.OrderID = o.OrderID AND o.OrderID = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, idPedido);

			resultSet = preparedStatement.executeQuery();

			List<LineaPedido> results = new ArrayList<LineaPedido>();  
			
			LineaPedido lp = null;
			
			while (resultSet.next()) {
				lp = loadNext (connection,resultSet);
				results.add(lp);
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
	public LineaPedido create(Connection connection, LineaPedido lp) 
			throws DuplicateInstanceException, DataException {

		PreparedStatement preparedStatement = null;
		
		LineaPedidoId id = new LineaPedidoId();
		id.setIdPedido(lp.getIdPedido());
		id.setIdProducto(lp.getIdProducto());
		
		try {          
			
			if (exists(connection, id)) {
				throw new DuplicateInstanceException(id, LineaPedido.class.getName());
			}
						
			String queryString = "INSERT INTO OrderDetails(OrderID,ProductID,UnitPrice,Quantity,Discount) "
					+ "VALUES (?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(queryString);
			
			int i = 1;     
			preparedStatement.setLong(i++,lp.getIdPedido());
			preparedStatement.setLong(i++,lp.getIdProducto());
			preparedStatement.setDouble(i++,lp.getPrecioUnitario());
			preparedStatement.setInt(i++, lp.getCantidad());
			preparedStatement.setDouble(i++, lp.getDescuento());
			
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Customers'");
			}
			
			return lp;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}


	@Override
	public long delete(Connection connection, LineaPedidoId id) 
			throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;

		try {

			String queryString =	
					  "DELETE FROM OrderDetails " 
					+ "WHERE OrderID = ? AND ProductID = ? ";
			
			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id.getIdPedido());
			preparedStatement.setLong(i++,id.getIdProducto());

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(id,LineaPedido.class.getName());
			} 
					
			return removedRows;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	
	private LineaPedido loadNext(Connection connection, ResultSet resultSet)
		throws SQLException, DataException {

			int i = 1;
			Long idPedido = resultSet.getLong(i++);	                
			Long idProducto = resultSet.getLong(i++);	                
			Double precioUnitario = resultSet.getDouble(i++);
			Integer cantidad = resultSet.getInt(i++);
			Double descuento = resultSet.getDouble(i++);	                
	
			LineaPedido lp = new LineaPedido();		
			lp.setIdPedido(idPedido);
			lp.setIdProducto(idProducto);
			lp.setPrecioUnitario(precioUnitario);
			lp.setCantidad(cantidad);
			lp.setDescuento(descuento);
			
			return lp;
		}

}
