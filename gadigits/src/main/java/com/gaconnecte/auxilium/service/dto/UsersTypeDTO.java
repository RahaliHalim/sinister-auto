package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;

public class UsersTypeDTO implements Serializable{

    private Long id;
    private String libelle;

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

}