package com.sacra.ecommerce.service;

import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.model.Provincia;

public interface ProvinciaService {

	public List<Provincia> findByNombre(String nombre) throws DataException;
	
	public List<Provincia> findByPais(Long idPais) throws DataException;
}
