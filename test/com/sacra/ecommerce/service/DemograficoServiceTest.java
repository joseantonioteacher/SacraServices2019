package com.sacra.ecommerce.service;

import java.util.List;

import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Demografico;
import com.sacra.ecommerce.service.DemograficoService;
import com.sacra.ecommerce.service.impl.DemograficoServiceImpl;
import com.sacra.ecommerce.util.ToStringUtil;

class DemograficoServiceTest {

	private DemograficoService demograficoService = null;
	
	public DemograficoServiceTest() {
		demograficoService = new DemograficoServiceImpl();
	}
	
	protected void testFindById() {
		System.out.println("Testing findById ...");
				String id = "Videojuego";
		
		try {			
			Demografico d = demograficoService.findById(id);			
			System.out.println("Found: "+ToStringUtil.toString(d));
			
		} catch (Throwable d) {
			d.printStackTrace();
		}
		
		System.out.println("Test FindById finished.\n");		
	}
	
	protected void testExists() {
		System.out.println("Testing exists ...");
		String id = "Videojuego";
		
		try {			
			Boolean exists = demograficoService.exists(id);			
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

			List<Demografico> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = demograficoService.findAll(startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Demografico d: results) {
						total++;
						System.out.println("Result "+total+": "+ToStringUtil.toString(d));
					}
					startIndex = startIndex + pageSize;
				}
				
			} while (results.size()==pageSize);
			
			System.out.println("Found "+total+" results.");
						
		} catch (Throwable d) {
			d.printStackTrace();
		}
		System.out.println("Test testFindAll finished.\n");
	}
	
	protected void testCreate() {		
		System.out.println("Testing create ...");	
		
		Demografico d = new Demografico();
		d.setClienteTipoId("Videojuego");
		d.setClienteDescripcion("Indica si al usuario le gustan los videojuegos.");
		
		try {
			
			d = demograficoService.create(d);
			
			System.out.println("Created: "+ToStringUtil.toString(d));
					
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test created finished.\n");		
	}
	
	protected void testCreate2() {		
		System.out.println("Testing create ...");	
		
		Demografico d = new Demografico();
		d.setClienteTipoId("Libros");
		d.setClienteDescripcion("Indica si al usuario le gusta leer.");
		
		try {
			
			d = demograficoService.create(d);
			
			System.out.println("Created: "+ToStringUtil.toString(d));
					
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test created finished.\n");		
	}
	
	protected void testUpdate() {		
		System.out.println("Testing update ...");	
		
		try {

			List<Demografico> results = 
					demograficoService.findByNombre("Videojuego", 1, 10);
			if (results.size()<1) {
				throw new RuntimeException("Unexpected results count from previous tests: "+results.size());
			}
			
			Demografico d = results.get(0);

			d.setClienteDescripcion("Indica si al usuario le gustan los videojuegos o cualquier noticia relacionada con el sector.");
			demograficoService.update(d);

			d = demograficoService.findById(d.getClienteTipoId());
			
			System.out.println("Updated to: "+d.getClienteTipoId());
								
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test update finished.\n");		
	}	
	
	protected void testDelete() {		
		System.out.println("Testing delete ...");	
		
		try {

			List<Demografico> results = 
					demograficoService.findByNombre("Videojuego", 1, 10);
			if (results.size()!=1) {
				throw new RuntimeException("Unexpected results from previous test");
			}
			
			Demografico d = results.get(0);
			System.out.println("Deleting by id "+d.getClienteTipoId());
			demograficoService.delete(d.getClienteTipoId());
			
						try {
				d = demograficoService.findById(d.getClienteTipoId());
				System.out.println("Delete NOK!");
			} catch (InstanceNotFoundException ine) {
				System.out.println("Delete OK");
			}						
								
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test delete finished.\n");		
	}		
	
	public static void main(String args[]) {
		DemograficoServiceTest test = new DemograficoServiceTest();
		test.testCreate();
		test.testCreate2();
		test.testFindById();	
		test.testExists();
		test.testFindAll();
		test.testUpdate();
		//test.testDelete();
	}
	
}
