package com.sacra.ecommerce.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.LineaPedido;
import com.sacra.ecommerce.model.Pedido;
import com.sacra.ecommerce.service.impl.PedidoServiceImpl;
import com.sacra.ecommerce.util.ToStringUtil;

public class PedidoServiceTest {
	
	private PedidoService pedidoService = null;
	
	public PedidoServiceTest() {
		pedidoService = new PedidoServiceImpl();
	}
	
	protected void testFindById() {
		System.out.println("Testing findById ...");
				Long id = 10248L;
		
		try {			
			Pedido p = pedidoService.findById(id);			
			System.out.println("Found: "+ToStringUtil.toString(p));
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		System.out.println("Test testFindById finished.\n");		
	}
	
	protected void testExists() {
		System.out.println("Testing exists ...");
				Long id = 10248L;
		
		try {			
			Boolean exists = pedidoService.exists(id);			
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

			List<Pedido> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = pedidoService.findAll(startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Pedido p: results) {
						total++;
						System.out.println("Result "+total+": "+ToStringUtil.toString(p));
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
	
	
	protected void testFindByCriteria() {
		System.out.println("Testing FindByCriteria ...");
		int pageSize = 2;
		
		PedidoCriteria pc = new PedidoCriteria();
		pc.setIdTransportista(1L);
		pc.setPaisReceptor("France");
		
		
		try {

			List<Pedido> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = pedidoService.findByCriteria(pc, startIndex, pageSize);
				if (results.size()>0) {
					System.out.println("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Pedido p: results) {
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
		System.out.println("Test FindByCriteria finished.\n");
	}
	
	protected void testCreate() {		
		System.out.println("Testing create ...");
		try {
		LineaPedido lineaPedido1 = new LineaPedido();
		lineaPedido1.setIdProducto(1L);
		lineaPedido1.setCantidad(20);
		lineaPedido1.setDescuento(0D);
		lineaPedido1.setPrecioUnitario(20.3);
		
		LineaPedido lineaPedido2 = new LineaPedido();
		lineaPedido2.setIdProducto(2L);
		lineaPedido2.setPrecioUnitario(34.32);
		lineaPedido2.setDescuento(0.33);
		lineaPedido2.setCantidad(4);
		
		List<LineaPedido> lista = new ArrayList<LineaPedido>();
		lista.add(lineaPedido1);
		lista.add(lineaPedido2);

		Pedido p = new Pedido();
		p.setCarga(1D);
		p.setCiudadReceptor("Chantada");
		p.setCodigoPostalReceptor("27500");
		p.setComunidadReceptor("Galicia");
		p.setDireccionReceptor("Av. de Lugo 12 3");
		p.setFechaEntregaObligatoria(new Date(2000000));
		p.setFechaEnvio(new Date(1300000));
		p.setFechaPedido(new Date (1200000));
		p.setIdCliente("ALFKI");
		p.setIdEmpleado(1L);
		p.setIdTransportista(1L);
		p.setLineas(lista);
		p.setNombreReceptor("Alberto");
		p.setPaisReceptor("España");
		
		
			
			p = pedidoService.create(p);
			
			System.out.println("Created: "+ToStringUtil.toString(p));
					
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test created finished.\n");		
	}
	
	protected void testUpdate() {		
		System.out.println("Testing update ...");	
		
		
		try {
			
			PedidoCriteria pedidoCriteria = new PedidoCriteria();
			pedidoCriteria.setNombreReceptor("Alberto");
			pedidoCriteria.setDireccionReceptor("Av. de Lugo 12 3");

			List<Pedido> results = 
					pedidoService.findByCriteria(pedidoCriteria, 1, 10);
			if (results.size()<1) {
				throw new RuntimeException("Unexpected results count from previous tests: "+results.size());
			}
			
			Pedido p = results.get(0);
			p.setNombreReceptor("Javier");
			p.setCiudadReceptor("Taboada");
			
			LineaPedido lineaPedido = new LineaPedido();
			lineaPedido.setIdProducto(3L);
			lineaPedido.setPrecioUnitario(20.2);
			lineaPedido.setDescuento(10D);
			lineaPedido.setCantidad(3);
			lineaPedido.setIdPedido(p.getIdPedido());
			List<LineaPedido> lista = new ArrayList<LineaPedido>();
			lista.add(lineaPedido);
			p.setLineas(lista);
			
			pedidoService.update(p);
			
			p = pedidoService.findById(p.getIdPedido());
			
			System.out.println("Updated to: "+p.getNombreReceptor());
								
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test update finished.\n");		
	}	
	
	protected void testDelete() {		
		System.out.println("Testing delete ...");	
		
		try {

			PedidoCriteria pedidoCriteria = new PedidoCriteria();
			pedidoCriteria.setNombreReceptor("Javier");
			pedidoCriteria.setCiudadReceptor("Taboada");

			List<Pedido> results = 
					pedidoService.findByCriteria(pedidoCriteria, 1, 10);
			if (results.size()<1) {
				throw new RuntimeException("Unexpected results count from previous tests: "+results.size());
			}
			
			Pedido p = results.get(0);
			System.out.println("Deleting by id "+p.getIdPedido());
			pedidoService.delete(p.getIdPedido());
			
						try {
				p = pedidoService.findById(p.getIdPedido());
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
		PedidoServiceTest test = new PedidoServiceTest();
		test.testFindById();	
		test.testExists();
		/*Funciona, pero o test de FindAll llena la consola
		test.testFindAll();	*/
		test.testFindByCriteria();
		test.testCreate();
		test.testUpdate();
		test.testDelete();
	}

}
