package com.sacra.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.model.Provincia;
import com.sacra.ecommerce.service.ProvinciaService;

public class MockProvinciaServiceImpl implements ProvinciaService {

	private static Logger logger = LogManager.getLogger(MockProvinciaServiceImpl.class.getName());
	
	private static List<Provincia> db = null;
	
	static {
		db = new ArrayList<Provincia>();
		db.add(new Provincia(18, "A Coruña", 34));
		db.add(new Provincia(27, "Lugo", 34));
		db.add(new Provincia(36, "Pontevedra", 34));
		db.add(new Provincia(38, "Ourense", 34));
		
		db.add(new Provincia(120, "Aveiro", 35));
		db.add(new Provincia(121, "Beja", 35));
		db.add(new Provincia(122, "Braga", 35));
		
	}
			
	public MockProvinciaServiceImpl() {
		
	}
	
	// Y el parametro de pais e idioma?
	public List<Provincia> findByNombre(String nombre) throws DataException {
		
		nombre = nombre.toUpperCase();
		
		List<Provincia> results = new ArrayList<Provincia>();
		for (Provincia p: db) {
			if (p.getNombre().toUpperCase().contains(nombre));
			results.add(p);
		}
		logger.debug("Encontradas {} provincias con {}", results.size(), nombre);
		return results;		
	}
	
	public List<Provincia> findByPais(Long idPais) throws DataException {
		List<Provincia> results = new ArrayList<Provincia>();
		for (Provincia p: db) {
			if (p.getIdPais()==idPais) {
				results.add(p);
			}
		}
		logger.debug("Encontradas {} provincias de pais {}", results.size(), idPais);
		return results;
	}
	
}
