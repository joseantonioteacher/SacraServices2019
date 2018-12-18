package com.sacra.ecommerce.service;

import java.util.List;

import com.sacra.ecommerce.model.Empleado;
import com.sacra.ecommerce.model.Territorio;
import com.sacra.ecommerce.service.impl.EmpleadoServiceImpl;
import com.sacra.ecommerce.util.ToStringUtil;


class EmpleadoServiceTest {
	private EmpleadoService empleadoService = null;
	private Territorio territorio;
	
	public EmpleadoServiceTest() {
		empleadoService = new EmpleadoServiceImpl();
	}
	
	protected void testFindById() {
		System.out.println("Testing findById ...");
				Long id = 1L;
		
		try {			
			Empleado e = empleadoService.findById(id);			
			System.out.println("Found: "+ToStringUtil.toString(e));
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		System.out.println("Test testFindById finished.\n");		
	}
	
	protected void testExists() {
		System.out.println("Testing exists ...");
				Long id = 1L;
		
		try {			
			Boolean exists = empleadoService.exists(id);			
			System.out.println("Exists: "+id+" -> "+exists);
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		System.out.println("Test exists finished.\n");		
	}
	
	protected void testFindAll() {
		System.out.println("Testing findAll ...");
		int pageSize = 10; 	
		
		try {

			List<Empleado> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = empleadoService.findAll(startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Empleado e: results) {
						total++;
						System.out.println("Result "+total+": "+ToStringUtil.toString(e));
					}
					startIndex = startIndex + pageSize;
				}
				
			} while (results.size()==pageSize);
			
			System.out.println("Found "+total+" results.");
						
		} catch (Throwable c) {
			c.printStackTrace();
		}
		System.out.println("Test testFindAll finished.\n");
	}
	
	protected void testFindByNombre() {
		System.out.println("Testing findByNombre ...");
		String name = "Nancy";
		int pageSize = 2;
		
		try {

			List<Empleado> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = empleadoService.findByNombre(name, startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Empleado e: results) {
						total++;
						System.out.println("Result "+total+": "+ToStringUtil.toString(e));
					}
					startIndex = startIndex + pageSize;
				}
				
			} while (results.size()==pageSize);
			
			System.out.println("Found "+total+" results.");
						
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test testFindByNombre finished.\n");
	}
	
	protected void testFindByCriteria() {
		System.out.println("Testing FindByCriteria ...");
		int pageSize = 2;
		
		EmpleadoCriteria e = new EmpleadoCriteria();
		e.setNombre("Daniel");
		e.setApellido("Romay");
		e.setTitulo("Dim");
		
		
		try {

			List<Empleado> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = empleadoService.findByCriteria(e, startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Empleado em: results) {
						total++;
						System.out.println("Result "+total+": "+ToStringUtil.toString(em));
					}
					startIndex = startIndex + pageSize;
				}
				
			} while (results.size()==pageSize);
			
			System.out.println("Found "+total+" results.");
						
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test FindByCriteria finished.\n");
	}
	
	
	
	public static void main(String args[]) {
		EmpleadoServiceTest test = new EmpleadoServiceTest();
		test.testFindById();	
		test.testExists();
		test.testFindAll();
		test.testFindByNombre();
		test.testFindByCriteria();

	}
	
}
