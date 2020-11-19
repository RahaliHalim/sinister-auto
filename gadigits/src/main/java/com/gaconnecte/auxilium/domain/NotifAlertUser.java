package com.gaconnecte.auxilium.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A Notification.
 */

@Entity
@Table(name = "app_notification_alert")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "app_notification_alert")
public class NotifAlertUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
	
    @ManyToOne
    @JoinColumn(name = "source")
    private UserExtra source;

    @ManyToOne
    @JoinColumn(name = "destination")
    private UserExtra destination;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "profile_id")
    private UserProfile profile;

    @ManyToOne
    @JoinColumn(name = "notification_id", referencedColumnName="id")
    private RefNotifAlert notification;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "sending_date")
    private LocalDateTime sendingDate;

    @Column(name = "settings")
    private String settings;

    @Column(name = "read")
    private Boolean read;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
    }

    public UserExtra getSource() {
        return source;
    }

    public void setSource(UserExtra source) {
        this.source = source;
    }

    public UserExtra getDestination() {
        return destination;
    }

    public void setDestination(UserExtra destination) {
        this.destination = destination;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public RefNotifAlert getNotification() {
        return notification;
    }

    public void setNotification(RefNotifAlert notification) {
        this.notification = notification;
    }

    public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
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

	public LocalDateTime getSendingDate() {
		return sendingDate;
	}

	public void setSendingDate(LocalDateTime sendingDate) {
		this.sendingDate = sendingDate;
	}
	
}
