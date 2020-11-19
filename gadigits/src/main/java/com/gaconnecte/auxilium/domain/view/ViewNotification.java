package com.gaconnecte.auxilium.domain.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "view_notification")
public class ViewNotification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "translation_code")
    private String translationCode;
    
    @Column(name = "type")
    private String type;

    @Column(name = "settings")
    private String settings;
    
    @Column(name = "read")
    private Boolean read;
    
    @Column(name = "destination")
    private Long destination;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTranslationCode() {
		return translationCode;
	}

	public void setTranslationCode(String translationCode) {
		this.translationCode = translationCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSettings() {
		return settings;
	}

	public void setSettings(String settings) {
		this.settings = settings;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Long getDestination() {
		return destination;
	}

	public void setDestination(Long destination) {
		this.destination = destination;
	}
    
   
}
