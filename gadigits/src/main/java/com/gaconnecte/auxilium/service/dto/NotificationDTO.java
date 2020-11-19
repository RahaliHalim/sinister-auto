package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.ArrayList;


public class NotificationDTO implements Serializable {
	
    /**
	 * 
	 */

	private Long id;
	
    private String commentaire;
	
    private ZonedDateTime jhiDate;
	
    private String jhiType;
	
    private Boolean isClosed;

	private List<NotificationUserProfileDTO> notificationUserProfile = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public ZonedDateTime getJhiDate() {
		return jhiDate;
	}

	public void setJhiDate(ZonedDateTime jhiDate) {
		this.jhiDate = jhiDate;
	}

	public String getJhiType() {
		return jhiType;
	}

	public void setJhiType(String jhiType) {
		this.jhiType = jhiType;
	}

	public Boolean getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(Boolean isClosed) {
		this.isClosed = isClosed;
	}
    
    public List<NotificationUserProfileDTO> getNotificationUserProfile() {
		return notificationUserProfile;
	}

	public void setNotificationUserProfile(List<NotificationUserProfileDTO> notificationUserProfile) {
		this.notificationUserProfile = notificationUserProfile;
	}
    

}
