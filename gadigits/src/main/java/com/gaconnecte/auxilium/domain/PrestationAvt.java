package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gaconnecte.auxilium.Utils.CustomJsonDateDeserializer;
import com.gaconnecte.auxilium.domain.enumeration.EtatDossierRmq;
import com.gaconnecte.auxilium.Utils.CustomJsonDateSerializer;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A PrestationAvt.
 */
@Entity
@Table(name = "prestation_avt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PrestationAvt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    private RefTypeService typeService;

    @ManyToOne(optional = false)
    @NotNull
    private Dossier dossier;

    @Column(name = "is_delete")
	private Boolean isDelete;
	
	@Column(name = "date_derniere_maj", nullable = false)
	private ZonedDateTime dateDerniereMaj;

	@NotNull
    @Column(name = "date_creation", nullable = false)
    private ZonedDateTime dateCreation;

    @Column(name = "date_cloture", nullable = false)
    private ZonedDateTime dateCloture;

    @Column(name = "is_ferier")
    private Boolean isFerier;

    @Column(name = "is_nuit")
    private Boolean isNuit;

    @Column(name = "is_poids_lourd")
    private Boolean isPoidsLourd;

    @Column(name = "is_demi_majore")
    private Boolean isDemiMajore;

    @Size(max = 2000)
    @Column(name = "commentaire", length = 2000)
    private String commentaire;

    @ManyToOne(optional = true)
    private User user;
    
    @Enumerated(EnumType.STRING)
    private EtatDossierRmq etat;

    @Column(name = "tug_arrival_date")
    private ZonedDateTime tugArrivalDate;

    @Column(name = "insured_arrival_date")
    private ZonedDateTime insuredArrivalDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public ZonedDateTime getDateCloture() {
        return dateCloture;
    }
        
    public void setDateCloture(ZonedDateTime dateCloture) {
        this.dateCloture = dateCloture;
    }

    public Boolean isIsFerier() {
        return isFerier;
    }

    public PrestationAvt isFerier(Boolean isFerier) {
        this.isFerier = isFerier;
        return this;
    }

    public void setIsFerier(Boolean isFerier) {
        this.isFerier = isFerier;
    }

    public Boolean isIsNuit() {
        return isNuit;
    }

    public PrestationAvt isNuit(Boolean isNuit) {
        this.isNuit = isNuit;
        return this;
    }

    public void setIsNuit(Boolean isNuit) {
        this.isNuit = isNuit;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public PrestationAvt commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    public Boolean getIsPoidsLourd() {
		return isPoidsLourd;
	}

	public void setIsPoidsLourd(Boolean isPoidsLourd) {
		this.isPoidsLourd = isPoidsLourd;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public Boolean getIsFerier() {
		return isFerier;
	}

	public Boolean getIsNuit() {
		return isNuit;
	}

	public Boolean isIsDelete() {
        return isDelete;
    }

    public PrestationAvt isDelete(Boolean isDelete) {
        this.isDelete = isDelete;
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
	}
	
	public ZonedDateTime getDateCreation() {
        return dateCreation;
    }

    public PrestationAvt dateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }
    
    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
	}
	
	public ZonedDateTime getDateDerniereMaj() {
        return dateDerniereMaj;
    }

    public PrestationAvt dateDerniereMaj(ZonedDateTime dateDerniereMaj) {
        this.dateDerniereMaj = dateDerniereMaj;
        return this;
    }
    
    public void setDateDerniereMaj(ZonedDateTime dateDerniereMaj) {
        this.dateDerniereMaj = dateDerniereMaj;
	}

    public Dossier getDossier() {
        return dossier;
    }

    public PrestationAvt dossier(Dossier dossier) {
        this.dossier = dossier;
        return this;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    public RefTypeService getTypeService() {
        return typeService;
    }

    public PrestationAvt typeService(RefTypeService refTypeService) {
        this.typeService = refTypeService;
        return this;
    }

    public void setTypeService(RefTypeService refTypeService) {
        this.typeService = refTypeService;
    }

    
    public Boolean getIsDemiMajore() {
		return isDemiMajore;
	}

	public void setIsDemiMajore(Boolean isDemiMajore) {
		this.isDemiMajore = isDemiMajore;
	}

	public EtatDossierRmq getEtat() {
		return etat;
	}

	public void setEtat(EtatDossierRmq etat) {
		this.etat = etat;
	}

	public ZonedDateTime getTugArrivalDate() {
		return tugArrivalDate;
	}
	
	public void setTugArrivalDate(ZonedDateTime tugArrivalDate) {
		this.tugArrivalDate = tugArrivalDate;
	}

	public ZonedDateTime getInsuredArrivalDate() {
		return insuredArrivalDate;
	}
	
	public void setInsuredArrivalDate(ZonedDateTime insuredArrivalDate) {
		this.insuredArrivalDate = insuredArrivalDate;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrestationAvt prestationAvt = (PrestationAvt) o;
        if (prestationAvt.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prestationAvt.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrestationAvt{" +
            "id=" + getId() +
            "}";
    }
}
