package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RefBareme entity.
 */
public class RefBaremeDTO implements Serializable {

    private Long id;

    private Integer code;

    private Integer responsabiliteX;

    private Integer responsabiliteY;

    private String description;

    private String title;

    private String chemin;

    private Long pieceJointeId;

    private AttachmentDTO attachment;

    private String attachment64;

    private String attachmentName;

    private String nomImage;

    public String getNomImage() {
        return nomImage;
    }

    public void setNomImage(String nomImage) {
        this.nomImage = nomImage;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getResponsabiliteX() {
        return responsabiliteX;
    }

    public void setResponsabiliteX(Integer responsabiliteX) {
        this.responsabiliteX = responsabiliteX;
    }

    public Integer getResponsabiliteY() {
        return responsabiliteY;
    }

    public void setResponsabiliteY(Integer responsabiliteY) {
        this.responsabiliteY = responsabiliteY;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AttachmentDTO getAttachment() {
        return attachment;
    }

    public void setAttachment(AttachmentDTO attachment) {
        this.attachment = attachment;
    }

    public String getAttachment64() {
        return attachment64;
    }

    public void setAttachment64(String attachment64) {
        this.attachment64 = attachment64;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RefBaremeDTO refBaremeDTO = (RefBaremeDTO) o;
        if (refBaremeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refBaremeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefBaremeDTO{" + "id=" + getId() + ", code='" + getCode() + "'" + ", responsabiliteX='"
                + getResponsabiliteX() + "'" + ", responsabiliteY='" + getResponsabiliteY() + "'" + ", description='"
                + getDescription() + "'" + "}";
    }
}
