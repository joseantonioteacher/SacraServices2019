package com.sacra.ecommerce.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Transportista;
import com.sacra.ecommerce.service.impl.TransportistaServiceImpl;
import com.sacra.ecommerce.util.ToStringUtil;

/**
 * "Antiguamente" los tests unitarios se implementaban e una clase main
 * como esta. (Aunque es perfectamente válido si están bien diseñados,
 * hoy en día se implementan siempre con una librería llama JUnit, 
 * que veremos más adelante)
 * 
 * Pregunta:
 * - Esta realmente desacomplado de la implementación del servicio?
 * 
 * @author https://www.linkedin.com/in/joseantoniolopezperez/
 * @version 0.2 (Desarrollo de Aplicaciones Web /JDBC / Paso 2)
 */
public class TransportistaServiceTest {
	
	private static Logger logger = LogManager.getLogger(TransportistaServiceTest.class.getName());
	
	private TransportistaService transportistaService = null;
	
	public TransportistaServiceTest() {
		transportistaService = new TransportistaServiceImpl();
	}
	
	protected void testFindById() {
		logger.info("Testing findById ...");
		// Test data
		Long id = 1L;
		
		try {
			
			Transportista t = transportistaService.findById(id);
		} catch (Throwable t) {
			logger.error("id = "+id, t);
		}		
		logger.info("Test testFindById finished.\n");		
	}
	
	protected void testExists() {
		logger.info("Testing exists ...");
		// Test data
		Long id = 1L;
		
		try {			
			Boolean exists = transportistaService.exists(id);			
			logger.info("Exists: "+id+" -> "+exists);			
		} catch (Throwable t) {
			logger.error("id = "+id, t);			
		}
		
		logger.info("Test exists finished.\n");		
	}
	
	protected void testFindAll() {
		logger.info("Testing findAll ...");
		int PAGE_SIZE = 4;

		try {

			Results<Transportista> results = null;
			int startIndex = 1; 
			int i = 1;
			do {
				results = transportistaService.findAll(startIndex, PAGE_SIZE);
				logger.info("Found "+results.getTotal()+" results.");				
				if (results.getPage().size()>0) {
					logger.info("Page ["+startIndex+" - "+(startIndex+results.getPage().size()-1)+"] : ");				
					for (Transportista t: results.getPage()) {
						logger.info("Result "+i+": "+ToStringUtil.toString(t));
						i++;
					}
					startIndex = startIndex + PAGE_SIZE;
				}

			} while (!(results.getPage().size()<PAGE_SIZE)); 		

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}
		logger.info("Test testFindAll finished.\n");
	}

	protected void testFindByNombre() {
		logger.info("Testing findByNombre ...");
		// Test data
		String name = "e"; // que contenga 'e'
		int PAGE_SIZE = 4;
		
		try {

			Results<Transportista> results = null;
			int startIndex = 1; 
			int i = 1;
			do {
				results = transportistaService.findByNombre(name, startIndex, PAGE_SIZE);
				logger.info("Found "+results.getTotal()+" results.");				
				if (results.getPage().size()>0) {
					logger.info("Page ["+startIndex+" - "+(startIndex+results.getPage().size()-1)+"] : ");				
					for (Transportista t: results.getPage()) {
						logger.info("Result "+i+": "+ToStringUtil.toString(t));
						i++;
					}
					startIndex = startIndex + PAGE_SIZE;
				}
				
			} while (!(results.getPage().size()<PAGE_SIZE)); 		
						
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}
		logger.info("Test testFindByNombre finished.\n");
	}

	protected void testCreate() {		
		logger.info("Testing create ...");			
		// Test data:
//		String[] names = new String[] {"Chium", "Fuaaa", "Fff", "A toda pastilla", "Ahi Vai", "ChegaSeguro", "Andandaras"};
//		String[] telfs = new String[] {"65156516", "8651651", "8416513", "2132135", "3215515", "881651651", "20561561651"};
//		for (int i= 0; i<names.length; i++) {
			Transportista e = new Transportista();
			e.setNombre("SGT");
			e.setTelefono("982982982");
			try {

				e = transportistaService.create(e);

				logger.info("Created: "+ToStringUtil.toString(e));

			} catch (Throwable t) {
				logger.error(t.getMessage(), t);
			}
//		}
		logger.info("Test created finished.\n");		
	}
	
	protected void testUpdate() {		
		logger.info("Testing update ...");	
		
		try {
			// Tomamos como muestra para actualizar el primero que retorne
			// que será solo uno si el test anterior ha finalizado OK
			Results<Transportista> results = 
					transportistaService.findByNombre("SGT", 1, 1);
			if (results.getPage().size()!=1) {
				throw new RuntimeException("Unexpected results count from previous tests: "+results.getTotal());
			}
			
			Transportista e = results.getPage().get(0);
			e.setNombre("Servizo Galego de Transporte");
			transportistaService.update(e);
			
			// Comprobamos
			e = transportistaService.findById(e.getId());
			
			logger.info("Updated to: "+e.getNombre());
								
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}
		logger.info("Test update finished.\n");		
	}	
	
	protected void testDelete() {		
		logger.info("Testing delete ...");	
		
		try {
			// Tomamos como muestra para actualizar el primero que retorne
			// que será solo uno si el test de creacion y actualizacion ha finalizado OK
			Results<Transportista> results = 
					transportistaService.findByNombre("galego", 1, 1);
			if (results.getPage().size()!=1) {
				throw new RuntimeException("Unexpected results from previous test");
			}
			
			Transportista e = results.getPage().get(0);
			logger.info("Deleting by id "+e.getId());
			transportistaService.delete(e.getId());
			
			// Comprobamos que ya no existe
			try {
				e = transportistaService.findById(e.getId());
				logger.info("Delete NOK!");
			} catch (InstanceNotFoundException ine) {
				logger.info("Delete OK");
			}						
								
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}
		logger.info("Test delete finished.\n");		
	}		
	
	public static void main(String args[]) {
		TransportistaServiceTest test = new TransportistaServiceTest();
		test.testFindById();
		test.testExists();
		test.testFindAll();
		test.testFindByNombre();		
		test.testCreate();
		test.testUpdate();
		test.testDelete();
		
	}
}

