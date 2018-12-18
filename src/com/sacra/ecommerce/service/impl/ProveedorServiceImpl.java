package com.sacra.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.sacra.ecommerce.dao.ProveedorDAO;
import com.sacra.ecommerce.dao.impl.ProveedorDAOImpl;
import com.sacra.ecommerce.dao.util.ConnectionManager;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Proveedor;
import com.sacra.ecommerce.service.ProveedorCriteria;
import com.sacra.ecommerce.service.ProveedorService;

public class ProveedorServiceImpl implements ProveedorService {
	
private ProveedorDAO dao = null;
	
	public ProveedorServiceImpl() {
		dao = new ProveedorDAOImpl();
	}
	
	public Proveedor findById(Long id) 
			throws InstanceNotFoundException, DataException {
				
		Connection connection = null;
		
		try {
			
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);
			
			return dao.findById(connection, id);	
			
		} catch (SQLException e){
			throw new DataException(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
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
			throw new DataException(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataException(e);
				}
			}
		}
		
	}

	public List<Proveedor> findAll(int startIndex, int count) 
			throws DataException {
			
		Connection connection = null;
		
		try {
			
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);
			
			return dao.findAll(connection, startIndex, count);	
			
		} catch (SQLException e){
			throw new DataException(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
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
			throw new DataException(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataException(e);
				}
			}
		}
		
	}

	
	public List<Proveedor> findByCriteria(ProveedorCriteria p, int startIndex,
			int count) throws DataException {
		
		Connection connection = null;
		
		try {
			
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);
			
			return dao.findByCriteria(connection, p, startIndex, count);
			
		} catch (SQLException e){
			throw new DataException(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataException(e);
				}
			}
		}
	}
	
	
	public Proveedor create(Proveedor p) 
			throws DuplicateInstanceException, DataException {
		
	    Connection connection = null;
        boolean commit = false;

        try {

            // Get a connection with autocommit to "false" 
        	// and isolation level to "TRANSACTION_READ_COMMITTED"
          
            connection = ConnectionManager.getConnection();

            connection.setTransactionIsolation(
                    Connection.TRANSACTION_READ_COMMITTED);

            connection.setAutoCommit(false);

            // Execute action
            Proveedor result = dao.create(connection, p);
            commit = true;
            
            return result;

        } catch (SQLException e) {
            throw new DataException(e);
        } finally {
        	JDBCUtils.closeConnection(connection, commit);
        }		
	}

	
	public void update(Proveedor p) 
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
            throw new DataException(e);
        } finally {
        	JDBCUtils.closeConnection(connection, commit);
        }		
	}

	


}
