package com.gaconnecte.auxilium.domain;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gaconnecte.auxilium.domain.app.Attachment;

@Entity
@Table(name = "ref_upload")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "upload")
public class Upload implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "referentiel_id")
    @JsonBackReference
    private Referentiel referentiel;

    @ManyToOne
    @JoinColumn(name = "user_id")
	private User user;
    
    @Column(name = "date_upload")
    private LocalDateTime dateUpload;
    
    @Column(name = "date_execution")
    private LocalDateTime dateExecution;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getDateUpload() {
		return dateUpload;
	}

	public void setDateUpload(LocalDateTime dateUpload) {
		this.dateUpload = dateUpload;
	}

	public LocalDateTime getDateExecution() {
		return dateExecution;
	}

	public void setDateExecution(LocalDateTime dateExecution) {
		this.dateExecution = dateExecution;
	}

	public Referentiel getReferentiel() {
		return referentiel;
	}

	public void setReferentiel(Referentiel referentiel) {
		this.referentiel = referentiel;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	

	@Override
	public String toString() {
		return "Upload [id=" + id + ", referentiel=" + referentiel + "]";
	}

	public Upload() {
		super();
	}
	

    }