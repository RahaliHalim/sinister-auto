package com.gaconnecte.auxilium.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Statement entity.
 */
public class StatementDTO implements Serializable {

    private Long id;

    private String name;

    private String path;

    private Long tugId;
    
    private String tugLabel;

    private LocalDate creationDate;

    private String notes;
    
    private String attachment64;
	
	private Integer step;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getTugId() {
        return tugId;
    }

    public void setTugId(Long tugId) {
        this.tugId = tugId;
    }

    public String getTugLabel() {
        return tugLabel;
    }

    public void setTugLabel(String tugLabel) {
        this.tugLabel = tugLabel;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAttachment64() {
        return attachment64;
    }

    public void setAttachment64(String attachment64) {
        this.attachment64 = attachment64;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StatementDTO statementDTO = (StatementDTO) o;
        if(statementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), statementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StatementDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", path='" + getPath() + "'" +
            ", tugId='" + getTugId() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
