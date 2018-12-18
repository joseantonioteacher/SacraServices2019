package com.sacra.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.sacra.ecommerce.dao.CategoriaDAO;
import com.sacra.ecommerce.dao.impl.CategoriaDAOImpl;
import com.sacra.ecommerce.dao.util.ConnectionManager;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Categoria;
import com.sacra.ecommerce.service.CategoriaService;


public class CategoriaServiceImpl implements CategoriaService {

	private CategoriaDAO dao = null;
	
	public CategoriaServiceImpl() {
		dao = new CategoriaDAOImpl();
	}
	
	public Categoria findById(Long id) 
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
			JDBCUtils.closeConnection(connection);
		}
		
	}

	public List<Categoria> findAll(int startIndex, int count) 
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

	public List<Categoria> findByNombre(String nombre, int startIndex, int count)
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

    public List<Categoria> findByCategoria(Categoria categoria, int startIndex, int count)
			throws DataException {
			
		Connection connection = null;
		
		try {
			
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);
			
			return dao.findByCategoria(connection, categoria, startIndex, count);
			
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

	
	public Categoria create(Categoria cat) 
			throws DuplicateInstanceException, DataException {
		
	    Connection connection = null;
        boolean commit = false;

        try {

          
            connection = ConnectionManager.getConnection();

            connection.setTransactionIsolation(
                    Connection.TRANSACTION_READ_COMMITTED);

            connection.setAutoCommit(false);

            // Execute action
            Categoria result = dao.create(connection, cat);
            commit = true;
            
            return result;

        } catch (SQLException e) {
            throw new DataException(e);

        } finally {
        	JDBCUtils.closeConnection(connection, commit);
        }		
	}

	public void update(Categoria cat) 
			throws InstanceNotFoundException, DataException {
		
	    Connection connection = null;
        boolean commit = false;

        try {
          
            connection = ConnectionManager.getConnection();

            connection.setTransactionIsolation(
                    Connection.TRANSACTION_READ_COMMITTED);

            connection.setAutoCommit(false);

            dao.update(connection, cat);
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
