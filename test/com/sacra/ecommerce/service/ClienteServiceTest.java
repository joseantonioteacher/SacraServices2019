package com.sacra.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Cliente;
import com.sacra.ecommerce.model.Demografico;
import com.sacra.ecommerce.service.ClienteService;
import com.sacra.ecommerce.service.impl.ClienteServiceImpl;
import com.sacra.ecommerce.util.ToStringUtil;


class ClienteServiceTest {
	private ClienteService clienteService = null;
	private Demografico demografico;
	
	public ClienteServiceTest() {
		clienteService = new ClienteServiceImpl();
	}
	
	protected void testFindById() {
		System.out.println("Testing findById ...");
				String id = "ALFKI";
		
		try {			
			Cliente t = clienteService.findById(id);			
			System.out.println("Found: "+ToStringUtil.toString(t));
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		System.out.println("Test testFindById finished.\n");		
	}
	
	protected void testExists() {
		System.out.println("Testing exists ...");
				String id = "ALFKI";
		
		try {			
			Boolean exists = clienteService.exists(id);			
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

			List<Cliente> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = clienteService.findAll(startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Cliente c: results) {
						total++;
						System.out.println("Result "+total+": "+ToStringUtil.toString(c));
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
		String name = "Alfreds Futterkiste";
		int pageSize = 2;
		
		try {

			List<Cliente> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = clienteService.findByNombre(name, startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Cliente t: results) {
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
		System.out.println("Test testFindByNombre finished.\n");
	}
	
	protected void testFindByCriteria() {
		System.out.println("Testing FindByCriteria ...");
		int pageSize = 2;
		
		ClienteCriteria e = new ClienteCriteria();
		e.setNombreCompania("Valve");
		e.setNombre("Hector");
		e.setTitulo("Owner");
		
		
		try {

			List<Cliente> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = clienteService.findByCriteria(e, startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Cliente t: results) {
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
		demografico = new Demografico();
		demografico.setClienteTipoId("videojuego");
		
		List<Demografico> lista = new ArrayList<Demografico>();
		lista.add(demografico);

		Cliente e = new Cliente();
		e.setNombreCompania("Valve Steam");
		e.setNombre("Hector");
		e.setTitulo("Owner");
		e.setDireccion("Lugar O Peto, Nº5");
		e.setCiudad("Chantada");
		e.setRegion("Lugo");
		e.setCodigoPostal("27500");
		e.setPais("España");
		e.setTelefono("638536905");
		e.setFax("982440011");
		e.setDemograficos(lista);
		try {
			
			e = clienteService.create(e);
			
			System.out.println("Created: "+ToStringUtil.toString(e));
					
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test created finished.\n");		
	}
	
	protected void testUpdate() {		
		System.out.println("Testing update ...");	
		demografico = new Demografico();
		demografico.setClienteTipoId("libros");
		
		List<Demografico> lista = new ArrayList<Demografico>();
		lista.add(demografico);
		
		try {

			List<Cliente> results = 
					clienteService.findByNombre("Steam", 1, 10);
			if (results.size()<1) {
				throw new RuntimeException("Unexpected results count from previous tests: "+results.size());
			}
			
			Cliente e = results.get(0);
			e.setNombreCompania("STEAM");
			e.setNombre("Hector Ledo Doval");
			e.setDemograficos(lista);
			clienteService.update(e);
			
			e = clienteService.findById(e.getId());
			
			System.out.println("Updated to: "+e.getNombre());
								
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test update finished.\n");		
	}	
	
	protected void testDelete() {		
		System.out.println("Testing delete ...");	
		
		try {

			List<Cliente> results = 
					clienteService.findByNombre("STEAM", 1, 10);
			if (results.size()!=1) {
				throw new RuntimeException("Unexpected results from previous test");
			}
			
			Cliente e = results.get(0);
			System.out.println("Deleting by id "+e.getId());
			clienteService.delete(e.getId());
			
						try {
				e = clienteService.findById(e.getId());
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
		ClienteServiceTest test = new ClienteServiceTest();
		test.testFindById();	
		test.testExists();
		test.testFindAll();
		test.testFindByNombre();
		test.testCreate();
		test.testFindByCriteria();
		//test.testUpdate();
		//test.testDelete();
	}
	
}