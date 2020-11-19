package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.validation.constraints.Size;

public class UploadDTO implements Serializable {
	
	private Long id;

    private Long referentielId;
    
    private String  libelle;

	private String chemin;
	
	private Long pieceJointeId;
	
	private AttachmentDTO attachment;
	
	private Long userId;
	
	private String firstName;
	
	private String lastName;
	
    private LocalDateTime dateUpload;
    
    private LocalDateTime dateExecution;
    
    private String  attachmentName;
    
    private String  attachment64;
    
    private String  originalName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getReferentielId() {
		return referentielId;
	}

	public void setReferentielId(Long referentielId) {
		this.referentielId = referentielId;
	}

	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}

	public Long getPieceJointeId() {
		return pieceJointeId;
	}

	public void setPieceJointeId(Long pieceJointeId) {
		this.pieceJointeId = pieceJointeId;
	}
	
	

	public AttachmentDTO getAttachment() {
		return attachment;
	}

	public void setAttachment(AttachmentDTO attachment) {
		this.attachment = attachment;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getAttachment64() {
		return attachment64;
	}

	public void setAttachment64(String attachment64) {
		this.attachment64 = attachment64;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UploadDTO uploadDTO = (UploadDTO) o;
        if(uploadDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uploadDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


	public UploadDTO(Long referentielId, String chemin, Long pieceJointeId,String libelle) {
		super();
		this.referentielId = referentielId;
		this.chemin = chemin;
		this.pieceJointeId = pieceJointeId;
		this.libelle = libelle;
	}

	public UploadDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	
    
    
}
