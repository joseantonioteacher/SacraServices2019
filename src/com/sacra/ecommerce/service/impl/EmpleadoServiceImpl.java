package com.sacra.ecommerce.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.sacra.ecommerce.dao.EmpleadoDAO;
import com.sacra.ecommerce.dao.impl.EmpleadoDAOImpl;
import com.sacra.ecommerce.dao.util.ConnectionManager;
import com.sacra.ecommerce.dao.util.JDBCUtils;
import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Empleado;
import com.sacra.ecommerce.service.EmpleadoCriteria;
import com.sacra.ecommerce.service.EmpleadoService;



public class EmpleadoServiceImpl implements EmpleadoService {
	
	private EmpleadoDAO dao = null;
		
		public EmpleadoServiceImpl() {
			dao = new EmpleadoDAOImpl();
		}
		
		public Empleado findById(Long id) 
				throws InstanceNotFoundException, DataException {
			
			long t0 = 0, t1 = 0, t2 = 0, t3 = 0;
			Connection connection = null;
			
			try {
				t0 = System.nanoTime();
				
				connection = ConnectionManager.getConnection();
				
				t1 = System.nanoTime();
				
				connection.setAutoCommit(true);				
				Empleado e = dao.findById(connection, id);
				t2 = System.nanoTime();
				
				return e;
			} catch (SQLException e){
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
				t3 = System.currentTimeMillis();
				System.out.println("Get cx: "+(t1-t0)+", DAO: "+(t2-t1)+", Release Cx: "+(t3-t2));	
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

		public List<Empleado> findAll(int startIndex, int count) 
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
		
		public List<Empleado> findByNombre(String nombre, int startIndex, int count)
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
		
	     public List<Empleado> findByCriteria(EmpleadoCriteria empleado, int startIndex, int count)
				throws DataException {
				
			Connection connection = null;
			
			try {
				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				
				return dao.findByCriteria(connection, empleado, startIndex, count);
				
			} catch (SQLException e){
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
		}

		public Empleado create(Empleado e) 
				throws DuplicateInstanceException, DataException {
			
		    Connection connection = null;
	        boolean commit = false;

	        try {
	          
	            connection = ConnectionManager.getConnection();

	            connection.setTransactionIsolation(
	                    Connection.TRANSACTION_READ_COMMITTED);

	            connection.setAutoCommit(false);

	            // Execute action
	            Empleado result = dao.create(connection, e);
	            
	            
	            
	            commit = true;
	            
	            return result;

	        } catch (SQLException s) {
	            throw new DataException(s);

	        } finally {
	        	JDBCUtils.closeConnection(connection, commit);
	        }		
		}

		public void update(Empleado e) 
				throws InstanceNotFoundException, DataException {
			
		    Connection connection = null;
	        boolean commit = false;

	        try {
	          
	            connection = ConnectionManager.getConnection();

	            connection.setTransactionIsolation(
	                    Connection.TRANSACTION_READ_COMMITTED);

	            connection.setAutoCommit(false);

	            dao.update(connection, e);
	            commit = true;
	            
	        } catch (SQLException s) {
	            throw new DataException(s);

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