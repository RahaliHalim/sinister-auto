package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.ArrayList;


public class RefNotifAlertDTO implements Serializable {
	
    /**
	 * 
	 */

	private Long id;
	
    private String label;
	
    private String code;

    private ZonedDateTime sendingDate;
	
    private char type;
	
    private Boolean isClosed;

    private String translationCode;

	//private List<NotifAlertUserDTO> notifAlertUser = new ArrayList<>();

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

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ZonedDateTime getSendingDate() {
		return sendingDate;
	}

	public void setSendingDate(ZonedDateTime sendingDate) {
		this.sendingDate = sendingDate;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

    public String getTranslationCode() {
		return translationCode;
	}

	public void setTranslationCode(String translationCode) {
		this.translationCode = translationCode;
	}

	public Boolean getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(Boolean isClosed) {
		this.isClosed = isClosed;
	}
    
  /*public List<NotifAlertUserDTO> getNotifAlertUser() {
		return notifAlertUser;
	}

	public void setNotifAlertUser(List<NotifAlertUserDTO> notifAlertUser) {
		this.notifAlertUser = notifAlertUser;
	}*/
    

}
