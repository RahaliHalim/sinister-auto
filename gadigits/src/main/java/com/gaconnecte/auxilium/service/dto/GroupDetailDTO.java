package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;

public class GroupDetailDTO implements Serializable {


  private Long id;
  private Long groupId;
  private String grouName;
  private Long refModeGestionId;
  private String refModeGestionName;
  private Long clientId;
  private String clientName;
  
  
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getRefModeGestionId() {
		return refModeGestionId;
	}
	public void setRefModeGestionId(Long refModeGestionId) {
		this.refModeGestionId = refModeGestionId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getGrouName() {
		return grouName;
	}
	public void setGrouName(String grouName) {
		this.grouName = grouName;
	}
	public String getRefModeGestionName() {
		return refModeGestionName;
	}
	public void setRefModeGestionName(String refModeGestionName) {
		this.refModeGestionName = refModeGestionName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
    
  
  

}