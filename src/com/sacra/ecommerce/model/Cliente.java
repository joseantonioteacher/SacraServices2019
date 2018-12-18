package com.sacra.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hector.ledo.doval
 *
 */

public class Cliente {
	
	private String id = null;
	private String nombreCompania = null;
	private String nombre = null;
	private String titulo = null;
	private String direccion = null; 
	private String ciudad = null;
	private String region = null; 
	private String codigoPostal = null;
	private String pais = null; 
	private String telefono = null;
	private String fax = null;
	
	private List<Demografico> demograficos = null;
	
	
	public Cliente() {
		demograficos = new ArrayList<Demografico>();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getNombreCompania() {
		return nombreCompania;
	}
	
	public void setNombreCompania(String nombreCompania) {
		this.nombreCompania = nombreCompania;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	public String getRegion() {
		return region;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getCodigoPostal() {
		return codigoPostal;
	}
	
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	
	public String getPais() {
		return pais;
	}
	
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}

	public List<Demografico> getDemograficos() {
		return demograficos;
	}

	public void setDemograficos(List<Demografico> demograficos) {
		this.demograficos = demograficos;
	} 
	
	
}