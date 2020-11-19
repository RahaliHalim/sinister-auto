package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.time.Instant;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A DTO for the ReparateurGrille entity.
 */
public class ReparateurGrilleDTO implements Serializable {

    private Long id;

    private Long reparateurId;

    private Long grilleId;

    private LocalDate date;

    private Long refTypeInterventionId;

    private String refTypeInterventionLibelle;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReparateurId() {
        return reparateurId;
    }

    public void setReparateurId(Long reparateurId) {
        this.reparateurId = reparateurId;
    }

    public Long getGrilleId() {
        return grilleId;
    }

    public void setGrilleId(Long grilleId) {
        this.grilleId = grilleId;
    }


    public Long getRefTypeInterventionId() {
        return refTypeInterventionId;
    }

     public void setRefTypeInterventionId(Long refTypeInterventionId) {
        this.refTypeInterventionId = refTypeInterventionId;
    }


    public String getRefTypeInterventionLibelle() {
        return refTypeInterventionLibelle;
    }

    public void setRefTypeInterventionLibelle(String refTypeInterventionLibelle) {
        this.refTypeInterventionLibelle = refTypeInterventionLibelle;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReparateurGrilleDTO reparateurGrilleDTO = (ReparateurGrilleDTO) o;
        if(reparateurGrilleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reparateurGrilleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReparateurGrilleDTO{" +
            "id=" + getId() +
            "}";
    }
}