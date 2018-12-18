package com.sacra.ecommerce.model;

public class Transportista {
	
	private Long id = null;
	private String nombre = null;
	private String telefono = null;  
	
	public Transportista() {		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
}
