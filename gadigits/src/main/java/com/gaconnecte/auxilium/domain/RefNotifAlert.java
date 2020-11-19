package com.gaconnecte.auxilium.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A Notification.
 */

@Entity
@Table(name = "ref_notification_alert")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ref_notification_alert")
public class RefNotifAlert implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
	
	@Column(name = "label")
    private String label;

    @Column(name = "code")
    private String code;
	
	@Column(name = "sending_date")
    private ZonedDateTime sendingDate;
	
	@Column(name = "type")
    private char type;
	
	@Column(name = "is_closed")
	private Boolean isClosed;

    @Column(name = "translation_code")
    private String translationCode;
	
	/*@OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<NotifAlertUser> notifAlertUser = new ArrayList<>();*/

	public RefNotifAlert() {
    }

	public RefNotifAlert(Long id) {
        this.id = id;
    }

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

    public String getTranslationCode() {
		return translationCode;
	}

	public void setTranslationCode(String translationCode) {
		this.translationCode = translationCode;
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

	public Boolean getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(Boolean isClosed) {
		this.isClosed = isClosed;
	}
	
	/*public List<NotifAlertUser> getNotifAlertUser() {
		return notifAlertUser;
	}

	public void setNotifAlertUser(List<NotifAlertUser> notifAlertUser) {
		this.notifAlertUser = notifAlertUser;
	}*/
	
}
