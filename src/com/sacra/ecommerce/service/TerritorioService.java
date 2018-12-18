package com.sacra.ecommerce.service;

import java.util.List;

import com.sacra.ecommerce.exceptions.DataException;
import com.sacra.ecommerce.exceptions.DuplicateInstanceException;
import com.sacra.ecommerce.exceptions.InstanceNotFoundException;
import com.sacra.ecommerce.model.Territorio;

public interface TerritorioService {

	public Territorio findById(Long id) throws InstanceNotFoundException, DataException;

	public Boolean exists(Long id) throws DataException;

	public List<Territorio> findAll(int startIndex, int count) throws DataException;

	public long countAll() throws DataException;

	public List<Territorio> findByNombre(String nombre, int startIndex, int count) throws DataException;

	public Territorio create(Territorio t) throws DuplicateInstanceException, DataException;

	public void update(Territorio t) throws InstanceNotFoundException, DataException;

	public long delete(Long id) throws InstanceNotFoundException, DataException;
}