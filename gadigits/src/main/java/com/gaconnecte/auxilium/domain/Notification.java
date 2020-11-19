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
@Table(name = "notification")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "notification")
public class Notification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
	
	@Column(name = "commentaire")
    private String commentaire;
	
	@Column(name = "jhi_date")
    private ZonedDateTime jhiDate;
	
	@Column(name = "jhi_type")
    private String jhiType;
	
	@Column(name = "is_closed")
	private Boolean isClosed;
	

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
	
}
