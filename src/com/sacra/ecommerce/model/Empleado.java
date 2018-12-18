package com.sacra.ecommerce.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Empleado {
	private Long id = null;
	private String nombre = null;
	private String apellido = null;
	private String titulo = null;
	private String tituloCortesia = null;
	private Date nacimiento = null;
	private Date alta = null;
	private String direccion = null;
	private String ciudad = null;
	private String region = null;
	private String codigoPostal = null;
	private String pais = null;
	private String telefonoFijo = null;
	private String extension = null;
	private String notas = null;
	private Long llamarA = null;
	private String rutaFoto = null;
	private Long salario = null;
	
	private List<Territorio> territorio = null;
	
	public Empleado() {
		territorio = new ArrayList<Territorio>();
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTituloCortesia() {
		return tituloCortesia;
	}

	public void setTituloCortesia(String tituloCortesia) {
		this.tituloCortesia = tituloCortesia;
	}

	public Date getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}

	public Date getAlta() {
		return alta;
	}

	public void setAlta(Date alta) {
		this.alta = alta;
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

	public String getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public Long getLlamarA() {
		return llamarA;
	}

	public void setLlamarA(Long llamarA) {
		this.llamarA = llamarA;
	}

	public String getRutaFoto() {
		return rutaFoto;
	}

	public void setRutaFoto(String rutaFoto) {
		this.rutaFoto = rutaFoto;
	}

	public Long getSalario() {
		return salario;
	}

	public void setSalario(Long salario) {
		this.salario = salario;
	}

	public List<Territorio> getTerritorio() {
		return territorio;
	}

	public void setTerritorio(List<Territorio> territorio) {
		this.territorio = territorio;
	}
	
}
