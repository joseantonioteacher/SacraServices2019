package com.sacra.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.sacra.ecommerce.dao.ProductoDAO;
import com.sacra.ecommerce.dao.impl.ProductoDAOImpl;
import com.sacra.ecommerce.dao.util.ConnectionManager;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Producto;
import com.sacra.ecommerce.service.ProductoCriteria;
import com.sacra.ecommerce.service.ProductoService;


public class ProductoServiceImpl implements ProductoService {

	private ProductoDAO dao = null;
	
	public ProductoServiceImpl() {
		dao = new ProductoDAOImpl();
	}
	

	public Boolean exists(Long id) throws DataException {
				
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

	public List<Producto> findAll(int startIndex, int count) throws DataException {
			
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
	
	public long countAll() throws DataException {
				
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

	public Producto findById(Long id) throws InstanceNotFoundException, DataException {
				
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


	public List<Producto> findByNombre(String nombre, int startIndex, int count) throws DataException {
			
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
	
	
	@Override
	public List<Producto> findByCategoria(Integer idCategoria, int startIndex, int count) throws DataException {

		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return dao.findByCategoria(connection, idCategoria, startIndex, count);

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}
	
	public List<Producto> findByCriteria(ProductoCriteria pc, int startIndex, int count)
			throws DataException {
			
		Connection connection = null;
		
		try {
			
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);
			
			return dao.findByCriteria(connection, pc, startIndex, count);
			
		} catch (SQLException e){
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}
		
	
	public Producto create(Producto pro) throws DuplicateInstanceException, DataException {
		
	    Connection connection = null;
        boolean commit = false;

        try {

          
            connection = ConnectionManager.getConnection();

            connection.setTransactionIsolation(
                    Connection.TRANSACTION_READ_COMMITTED);

            connection.setAutoCommit(false);

            // Execute action
            Producto result = dao.create(connection, pro);
            commit = true;
            
            return result;

        } catch (SQLException e) {
            throw new DataException(e);

        } finally {
        	JDBCUtils.closeConnection(connection, commit);
        }		
	}

	public void update(Producto pro) throws InstanceNotFoundException, DataException {
		
	    Connection connection = null;
        boolean commit = false;

        try {
          
            connection = ConnectionManager.getConnection();

            connection.setTransactionIsolation(
                    Connection.TRANSACTION_READ_COMMITTED);

            connection.setAutoCommit(false);

            dao.update(connection, pro);
            commit = true;
            
        } catch (SQLException e) {
            throw new DataException(e);

        } finally {
        	JDBCUtils.closeConnection(connection, commit);
        }
	}
	

	public long delete(Long id) throws InstanceNotFoundException, DataException {
		
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
