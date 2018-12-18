package com.sacra.ecommerce.model;

import java.util.List;

public class Categoria {
	
	private Long id = null;
	private String nombre = null;
	private String descripcion = null;
	
	
	
	private List<Categoria> categoria = null;
	


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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
