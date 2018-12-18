

package com.sacra.ecommerce.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido {
	
	private Long idPedido = null;
	private String idCliente = null;
	private Long idEmpleado = null;
	private Date fechaPedido = null;
	private Date fechaEntregaObligatoria = null;
	private Date fechaEnvio = null;
	private Long idTransportista = null;
	private Double carga = null;
	private String nombreReceptor = null;
	private String direccionReceptor = null;
	private String ciudadReceptor = null;
	private String comunidadReceptor = null;
	private String codigoPostalReceptor = null;
	private String paisReceptor = null;
	
	private List<LineaPedido> lineas = null;
	
	public Pedido() {
		lineas = new ArrayList<LineaPedido>();
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public Date getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public Date getFechaEntregaObligatoria() {
		return fechaEntregaObligatoria;
	}

	public void setFechaEntregaObligatoria(Date fechaEntregaObligatoria) {
		this.fechaEntregaObligatoria = fechaEntregaObligatoria;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Long getIdTransportista() {
		return idTransportista;
	}

	public void setIdTransportista(Long idTransportista) {
		this.idTransportista = idTransportista;
	}

	public Double getCarga() {
		return carga;
	}

	public void setCarga(Double carga) {
		this.carga = carga;
	}

	public String getNombreReceptor() {
		return nombreReceptor;
	}

	public void setNombreReceptor(String nombreReceptor) {
		this.nombreReceptor = nombreReceptor;
	}

	public String getDireccionReceptor() {
		return direccionReceptor;
	}

	public void setDireccionReceptor(String direccionReceptor) {
		this.direccionReceptor = direccionReceptor;
	}

	public String getCiudadReceptor() {
		return ciudadReceptor;
	}

	public void setCiudadReceptor(String ciudadReceptor) {
		this.ciudadReceptor = ciudadReceptor;
	}

	public String getComunidadReceptor() {
		return comunidadReceptor;
	}

	public void setComunidadReceptor(String comunidadReceptor) {
		this.comunidadReceptor = comunidadReceptor;
	}

	public String getCodigoPostalReceptor() {
		return codigoPostalReceptor;
	}

	public void setCodigoPostalReceptor(String codigoPostalReceptor) {
		this.codigoPostalReceptor = codigoPostalReceptor;
	}

	public String getPaisReceptor() {
		return paisReceptor;
	}

	public void setPaisReceptor(String paisReceptor) {
		this.paisReceptor = paisReceptor;
	}

	public List<LineaPedido> getLineas() {
		return lineas;
	}

	public void setLineas(List<LineaPedido> lineas) {
		this.lineas = lineas;
	}
	
	
}
