package com.sacra.ecommerce.service;

import java.util.List;

import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Proveedor;
import com.sacra.ecommerce.service.impl.ProveedorServiceImpl;
import com.sacra.ecommerce.util.ToStringUtil;

public class ProveedorServiceTest {

	private ProveedorService proveedorService = null;
	
	public ProveedorServiceTest() {
		proveedorService = new ProveedorServiceImpl();
	}
	
	protected void testFindById() {
		System.out.println("Testing findById ...");
		// Test data
		Long id = 1L;
		
		try {			
			Proveedor p = proveedorService.findById(id);			
			System.out.println("Found: "+ToStringUtil.toString(p));
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		System.out.println("Test testFindById finished.\n");		
	}
	
	protected void testExists() {
		System.out.println("Testing exists ...");
		// Test data
		Long id = 1L;
		
		try {			
			Boolean exists = proveedorService.exists(id);			
			System.out.println("Exists: "+id+" -> "+exists);
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		System.out.println("Test exists finished.\n");		
	}
	
	protected void testFindAll() {
		System.out.println("Testing findAll ...");
		// Test data:		
		int pageSize = 10; 	
		
		try {

			List<Proveedor> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = proveedorService.findAll(startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Proveedor p: results) {
						total++;
						System.out.println("Result "+total+": "+ToStringUtil.toString(p));
					}
					startIndex = startIndex + pageSize;
				}
				
			} while (results.size()==pageSize);
			
			System.out.println("Found "+total+" results.");
						
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test testFindAll finished.\n");
	}
	
	protected void testFindByCriteria() {
		System.out.println("Testing findByCriteria ...");
		// Test data
		ProveedorCriteria c = new ProveedorCriteria();
		c.setNombreCompania("Exotic Liquids");
		int pageSize = 10;
		
		try {

			List<Proveedor> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = proveedorService.findByCriteria(c, startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Proveedor p: results) {
						total++;
						System.out.println("Result "+total+": "+ToStringUtil.toString(p));
					}
					startIndex = startIndex + pageSize;
				}
				
			} while (results.size()==pageSize);
			
			System.out.println("Found "+total+" results.");
						
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test testFindByCriteria finished.\n");
	}

	
	protected void testCreate() {		
		System.out.println("Testing create ...");	
		
		// Test data:
		Proveedor p = new Proveedor();
		p.setCiudad("Santiago");
		p.setCodigoPostal("34567");
		p.setComunidad("Galiza");
		p.setDireccion("Av. de Lugo 12 3º");
		p.setFax("986462289");
		p.setNombreCompania("Blu:Sens");
		p.setNombreContacto("Alberto García Moreno");
		p.setPaginaInicio("www.blue_sens.com");
		p.setPais("España");
		p.setPuestoContacto("Jefe Departamento Márketing");
		p.setTelefono("986655747");
	
		try {
			p = proveedorService.create(p);
			
			System.out.println("Created: "+ToStringUtil.toString(p));
					
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test created finished.\n");		
	}
	
	
	protected void testUpdate() {		
		System.out.println("Testing update ...");	
		
		ProveedorCriteria c = new ProveedorCriteria();
		c.setNombreCompania("Blu:Sens");
		
		try {
			List<Proveedor> results = 
					proveedorService.findByCriteria(c, 1, 10);
			if (results.size()<1) {
				throw new RuntimeException("Unexpected results count from previous tests: "+results.size());
			}
			
			Proveedor p = results.get(0);
			p.setNombreCompania("Sony");
			proveedorService.update(p);
			
			// Comprobamos
			p = proveedorService.findById(p.getId());
			
			System.out.println("Updated to: "+p.getNombreCompania());
								
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test update finished.\n");		
	}	
	
	protected void testDelete() {		
		System.out.println("Testing delete ...");	
		ProveedorCriteria c = new ProveedorCriteria();
		c.setNombreCompania("Sony");
		
		try {
			
			List<Proveedor> results = 
					proveedorService.findByCriteria(c, 1, 10);
			if (results.size()!=1) {
				throw new RuntimeException("Unexpected results from previous test");
			}
			
			Proveedor p = results.get(0);
			System.out.println("Deleting by id "+p.getId());
			proveedorService.delete(p.getId());
			
			try {
				p = proveedorService.findById(p.getId());
				System.out.println("Delete NOK!");
			} catch (InstanceNotFoundException ine) {
				System.out.println("Delete OK");
			}						
								
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test delete finished.\n");		
	} 
	public static void main(String[] args) {
		
		ProveedorServiceTest test = new ProveedorServiceTest();
		test.testFindById();
		test.testExists();
		test.testFindAll();
		test.testFindByCriteria();	
		test.testCreate();
		test.testUpdate();
		test.testDelete();
		
	}

}
