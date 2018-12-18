package com.sacra.ecommerce.model;

import java.util.List;

public class Producto {
	
	private Long id = null;
	private String nombre = null;
	private Integer idProveedor = null;
	private Integer idCategoria =null;
	private String cantidadPorUnidad = null;
	private Double precioUnitario = (Double) null;
	private Integer unidadesEnStock = null;
	private Integer unidadesEnPedido = null;
	private Integer nivelDeReordenacion = null;
	private Boolean descontinuado = (Boolean) null;
	
	private List<Producto> productos = null;

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

	public Integer getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getCantidadPorUnidad() {
		return cantidadPorUnidad;
	}

	public void setCantidadPorUnidad(String cantidadPorUnidad) {
		this.cantidadPorUnidad = cantidadPorUnidad;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Integer getUnidadesEnStock() {
		return unidadesEnStock;
	}

	public void setUnidadesEnStock(Integer unidadesEnStock) {
		this.unidadesEnStock = unidadesEnStock;
	}

	public Integer getUnidadesEnPedido() {
		return unidadesEnPedido;
	}

	public void setUnidadesEnPedido(Integer unidadesEnPedido) {
		this.unidadesEnPedido = unidadesEnPedido;
	}

	public Integer getNivelDeReordenacion() {
		return nivelDeReordenacion;
	}

	public void setNivelDeReordenacion(Integer nivelDeReordenacion) {
		this.nivelDeReordenacion = nivelDeReordenacion;
	}

	public boolean isDescontinuado() {
		return descontinuado;
	}

	public void setDescontinuado(boolean descontinuado) {
		this.descontinuado = descontinuado;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	

	
}