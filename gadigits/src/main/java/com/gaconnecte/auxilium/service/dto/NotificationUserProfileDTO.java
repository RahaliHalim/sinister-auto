package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;


public class NotificationUserProfileDTO implements Serializable {
	
    /**
	 * 
	 */

	private Long id;

    private Long userProfileId;

    private Long userExtraId;

    private Long notificationId;


    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    public Long getUserExtraId() {
        return userExtraId;
    }

    public void setUserExtraId(Long userExtraId) {
        this.userExtraId = userExtraId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }



}