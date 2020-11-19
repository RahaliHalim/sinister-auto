package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;

public class FeaturesDTO implements Serializable{

	private Long id;
	private String libelle;
	private String name;
	private String url;
	private Long entityId;
	private String entitiLabel;
	private Long parent;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getEntityId() {
		return entityId;
	}
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	public String getEntitiLabel() {
		return entitiLabel;
	}
	public void setEntitiLabel(String entitiLabel) {
		this.entitiLabel = entitiLabel;
	}
	public Long getParent() {
		return parent;
	}
	public void setParent(Long parent) {
		this.parent = parent;
	}
	
	
}
