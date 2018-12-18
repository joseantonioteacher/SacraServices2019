package com.sacra.ecommerce.service;

import java.util.List;

import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Categoria;
import com.sacra.ecommerce.service.CategoriaService;
import com.sacra.ecommerce.service.impl.CategoriaServiceImpl;
import com.sacra.ecommerce.util.ToStringUtil;


public class CategoriaServiceTest {
	private CategoriaService categoriaService = null;
	
	public CategoriaServiceTest() {
		categoriaService = new CategoriaServiceImpl();
	}
	
	protected void testFindById() {
		System.out.println("Testing findById ...");
		
		Long id = (long) 1;
		
		try {			
			Categoria cat = categoriaService.findById(id);			
			System.out.println("Found: "+ToStringUtil.toString(cat));
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		System.out.println("Test testFindById finished.\n");		
	}
	
	protected void testExists() {
		System.out.println("Testing exists ...");

		Long id = (long) 1;
		
		try {			
			Boolean exists = categoriaService.exists(id);			
			System.out.println("Exists: "+id+" -> "+exists);
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		System.out.println("Test exists finished.\n");		
	}
	
	protected void testFindAll() {
		System.out.println("Testing findAll ...");
		
		int pageSize = 2; 	
		
		try {

			List<Categoria> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = categoriaService.findAll(startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Categoria cat: results) {
						total++;
						System.out.println("Result "+total+": "+ToStringUtil.toString(cat));
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
	
	protected void testFindByNombre() {
		System.out.println("Testing findByNombre ...");
		
		String name = "e"; 
		int pageSize = 2;
		
		try {

			List<Categoria> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = categoriaService.findByNombre(name, startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Categoria cat: results) {
						total++;
						System.out.println("Result "+total+": "+ToStringUtil.toString(cat));
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
	
	
	protected void testFindByCategoria() {
		System.out.println("Testing findByCategoria ...");
		int pageSize = 2;
		
		Categoria e = new Categoria();
		e.setNombre("Seafood");
		e.setDescripcion("Seaweed and fish");
		
		try {

			List<Categoria> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = categoriaService.findByCategoria(e, startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Categoria cat: results) {
						total++;
						System.out.println("Result "+total+": "+ToStringUtil.toString(cat));
					}
					startIndex = startIndex + pageSize;
				}
				
			} while (results.size()==pageSize);
			
			System.out.println("Found "+total+" results.");
						
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test FindByCategoria finished.\n");
	}
	
	
	
	protected void testCreate() {		
		System.out.println("Testing create ...");	
		
		Categoria e = new Categoria();
		e.setNombre("Soup");
		e.setDescripcion("chicken soup");
	
		try {
			
			e = categoriaService.create(e);
			
			System.out.println("Created: "+ToStringUtil.toString(e));
					
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test created finished.\n");		
	}
	
	
	protected void testUpdate() {		
		System.out.println("Testing update ...");	
		
		try {

			List<Categoria> results = 
					categoriaService.findByNombre("Soup", 1, 10);
			if (results.size()<1) {
				throw new RuntimeException("Unexpected results count from previous tests: "+results.size());
			}
			
			Categoria e = results.get(0);
			e.setNombre("Fast Food");
			categoriaService.update(e);
			
			e = categoriaService.findById(e.getId());
			
			System.out.println("Updated to: "+e.getNombre());
								
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test update finished.\n");		
	}	
	
	protected void testDelete() {		
		System.out.println("Testing delete ...");	
		
		try {

			List<Categoria> results = 
					categoriaService.findByNombre("Fast Food", 1, 10);
			if (results.size()!=1) {
				throw new RuntimeException("Unexpected results from previous test");
			}
			
			Categoria e = results.get(0);
			System.out.println("Deleting by id "+e.getId());
			categoriaService.delete(e.getId());
			
			try {
				e = categoriaService.findById(e.getId());
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
		CategoriaServiceTest test = new CategoriaServiceTest();
		test.testFindById();
		test.testExists();
		test.testFindAll();
		test.testFindByNombre();
		test.testFindByCategoria();
		test.testCreate();
		test.testUpdate();
		test.testDelete();
		
	}
}

