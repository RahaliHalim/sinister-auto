package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.ArrayList;


public class NotifAlertUserDTO implements Serializable {
	
    /**
	 * 
	 */

  private Long id;
	
    private Long source;
	
    private Long destination;

    private Long profil;

    private RefNotifAlertDTO notification;

    private String entityName;

    private Long entityId;  

    private LocalDateTime sendingDate;

		private String settings;

		private Boolean read;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public Long getSource() {
		return source;
	}

	public void setSource(Long source) {
		this.source = source;
	}

    public Long getDestination() {
		return destination;
	}

	public void setDestination(Long destination) {
		this.destination = destination;
	}

    public Long getProfil() {
		return profil;
	}

	public void setProfil(Long profil) {
		this.profil = profil;
	}

  public RefNotifAlertDTO getNotification() {
		return notification;
	}

	public void setNotification(RefNotifAlertDTO notification) {
		this.notification = notification;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

    public LocalDateTime getSendingDate() {
		return sendingDate;
	}

	public void setSendingDate(LocalDateTime sendingDate) {
		this.sendingDate = sendingDate;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
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

}
