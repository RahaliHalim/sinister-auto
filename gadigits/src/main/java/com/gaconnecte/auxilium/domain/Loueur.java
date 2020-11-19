package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Loueur.
 */
@Entity
@Table(name = "ref_loueur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ref_loueur")
public class Loueur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private Long code;
    
    @Column(name = "raison_sociale")
    private String raisonSociale;

    @Column(name = "matricule_fiscale")
    private String matriculeFiscale;

    @Column(name = "registre_commerce")
    private String registreCommerce;

    @Column(name = "conventionne")
    private Boolean conventionne;

    @Column(name = "date_effet_convention")
    private LocalDate dateEffetConvention;

    @Column(name = "date_fin_convention")
    private LocalDate dateFinConvention;

    @Column(name = "nbr_vehicules")
    private Integer nbrVehicules;

    @Column(name = "address")
    private String address;

    @Column(name = "blocage")
    private Boolean blocage;

    @Column(name = "rib")
    private String rib;
    
    @Column(name = "telephone")
	private String telephone;
    
    @Column(name = "reclamation")
	private String reclamation;
    
    
    @ManyToOne
    @JoinColumn(name = "reglement_id")
    private RefModeReglement reglement;
  
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "governorate_id")
	private Governorate governorate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delegation_id")
	private Delegation delegation;
	
    @ManyToOne
    @JoinColumn(name = "motif_id")
    private RaisonAssistance motif;
    
	@OneToMany(mappedBy = "loueur", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VisAVis> visAViss = new ArrayList<>();
	
    
	@OneToMany(mappedBy = "loueur", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VehiculeLoueur> vehicules = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCode() {
        return code;
    }

    public Loueur code(Long code) {
        this.code = code;
        return this;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMatriculeFiscale() {
        return matriculeFiscale;
    }

    public Loueur matriculeFiscale(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
        return this;
    }

    public void setMatriculeFiscale(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
    }

    public String getRegistreCommerce() {
        return registreCommerce;
    }

    public Loueur registreCommerce(String registreCommerce) {
        this.registreCommerce = registreCommerce;
        return this;
    }

    public void setRegistreCommerce(String registreCommerce) {
        this.registreCommerce = registreCommerce;
    }

    public Boolean isConventionne() {
        return conventionne;
    }

    public Loueur conventionne(Boolean conventionne) {
        this.conventionne = conventionne;
        return this;
    }

    public void setConventionne(Boolean conventionne) {
        this.conventionne = conventionne;
    }

    public LocalDate getDateEffetConvention() {
        return dateEffetConvention;
    }

    public Loueur dateEffetConvention(LocalDate dateEffetConvention) {
        this.dateEffetConvention = dateEffetConvention;
        return this;
    }

    public void setDateEffetConvention(LocalDate dateEffetConvention) {
        this.dateEffetConvention = dateEffetConvention;
    }

    public LocalDate getDateFinConvention() {
        return dateFinConvention;
    }

    public Loueur dateFinConvention(LocalDate dateFinConvention) {
        this.dateFinConvention = dateFinConvention;
        return this;
    }

    public void setDateFinConvention(LocalDate dateFinConvention) {
        this.dateFinConvention = dateFinConvention;
    }

    public Integer getNbrVehicules() {
        return nbrVehicules;
    }

    public Loueur nbrVehicules(Integer nbrVehicules) {
        this.nbrVehicules = nbrVehicules;
        return this;
    }

    public void setNbrVehicules(Integer nbrVehicules) {
        this.nbrVehicules = nbrVehicules;
    }

    public String getAddress() {
        return address;
    }

    public Loueur address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean isBlocage() {
        return blocage;
    }

    public Loueur blocage(Boolean blocage) {
        this.blocage = blocage;
        return this;
    }

    public void setBlocage(Boolean blocage) {
        this.blocage = blocage;
    }

    public String getRib() {
        return rib;
    }

    public Loueur rib(String rib) {
        this.rib = rib;
        return this;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public RefModeReglement getReglement() {
		return reglement;
	}

	public void setReglement(RefModeReglement reglement) {
		this.reglement = reglement;
	}

	public Governorate getGovernorate() {
		return governorate;
	}

	public void setGovernorate(Governorate governorate) {
		this.governorate = governorate;
	}

	public Delegation getDelegation() {
		return delegation;
	}

	public void setDelegation(Delegation delegation) {
		this.delegation = delegation;
	}

	public RaisonAssistance getMotif() {
		return motif;
	}

	public void setMotif(RaisonAssistance motif) {
		this.motif = motif;
	}

	public List<VisAVis> getVisAViss() {
		return visAViss;
	}

	public void setVisAViss(List<VisAVis> visAViss) {
		this.visAViss = visAViss;
	}

	public Boolean getConventionne() {
		return conventionne;
	}

	public Boolean getBlocage() {
		return blocage;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

    public List<VehiculeLoueur> getVehicules() {
		return vehicules;
	}

	public void setVehicules(List<VehiculeLoueur> vehicules) {
		this.vehicules = vehicules;
	}

	public String getReclamation() {
		return reclamation;
	}

	public void setReclamation(String reclamation) {
		this.reclamation = reclamation;
	}

	public String getRaisonSociale() {
		return raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}

	

    @Override
    public String toString() {
        return "Loueur{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", matriculeFiscale='" + getMatriculeFiscale() + "'" +
            ", registreCommerce='" + getRegistreCommerce() + "'" +
            ", conventionne='" + isConventionne() + "'" +
            ", dateEffetConvention='" + getDateEffetConvention() + "'" +
            ", dateFinConvention='" + getDateFinConvention() + "'" +
            ", nbrVehicules='" + getNbrVehicules() + "'" +
            ", address='" + getAddress() + "'" +
            ", blocage='" + isBlocage() + "'" +
            ", rib='" + getRib() + "'" +
            "}";
    }
}
