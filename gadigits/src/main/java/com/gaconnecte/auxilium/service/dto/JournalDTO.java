package com.gaconnecte.auxilium.service.dto;


import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Journal entity.
 */
public class JournalDTO implements Serializable {

    private Long id;

    @Size(max = 2000)
    private String commentaire;

    @NotNull
    private ZonedDateTime timestamp;

    private Long sysActionUtilisateurId;

    private String sysActionUtilisateurNom;

    private String utilisateur;

    private String ipaddress;

    private String reference;

    private Long dossierId;
    private String dossierReference;

    private Long prestationId;
    private String prestationReference;
    private Long reparateurId;

    private Long refRemorqueurId;
    private Long quotationId;
    private String refRemorqueurReference;

    private Set<RefMotifDTO> motifs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getRefRemorqueurId() {
		return refRemorqueurId;
	}

	public void setRefRemorqueurId(Long refRemorqueurId) {
		this.refRemorqueurId = refRemorqueurId;
	}

	public String getRefRemorqueurReference() {
		return refRemorqueurReference;
	}

	public void setRefRemorqueurReference(String refRemorqueurReference) {
		this.refRemorqueurReference = refRemorqueurReference;
	}

	public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getSysActionUtilisateurId() {
        return sysActionUtilisateurId;
    }

    public void setSysActionUtilisateurId(Long sysActionUtilisateurId) {
        this.sysActionUtilisateurId = sysActionUtilisateurId;
    }

    public String getSysActionUtilisateurNom() {
        return sysActionUtilisateurNom;
    }

    public void setSysActionUtilisateurNom(String sysActionUtilisateurNom) {
        this.sysActionUtilisateurNom = sysActionUtilisateurNom;
    }


    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Set<RefMotifDTO> getMotifs() {
        return motifs;
    }

    public void setMotifs(Set<RefMotifDTO> refMotifs) {
        this.motifs = refMotifs;
    }

    public Long getDossierId() {
        return dossierId;
    }

    public void setDossierId(Long dossierId) {
        this.dossierId = dossierId;
    }

    public String getDossierReference() {
        return dossierReference;
    }

    public void setDossierReference(String dossierReference) {
        this.dossierReference = dossierReference;
    }

    public Long getPrestationId() {
        return prestationId;
    }

    public void setPrestationId(Long prestationId) {
        this.prestationId = prestationId;
    }

    public String getPrestationReference() {
        return prestationReference;
    }

    public void setPrestationReference(String prestationReference) {
        this.prestationReference = prestationReference;
    }

    public Long getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Long quotationId) {
		this.quotationId = quotationId;
	}

	public Long getReparateurId() {
		return reparateurId;
	}

	public void setReparateurId(Long reparateurId) {
		this.reparateurId = reparateurId;
	}

	

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JournalDTO journalDTO = (JournalDTO) o;
        if (journalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), journalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JournalDTO{" +
            "id=" + id +
            ", commentaire='" + commentaire + '\'' +
            ", timestamp=" + timestamp +
            ", utilisateur='" + utilisateur + '\'' +
            ", ipaddress='" + ipaddress + '\'' +
            '}';
    }
}
