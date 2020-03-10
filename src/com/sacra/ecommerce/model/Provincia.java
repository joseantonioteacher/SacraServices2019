package com.sacra.ecommerce.model;

public class Provincia {

	private int id;
	private String nombre;
	private int idPais;
	
	public Provincia() {
		
	}
	
	public Provincia(int id, String nombre, int idPais) {
		setId(id);
		setNombre(nombre);
		setIdPais(idPais);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getIdPais() {
		return idPais;
	}

	public void setIdPais(int idPais) {
		this.idPais = idPais;
	}
}
