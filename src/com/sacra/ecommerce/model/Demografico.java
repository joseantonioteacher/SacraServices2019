/**
 * 
 */
package com.sacra.ecommerce.model;

/**
 * @author hector.ledo.doval
 *
 */
public class Demografico {
	
	private String clienteTipoId = null;
	private String clienteDescripcion = null;
	
	
	public Demografico() {
	
	}
	
	public String getClienteTipoId() {
		return clienteTipoId;
	}
	
	public void setClienteTipoId(String clienteTipoId) {
		this.clienteTipoId = clienteTipoId;
	}
	
	public String getClienteDescripcion() {
		return clienteDescripcion;
	}
	
	public void setClienteDescripcion(String clienteDescripcion) {
		this.clienteDescripcion = clienteDescripcion;
	}
}
