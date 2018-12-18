package com.sacra.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.sacra.ecommerce.dao.ClienteDAO;
import com.sacra.ecommerce.dao.impl.ClienteDAOImpl;
import com.sacra.ecommerce.dao.util.ConnectionManager;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Cliente;
import com.sacra.ecommerce.service.ClienteCriteria;
import com.sacra.ecommerce.service.ClienteService;

public class ClienteServiceImpl implements ClienteService {
	
	private ClienteDAO dao = null;
		
		public ClienteServiceImpl() {
			dao = new ClienteDAOImpl();
		}
		
		public Cliente findById(String id) 
				throws InstanceNotFoundException, DataException {
					
			Connection connection = null;
			
			try {
				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				
				return dao.findById(connection, id);	
				
			} catch (SQLException e){
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
			
		}

		public Boolean exists(String id) 
				throws DataException {
					
			Connection connection = null;
			
			try {
				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				
				return dao.exists(connection, id);
				
			} catch (SQLException e){
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
			
		}

		public List<Cliente> findAll(int startIndex, int count) 
				throws DataException {
				
			Connection connection = null;
			
			try {
				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				
				return dao.findAll(connection, startIndex, count);	
				
			} catch (SQLException e){
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
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
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
			
		}
		
		public List<Cliente> findByNombre(String nombre, int startIndex, int count)
				throws DataException {
				
			Connection connection = null;
			
			try {
				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				
				return dao.findByNombre(connection, nombre, startIndex, count);
				
			} catch (SQLException e){
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
		}
		
	     public List<Cliente> findByCriteria(ClienteCriteria cliente, int startIndex, int count)
				throws DataException {
				
			Connection connection = null;
			
			try {
				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				
				return dao.findByCriteria(connection, cliente, startIndex, count);
				
			} catch (SQLException e){
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
		}

		public Cliente create(Cliente c) 
				throws DuplicateInstanceException, DataException {
			
		    Connection connection = null;
	        boolean commit = false;

	        try {
	          
	            connection = ConnectionManager.getConnection();

	            connection.setTransactionIsolation(
	                    Connection.TRANSACTION_READ_COMMITTED);

	            connection.setAutoCommit(false);

	            // Execute action
	            Cliente result = dao.create(connection, c);
	            commit = true;
	            
	            return result;

	        } catch (SQLException e) {
	            throw new DataException(e);

	        } finally {
	        	JDBCUtils.closeConnection(connection, commit);
	        }		
		}

		public void update(Cliente c) 
				throws InstanceNotFoundException, DataException {
			
		    Connection connection = null;
	        boolean commit = false;

	        try {
	          
	            connection = ConnectionManager.getConnection();

	            connection.setTransactionIsolation(
	                    Connection.TRANSACTION_READ_COMMITTED);

	            connection.setAutoCommit(false);

	            dao.update(connection, c);
	            commit = true;
	            
	        } catch (SQLException e) {
	            throw new DataException(e);

	        } finally {
	        	JDBCUtils.closeConnection(connection, commit);
	        }
		}

		public long delete(String id) 
				throws InstanceNotFoundException, DataException {
			
		    Connection connection = null;
	        boolean commit = false;

	        try {
	          
	            connection = ConnectionManager.getConnection();

	            connection.setTransactionIsolation(
	                    Connection.TRANSACTION_READ_COMMITTED);

	            connection.setAutoCommit(false);

	            	            long result = dao.delete(connection, id);            
	            commit = true;            
	            return result;
	            
	        } catch (SQLException e) {
	            throw new DataException(e);

	        } finally {
	        	JDBCUtils.closeConnection(connection, commit);
	        }		
		}
}
