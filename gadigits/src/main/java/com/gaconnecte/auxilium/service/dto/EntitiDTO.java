package com.gaconnecte.auxilium.service.dto;
import java.io.Serializable;

public class EntitiDTO implements Serializable {

    private Long id;
    private String label;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}


}