package com.sacra.ecommerce.service;

import java.util.List;

import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Producto;
import com.sacra.ecommerce.service.ProductoService;
import com.sacra.ecommerce.service.impl.ProductoServiceImpl;
import com.sacra.ecommerce.util.ToStringUtil;


public class ProductoServiceTest {
	private ProductoService productoService = null;
	
	public ProductoServiceTest() {
		productoService = new ProductoServiceImpl();
	}
	
	
	protected void testExists() {
		System.out.println("Testing exists ...");

		Long id = (long) 1;
		
		try {			
			Boolean exists = productoService.exists(id);			
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

			List<Producto> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = productoService.findAll(startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Producto pro: results) {
						total++;
						System.out.println("Result "+total+": "+ToStringUtil.toString(pro));
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
	
	
	protected void testFindById() {
		System.out.println("Testing findById ...");
		
		Long id = (long) 1;
		
		try {			
			Producto pro = productoService.findById(id);			
			System.out.println("Found: "+ToStringUtil.toString(pro));
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		System.out.println("Test testFindById finished.\n");		
	}
	
	
	
	protected void testFindByNombre() {
		System.out.println("Testing findByNombre ...");
		
		String name = "e"; 
		int pageSize = 2;
		
		try {

			List<Producto> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = productoService.findByNombre(name, startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Producto pro: results) {
						total++;
						System.out.println("Result "+total+": "+ToStringUtil.toString(pro));
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
		
		Integer idCategoria = 3; 
		int pageSize = 2;
		
		try {

			List<Producto> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = productoService.findByCategoria(idCategoria, startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Producto pro: results) {
						total++;
						System.out.println("Result "+total+": "+ToStringUtil.toString(pro));
					}
					startIndex = startIndex + pageSize;
				}
				
			} while (results.size()==pageSize);
			
			System.out.println("Found "+total+" results.");
						
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test testFindByCategoria finished.\n");
	}
	
	
	protected void testFindByCriteria() {
		System.out.println("Testing FindByCriteria ...");
		int pageSize = 2;
		
		ProductoCriteria e = new ProductoCriteria();
		e.setNombre("Thermomix");
		e.setUnidadesEnStock(45);
		//setPrecioUnitario(1100.00);
		e.setIdProveedor(3);
		
		
		try {

			List<Producto> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = productoService.findByCriteria(e, startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Producto t: results) {
						total++;
						System.out.println("Result "+total+": "+ToStringUtil.toString(t));
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

	
	protected void testCreate() {		
		System.out.println("Testing create ...");	
		
		Producto pro = new Producto();
		pro.setNombre("producto5");
		pro.setCantidadPorUnidad("3");
		pro.setPrecioUnitario(30.0);
		pro.setUnidadesEnStock(56);
		pro.setUnidadesEnPedido(4);
		pro.setNivelDeReordenacion(1);
	
		try {
			
			pro = productoService.create(pro);
			
			System.out.println("Created: "+ToStringUtil.toString(pro));
					
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test created finished.\n");		
	}
	
	
	protected void testUpdate() {		
		System.out.println("Testing update ...");	
		
		try {

			List<Producto> results = 
					productoService.findByNombre("producto5", 1, 10);
			if (results.size()<1) {
				throw new RuntimeException("Unexpected results count from previous tests: "+results.size());
			}
			
			Producto pro = results.get(0);
			pro.setNombre("producto7");
			productoService.update(pro);
			
			pro = productoService.findById(pro.getId());
			
			System.out.println("Updated to: "+pro.getNombre());
								
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test update finished.\n");		
	}	
	
	protected void testDelete() {		
		System.out.println("Testing delete ...");	
		
		try {

			List<Producto> results = 
					productoService.findByNombre("producto7", 1, 10);
			if (results.size()!=1) {
				throw new RuntimeException("Unexpected results from previous test");
			}
			
			Producto pro = results.get(0);
			System.out.println("Deleting by id "+pro.getId());
			productoService.delete(pro.getId());
			
			try {
				pro = productoService.findById(pro.getId());
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
		ProductoServiceTest test = new ProductoServiceTest();
		test.testExists();
		test.testFindAll();
		test.testFindById();
		test.testFindByNombre();
		test.testFindByCategoria();
		test.testFindByCriteria();
		test.testCreate();
		test.testUpdate();
		test.testDelete();
		
	}
}

