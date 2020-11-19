package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;

public class ParamDroolsDTO implements Serializable{

	
    private Long id;
	private String parametre;
	private Double defaultValue;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getParametre() {
		return parametre;
	}
	public void setParametre(String parametre) {
		this.parametre = parametre;
	}
	public Double getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(Double defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
}
