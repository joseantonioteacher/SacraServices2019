package com.sacra.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sacra.ecommerce.dao.TransportistaDAO;
import com.sacra.ecommerce.dao.impl.TransportistaDAOImpl;
import com.sacra.ecommerce.dao.util.ConnectionManager;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Transportista;
import com.sacra.ecommerce.service.Results;
import com.sacra.ecommerce.service.TransportistaService;

/**
 * Implementacion de un servicio de negocio para ilustrar una primera posible
 * aproximación  a la gestion de conexion y su transacciones. 
 * 
 * (Posteriormente otras soluciones de diseño mejores en cuanto 
 * a la factorización de la gestión de las conexiones y sus transacciones).
 *
 * Preguntas para el alumnado:
 * - Es realmente una solución optima desde el punto de vista de encapsulación la instanciacion
 *   de la implementacion concreta del DAO en el servicio?
 * - Y la inclusión de código JDBC en el servicio? Necesita/debe saber si el DAO es JDBC? 
 * - Como resolver�as si un caso de uso (méodo) del servicio necesitase
 *   acceder a varios DAO? Porque no podríamos usar otros servicios? 
 * Veremos tambión lo necesario si una transacción abarcase otros recursos transaccionales
 * además de una sola BD? (Otras BD, o incluso otros recursos transaccionales 
 * diferentes a BD).
 *   
 * @author https://www.linkedin.com/in/joseantoniolopezperez
 * @version 0.2
 */
public class TransportistaServiceImpl implements TransportistaService {

	private static Logger logger = LogManager.getLogger(TransportistaServiceImpl.class.getName());
	
	private TransportistaDAO dao = null;
	
	public TransportistaServiceImpl() {
		dao = new TransportistaDAOImpl();
	}
	
	public Transportista findById(Long id) 
			throws InstanceNotFoundException, DataException {
			
	
		
		Connection connection = null;
		
		try {
			
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);
			
			Transportista t = dao.findById(connection, id);	
			
			return t;
			
		} catch (SQLException e){
			logger.error(e.getMessage(), e);
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

	public List<Transportista> findAll(int startIndex, int count) 
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

	public Results<Transportista> findByNombre(String nombre, int startIndex, int count)
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

	public Transportista create(Transportista t) 
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
            Transportista result = dao.create(connection, t);
            commit = true;
            
            return result;

        } catch (SQLException e) {
            throw new DataException(e);
        // Tener claro que tambien puede ocurrir: 
        // } catch (DataException e) {
        //    throw e;
        // } catch(RuntimeException e) {
        //    throw e;
        // } catch(Error e) {
        //    throw e;
        } finally {
        	JDBCUtils.closeConnection(connection, commit);
        }		
	}

	public void update(Transportista t) 
			throws InstanceNotFoundException, DataException {
		
	    Connection connection = null;
        boolean commit = false;

        try {
          
            connection = ConnectionManager.getConnection();

            connection.setTransactionIsolation(
                    Connection.TRANSACTION_READ_COMMITTED);

            connection.setAutoCommit(false);

            // Execute action
            dao.update(connection, t);
            commit = true;
            
        } catch (SQLException e) {
            throw new DataException(e);
        // Tener claro que tambien puede ocurrir: 
        // } catch (DataException e) {
        //    throw e;
        // } catch(RuntimeException e) {
        //    throw e;
        // } catch(Error e) {
        //    throw e;
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
        // Tener claro que tambien puede ocurrir: 
        // } catch (DataException e) {
        //    throw e;
        // } catch(RuntimeException e) {
        //    throw e;
        // } catch(Error e) {
        //    throw e;
        } finally {
        	JDBCUtils.closeConnection(connection, commit);
        }		
	}
}
