	package com.sacra.ecommerce.service;

	import java.util.List;

import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Territorio;
import com.sacra.ecommerce.service.impl.TerritorioServiceImpl;
import com.sacra.ecommerce.util.ToStringUtil;
import com.sacra.ecommerce.service.TerritorioService;



	class TerritorioServiceTest {

		private TerritorioService territorioService = null;
		
		public TerritorioServiceTest() {
			territorioService = new TerritorioServiceImpl();
		}
		
		protected void testFindById() {
			System.out.println("Testing findById ...");
					Long id = 10019L;
			
			try {			
				Territorio t = territorioService.findById(id);			
				System.out.println("Found: "+ToStringUtil.toString(t));
				
			} catch (Throwable d) {
				d.printStackTrace();
			}
			
			System.out.println("Test FindById finished.\n");		
		}
		
		protected void testExists() {
			System.out.println("Testing exists ...");
			Long id = 10019L;
			
			try {			
				Boolean exists = territorioService.exists(id);			
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

				List<Territorio> results = null;
				int startIndex = 1; 
				int total = 0;
				
				do {
					results = territorioService.findAll(startIndex, pageSize);
					if (results.size()>0) {
						System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
						for (Territorio t: results) {
							total++;
							System.out.println("Result "+total+": "+ToStringUtil.toString(t));
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
			
			Territorio t = new Territorio();
			t.setId(66666L);
			t.setDescripcion("Molly");
			
			try {
				
				t = territorioService.create(t);
				
				System.out.println("Created: "+ToStringUtil.toString(t));
						
			} catch (Throwable e) {
				e.printStackTrace();
			}
			System.out.println("Test created finished.\n");		
		}
		
		protected void testCreate2() {		
			System.out.println("Testing create ...");	
			
			Territorio t = new Territorio();
			t.setId(55555L);
			t.setDescripcion("Barna");
			
			try {
				
				t = territorioService.create(t);
				
				System.out.println("Created: "+ToStringUtil.toString(t));
						
			} catch (Throwable e) {
				e.printStackTrace();
			}
			System.out.println("Test created finished.\n");		
		}
		
		protected void testUpdate() {		
			System.out.println("Testing update ...");	
			
			try {

				List<Territorio> results = 
						territorioService.findByNombre("Barna", 1, 10);
				if (results.size()<1) {
					throw new RuntimeException("Unexpected results count from previous tests: "+results.size());
				}
				
				Territorio t = results.get(0);

				t.setDescripcion("Barne");
				territorioService.update(t);

				t = territorioService.findById(t.getId());
				
				System.out.println("Updated to: "+t.getId());
									
			} catch (Throwable e) {
				e.printStackTrace();
			}
			System.out.println("Test update finished.\n");		
		}	
			
		
		public static void main(String args[]) {
			TerritorioServiceTest test = new TerritorioServiceTest();
			test.testCreate();
			test.testCreate2();
			test.testFindById();	
			test.testExists();
			test.testFindAll();
			test.testUpdate();
		}
		
}