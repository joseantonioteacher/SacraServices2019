package com.sacra.ecommerce.model;

public class Proveedor {
	
	private Long id = null;
	private String nombreCompania = null;
	private String nombreContacto = null;
	private String puestoContacto = null;
	private String direccion = null;
	private String ciudad = null;
	private String comunidad = null;
	private String codigoPostal = null;
	private String pais = null;
	private String telefono = null;
	private String fax = null;
	private String paginaInicio = null;
	
	public Proveedor () {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreCompania() {
		return nombreCompania;
	}

	public void setNombreCompania(String nombreCompania) {
		this.nombreCompania = nombreCompania;
	}

	public String getNombreContacto() {
		return nombreContacto;
	}

	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}

	public String getPuestoContacto() {
		return puestoContacto;
	}

	public void setPuestoContacto(String puestoContacto) {
		this.puestoContacto = puestoContacto;
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

	public String getComunidad() {
		return comunidad;
	}

	public void setComunidad(String comunidad) {
		this.comunidad = comunidad;
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

	public String getPaginaInicio() {
		return paginaInicio;
	}

	public void setPaginaInicio(String paginaInicio) {
		this.paginaInicio = paginaInicio;
	}
	
	
	
}
