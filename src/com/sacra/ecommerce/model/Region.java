package com.sacra.ecommerce.model;

import java.util.List;

public class Region {
	private Long id = null;
	private String region = null;
	
	private List<Territorio> territorio = null;
	
	public Region () {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public List<Territorio> getTerritorio() {
		return territorio;
	}

	public void setTerritorio(List<Territorio> territorio) {
		this.territorio = territorio;
	}
	

	
}
