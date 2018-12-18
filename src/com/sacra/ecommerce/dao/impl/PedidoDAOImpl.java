package com.sacra.ecommerce.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sacra.ecommerce.dao.LineaPedidoDAO;
import com.sacra.ecommerce.dao.PedidoDAO;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Cliente;
import com.sacra.ecommerce.model.LineaPedido;
import com.sacra.ecommerce.model.Pedido;
import com.sacra.ecommerce.service.PedidoCriteria;

public class PedidoDAOImpl implements PedidoDAO {

	private static Logger logger = LogManager.getLogger(PedidoDAOImpl.class.getName());
	
	private LineaPedidoDAO lineaPedidoDAO = null;
	
	public PedidoDAOImpl() {
		lineaPedidoDAO  = new LineaPedidoDAOImpl();
	}
	
	@Override
	public Pedido findById(Connection connection, Long id) 
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT o.OrderID, o.CustomerID, o.EmployeeID, o.OrderDate, o.RequiredDate, o.ShippedDate, o.ShipVia, o.Freight, o.ShipName, "
							+ "o.ShipAddress, o.ShipCity, o.ShipRegion, o.ShipPostalCode, o.ShipCountry " 
							+ "FROM Orders o  " +
							"WHERE o.OrderID = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, id);

			resultSet = preparedStatement.executeQuery();

			Pedido p = null;

			if (resultSet.next()) {
				p = loadNext(connection, resultSet);				
			} else {
				throw new InstanceNotFoundException("Order with id " + id + 
						"not found", Pedido.class.getName());
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

			String queryString = 
					"SELECT o.OrderID " + 
					"FROM Orders o  " +
					"WHERE o.OrderID = ? ";

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
	public List<Pedido> findAll(Connection connection,int startIndex, int count) 
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

		String queryString = 
			"SELECT o.OrderID, o.CustomerID, o.EmployeeID, o.OrderDate, o.RequiredDate, o.ShippedDate, o.ShipVia, o.Freight, o.ShipName, "
			+ "o.ShipAddress, o.ShipCity, o.ShipRegion, o.ShipPostalCode, o.ShipCountry "  
			+ "FROM Orders o  " 
			+ "ORDER BY o.OrderDate DESC";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();

			List<Pedido> results = new ArrayList<Pedido>();                        
			Pedido p = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					p = loadNext(connection, resultSet);
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

			String queryString = 
					" SELECT count(*) "
							+ " FROM Orders ";

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
	public List<Pedido> findByCriteria(Connection connection, PedidoCriteria criteria, int startIndex, int count)
			throws DataException {				
				
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
    
			queryString = new StringBuilder(
					"SELECT o.OrderID, o.CustomerID, o.EmployeeID, o.OrderDate, o.RequiredDate, o.ShippedDate, o.ShipVia, o.Freight, o.ShipName, "
					+ "o.ShipAddress, o.ShipCity, o.ShipRegion, o.ShipPostalCode, o.ShipCountry " 
					+ " FROM Orders asdf asd o ");
			
			// Marca (flag) de primera clausula, que se desactiva en la primera
			boolean first = true;
			
			if (criteria.getCarga()!=null) {
				addClause(queryString, first, " o.Freight = ? ");
				first = false;
			}
			
			if (criteria.getCiudadReceptor()!=null) {
				addClause(queryString, first, " UPPER(o.ShipCity) LIKE ? ");
				first = false;
			}

			if (criteria.getCodigoPostalReceptor()!=null) {
				addClause(queryString, first, " UPPER(o.ShipPostalCode) LIKE ? ");
				first = false;
			}			
			
			if (criteria.getComunidadReceptor()!=null) {
				addClause(queryString, first, " UPPER(o.ShipRegion) LIKE ? ");
				first = false;
			}
			
			if (criteria.getDireccionReceptor()!=null) {
				addClause(queryString, first, " UPPER(o.ShipAddress) LIKE ? ");
				first = false;
			}

			if (criteria.getFechaEntregaObligatoria()!=null) {
				addClause(queryString, first, " o.RequiredDate = ? ");
				first = false;
			}	
			
			if (criteria.getFechaEnvio()!=null) {
				addClause(queryString, first, " o.ShippedDate = ? ");
				first = false;
			}
			
			if (criteria.getFechaPedido()!=null) {
				addClause(queryString, first, " o.OrderDate = ? ");
				first = false;
			}

			if (criteria.getIdCliente()!=null) {
				addClause(queryString, first, " UPPER(o.CustomerID) LIKE ? ");
				first = false;
			}	
			
			if (criteria.getIdEmpleado()!=null) {
				addClause(queryString, first, " o.EmployeeID = ? ");
				first = false;
			}
			
			if (criteria.getIdPedido()!=null) {
				addClause(queryString, first, " o.OrderID = ? ");
				first = false;
			}

			if (criteria.getIdTransportista()!=null) {
				addClause(queryString, first, " o.ShipVia = ? ");
				first = false;
			}
			
			if (criteria.getNombreReceptor()!=null) {
				addClause(queryString, first, " UPPER(o.ShipName) LIKE ? ");
				first = false;
			}
			
			if(criteria.getPaisReceptor()!=null) {
				addClause(queryString, first, " UPPER(o.ShipCountry) LIKE ? ");
			}
			
			
			
			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;       
			
			if (criteria.getCarga()!=null) 
				preparedStatement.setDouble(i++, criteria.getCarga());
			if (criteria.getCiudadReceptor()!=null) 
				preparedStatement.setString(i++,criteria.getCiudadReceptor());
			if (criteria.getCodigoPostalReceptor()!=null)
				preparedStatement.setString(i++,criteria.getCodigoPostalReceptor());
			if (criteria.getComunidadReceptor()!=null) 
				preparedStatement.setString(i++,criteria.getComunidadReceptor());
			if (criteria.getDireccionReceptor()!=null) 
				preparedStatement.setString(i++,criteria.getDireccionReceptor());
			if (criteria.getFechaEntregaObligatoria()!=null) 
				preparedStatement.setDate(i++, new java.sql.Date(criteria.getFechaEntregaObligatoria().getTime()));
			if (criteria.getFechaEnvio()!=null) 
				preparedStatement.setDate(i++, new java.sql.Date(criteria.getFechaEnvio().getTime()));
			if (criteria.getFechaPedido()!=null) 
				preparedStatement.setDate(i++, new java.sql.Date(criteria.getFechaPedido().getTime()));
			if (criteria.getIdCliente()!=null)
				preparedStatement.setString(i++,criteria.getIdCliente());
			if (criteria.getIdEmpleado()!=null) 
				preparedStatement.setLong(i++, criteria.getIdEmpleado());
			if (criteria.getIdPedido()!=null)
				preparedStatement.setLong(i++, criteria.getIdPedido());
			if (criteria.getIdTransportista()!=null) 
				preparedStatement.setLong(i++, criteria.getIdTransportista());
			if (criteria.getNombreReceptor()!=null) 
				preparedStatement.setString(i++,criteria.getNombreReceptor());
			if (criteria.getPaisReceptor()!=null)
				preparedStatement.setString(i++,criteria.getPaisReceptor());

			resultSet = preparedStatement.executeQuery();
			
			List<Pedido> results = new ArrayList<Pedido>();                        
			Pedido p = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					p = loadNext(connection, resultSet);
					results.add(p);               	
					currentCount++;                	
				} while ((currentCount < count) && resultSet.next()) ;
			}

			return results;
	
			} catch (SQLException e) {
				logger.error(" Criteria = "+ ToStringBuilder.reflectionToString(criteria), e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeResultSet(resultSet);
				JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	
	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " AND ").append(clause);
	}
	
	private Pedido loadNext(Connection connection, ResultSet resultSet)
		throws SQLException, DataException {

			int i = 1;
			Long orderId = resultSet.getLong(i++);	                
			String customerId = resultSet.getString(i++);	                
			Long employeeId = resultSet.getLong(i++);
			Date orderDate = resultSet.getDate(i++);
			Date requiredDate = resultSet.getDate(i++);	                
			Date shippedDate = resultSet.getDate(i++);	                
			Long shipVia = resultSet.getLong(i++);
			Double freight = resultSet.getDouble(i++);
			String shipName = resultSet.getString(i++);	                
			String shipAddress = resultSet.getString(i++);
			String shipCity = resultSet.getString(i++);
			String shipRegion = resultSet.getString(i++);
			String shipPostalCode = resultSet.getString(i++);
			String shipCountry = resultSet.getString(i++);
	
			Pedido p = new Pedido();		
			p.setCarga(freight);
			p.setCiudadReceptor(shipCity);
			p.setCodigoPostalReceptor(shipPostalCode);
			p.setComunidadReceptor(shipRegion);
			p.setDireccionReceptor(shipAddress);
			p.setFechaEntregaObligatoria(requiredDate);
			p.setFechaEnvio(shippedDate);
			p.setFechaPedido(orderDate);
			p.setIdCliente(customerId);
			p.setIdEmpleado(employeeId);
			p.setIdPedido(orderId);
			p.setIdTransportista(shipVia);
			p.setNombreReceptor(shipName);
			p.setPaisReceptor(shipCountry);
			
			
			List<LineaPedido> lineasPedido = lineaPedidoDAO.findByPedido(connection, orderId);
			p.setLineas(lineasPedido);

			return p;
		}
	

	@Override
	public Pedido create(Connection connection, Pedido p) 
			throws DuplicateInstanceException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {          
			
			String queryString = "INSERT INTO Orders(CustomerID, EmployeeID, OrderDate, RequiredDate, ShippedDate, ShipVia, Freight, ShipName, ShipAddress,"
					+ " ShipCity, ShipRegion, ShipPostalCode, ShipCountry) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(queryString,
					Statement.RETURN_GENERATED_KEYS);
			
			int i = 1;     
			preparedStatement.setString(i++,p.getIdCliente());
			preparedStatement.setLong(i++, p.getIdEmpleado());
			preparedStatement.setDate(i++, new java.sql.Date(p.getFechaPedido().getTime()));
			preparedStatement.setDate(i++, new java.sql.Date(p.getFechaEntregaObligatoria().getTime()));
			preparedStatement.setDate(i++, new java.sql.Date(p.getFechaEnvio().getTime()));
			preparedStatement.setLong(i++, p.getIdTransportista());
			preparedStatement.setDouble(i++, p.getCarga());
			preparedStatement.setString(i++,p.getNombreReceptor());
			preparedStatement.setString(i++,p.getDireccionReceptor());
			preparedStatement.setString(i++,p.getCiudadReceptor());
			preparedStatement.setString(i++,p.getComunidadReceptor());
			preparedStatement.setString(i++,p.getCodigoPostalReceptor());
			preparedStatement.setString(i++,p.getPaisReceptor());

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Orders'");
			} 
			
			
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Long pk = resultSet.getLong(1); 
				p.setIdPedido(pk);
			} else {
				throw new DataException("Unable to fetch autogenerated primary key");
			}
			
			List<LineaPedido> lineas = p.getLineas();
			for (LineaPedido lp: lineas) {
				lp.setIdPedido(p.getIdPedido());
				lineaPedidoDAO.create(connection, lp);
			}
			
			return p;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	public void update(Connection connection, Pedido p) 
			throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;
		try {
			
			deleteLineasPedido(connection, p.getIdPedido());
			
			String queryString = 
					"UPDATE Orders " +
					"SET ShipName = ? , ShipAddress = ?, ShipCity = ?, ShipRegion = ?, ShipPostalCode = ?, ShipCountry = ? " +
					"WHERE OrderID = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setString(i++, p.getNombreReceptor());
			preparedStatement.setString(i++, p.getDireccionReceptor());
			preparedStatement.setString(i++, p.getCiudadReceptor());
			preparedStatement.setString(i++,p.getComunidadReceptor());
			preparedStatement.setString(i++, p.getCodigoPostalReceptor());
			preparedStatement.setString(i++,p.getPaisReceptor());
			preparedStatement.setDouble(i++, p.getIdPedido());
			
			

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(p.getIdPedido(), Pedido.class.getName());
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						p.getIdPedido() + "' in table 'Orders'");
			}     
			
			createLineasPedido(connection, p.getIdPedido(), p.getLineas());

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
			
			deleteLineasPedido (connection, id);

			String queryString =	
					  "DELETE FROM Orders " 
					+ "WHERE OrderID = ? ";
			
			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id);

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
	
	protected void deleteLineasPedido(Connection c, Long pedidoId)
			throws SQLException, DataException {

			PreparedStatement preparedStatement = null;

			try {

				String queryString =	
						  "DELETE FROM OrderDetails " 
						+ "WHERE OrderID = ? ";
				
				preparedStatement = c.prepareStatement(queryString);

				int i = 1;
				preparedStatement.setLong(i++, pedidoId);

				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				throw new DataException(e);
			} finally {
				JDBCUtils.closeStatement(preparedStatement);
			}
		}
	
	protected void createLineasPedido(Connection connection, Long pedidoId,  List<LineaPedido> lineas)
			throws SQLException, DataException {

		PreparedStatement preparedStatement = null;
		try {          
			String queryString = null;
			int i;
			for (LineaPedido lp: lineas ) {
				queryString = "INSERT INTO OrderDetails (OrderID, ProductID, UnitPrice, Quantity, Discount) VALUES (?, ?, ?, ?, ?)";
				preparedStatement = connection.prepareStatement(queryString);				

				i = 1;     
				preparedStatement.setLong(i++, pedidoId);
				preparedStatement.setLong(i++, lp.getIdProducto());
				preparedStatement.setDouble(i++, lp.getPrecioUnitario());
				preparedStatement.setInt(i++, lp.getCantidad());
				preparedStatement.setDouble(i++, lp.getDescuento());

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

}
