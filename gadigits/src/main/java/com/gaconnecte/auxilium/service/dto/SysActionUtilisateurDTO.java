package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SysActionUtilisateur entity.
 */
public class SysActionUtilisateurDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String nom;

    private Set<RefMotifDTO> motifs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<RefMotifDTO> getMotifs() {
        return motifs;
    }

    public void setMotifs(Set<RefMotifDTO> refMotifs) {
        this.motifs = refMotifs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysActionUtilisateurDTO sysActionUtilisateurDTO = (SysActionUtilisateurDTO) o;
        if(sysActionUtilisateurDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysActionUtilisateurDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysActionUtilisateurDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
