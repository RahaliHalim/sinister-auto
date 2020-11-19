package com.gaconnecte.auxilium.service.prm.dto;

import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import com.gaconnecte.auxilium.service.referential.dto.RefPackDTO;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the ConventionAmendment entity.
 */
public class ConventionAmendmentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer serial;

    private Long conventionId;

    private LocalDate startDate;
    private LocalDate endDate;

    private RefPackDTO refPack;

    private Long oldRefPackId;

    private AttachmentDTO attachment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOldRefPackId(Long oldRefPackId) {
        this.oldRefPackId = oldRefPackId;
    }

    public Long getOldRefPackId() {
        return oldRefPackId;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public Long getConventionId() {
        return conventionId;
    }

    public void setConventionId(Long conventionId) {
        this.conventionId = conventionId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public AttachmentDTO getAttachment() {
        return attachment;
    }

    public void setAttachment(AttachmentDTO attachment) {
        this.attachment = attachment;
    }

    public RefPackDTO getRefPack() {
        return refPack;
    }

    public void setRefPack(RefPackDTO refPack) {
        this.refPack = refPack;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConventionAmendmentDTO other = (ConventionAmendmentDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ConventionAmendmentDTO [id=" + id + ", serial=" + serial + ", conventionId=" + conventionId
                + ", startDate=" + startDate + ", endDate=" + endDate + ", refPack=" + refPack + ", attachment="
                + attachment + "]";
    }

}
