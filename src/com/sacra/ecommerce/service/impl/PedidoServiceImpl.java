package com.sacra.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sacra.ecommerce.dao.PedidoDAO;
import com.sacra.ecommerce.dao.impl.ClienteDAOImpl;
import com.sacra.ecommerce.dao.impl.PedidoDAOImpl;
import com.sacra.ecommerce.dao.util.ConnectionManager;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Pedido;
import com.sacra.ecommerce.service.PedidoCriteria;
import com.sacra.ecommerce.service.PedidoService;

public class PedidoServiceImpl implements PedidoService{
	
	private static Logger logger = LogManager.getLogger(PedidoServiceImpl.class.getName());
	
	private PedidoDAO dao = null;
		
		public PedidoServiceImpl() {
			dao = new PedidoDAOImpl();
		}
		
		public Pedido findById(Long id) 
				throws InstanceNotFoundException, DataException {
					
			Connection connection = null;
			
			try {
				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				
				return dao.findById(connection, id);	
				
			} catch (SQLException e){
				logger.error(e.getMessage(),e);
				throw new DataException(e);
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						logger.error(e.getMessage(),e);
						throw new DataException(e);
					}
				}
			}
			
		}

		public Boolean exists(Long id) 
				throws DataException {
					
			Connection connection = null;
			
			try {
				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				
				return dao.exists(connection, id);
				
			} catch (SQLException e){
				logger.error(e.getMessage(),e);
				throw new DataException(e);
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						logger.error(e.getMessage(),e);
						throw new DataException(e);
					}
				}
			}
			
		}

		public List<Pedido> findAll(int startIndex, int count) 
				throws DataException {
				
			Connection connection = null;
			
			try {
				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				
				return dao.findAll(connection, startIndex, count);	
				
			} catch (SQLException e){
				logger.error(e.getMessage(),e);
				throw new DataException(e);
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						logger.error(e.getMessage(),e);
						throw new DataException(e);
					}
				}
			}
			
		}

		public long countAll() 
				throws DataException {
					
			Connection connection = null;
			
			try {
				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				
				return dao.countAll(connection);		
				
			} catch (SQLException e){
				logger.error(e.getMessage(),e);
				throw new DataException(e);
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						logger.error(e.getMessage(),e);
						throw new DataException(e);
					}
				}
			}
			
		}

		
		public List<Pedido> findByCriteria(PedidoCriteria p, int startIndex,
				int count) throws DataException {
			
			Connection connection = null;
			
			try {
				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				
				return dao.findByCriteria(connection, p, startIndex, count);
				
			} catch (SQLException e){
				logger.error(e.getMessage(),e);
				throw new DataException(e);
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						logger.error(e.getMessage(),e);
						throw new DataException(e);
					}
				}
			}
		}
		
		
		public Pedido create(Pedido p) 
				throws DuplicateInstanceException, DataException {
			
		    Connection connection = null;
	        boolean commit = false;

	        try {

	          
	            connection = ConnectionManager.getConnection();

	            connection.setTransactionIsolation(
	                    Connection.TRANSACTION_READ_COMMITTED);

	            connection.setAutoCommit(false);

	            // Execute action
	            Pedido result = dao.create(connection, p);
	            commit = true;
	            
	            return result;

	        } catch (SQLException e) {
				logger.error(e.getMessage(),e);
	            throw new DataException(e);
	        } finally {
	        	JDBCUtils.closeConnection(connection, commit);
	        }		
		}

		
		public void update(Pedido p) 
				throws InstanceNotFoundException, DataException {
			
		    Connection connection = null;
	        boolean commit = false;

	        try {
	          
	            connection = ConnectionManager.getConnection();

	            connection.setTransactionIsolation(
	                    Connection.TRANSACTION_READ_COMMITTED);

	            connection.setAutoCommit(false);

	            // Execute action
	            dao.update(connection, p);
	            commit = true;
	            
	        } catch (SQLException e) {
				logger.error(e.getMessage(),e);
	            throw new DataException(e);
	        } finally {
	        	JDBCUtils.closeConnection(connection, commit);
	        }
		}
		
		public long delete(Long id) 
				throws InstanceNotFoundException, DataException {
			
		    Connection connection = null;
	        boolean commit = false;

	        try {
	          
	            connection = ConnectionManager.getConnection();

	            connection.setTransactionIsolation(
	                    Connection.TRANSACTION_READ_COMMITTED);

	            connection.setAutoCommit(false);

	            // Execute action
	            long result = dao.delete(connection, id);            
	            commit = true;            
	            return result;
	            
	        } catch (SQLException e) {
				logger.error(e.getMessage(),e);
	            throw new DataException(e);
	        } finally {
	        	JDBCUtils.closeConnection(connection, commit);
	        }		
		}

		


	}
