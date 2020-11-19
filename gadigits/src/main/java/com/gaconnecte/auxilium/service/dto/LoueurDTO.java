package com.gaconnecte.auxilium.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the Loueur entity.
 */
public class LoueurDTO implements Serializable {

    private Long id;

    private Long code;

    private String raisonSociale;

    private String matriculeFiscale;

    private String registreCommerce;

    private Boolean conventionne;

    private LocalDate dateEffetConvention;

    private LocalDate dateFinConvention;

    private Integer nbrVehicules;

    private String address;

    private Boolean blocage;

    private String rib;
    
    private Long reglementId;
  
	private Long governorateId;
	private String governorateLabel;


	private Long delegationId;
	private String delegationLabel;

	private String telephone;
	private String reclamation;


    private Long motifId;
    
	private List<VisAVisDTO> visAViss = new ArrayList<>();
	
	private List<VehiculeLoueurDTO> vehicules =  new ArrayList<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMatriculeFiscale() {
        return matriculeFiscale;
    }

    public void setMatriculeFiscale(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
    }

    public String getRegistreCommerce() {
        return registreCommerce;
    }

    public void setRegistreCommerce(String registreCommerce) {
        this.registreCommerce = registreCommerce;
    }

    public Boolean isConventionne() {
        return conventionne;
    }

    public void setConventionne(Boolean conventionne) {
        this.conventionne = conventionne;
    }

    public LocalDate getDateEffetConvention() {
        return dateEffetConvention;
    }

    public void setDateEffetConvention(LocalDate dateEffetConvention) {
        this.dateEffetConvention = dateEffetConvention;
    }

    public LocalDate getDateFinConvention() {
        return dateFinConvention;
    }

    public void setDateFinConvention(LocalDate dateFinConvention) {
        this.dateFinConvention = dateFinConvention;
    }

    public Integer getNbrVehicules() {
        return nbrVehicules;
    }

    public void setNbrVehicules(Integer nbrVehicules) {
        this.nbrVehicules = nbrVehicules;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean isBlocage() {
        return blocage;
    }

    public void setBlocage(Boolean blocage) {
        this.blocage = blocage;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public List<VisAVisDTO> getVisAViss() {
		return visAViss;
	}

	public void setVisAViss(List<VisAVisDTO> visAViss) {
		this.visAViss = visAViss;
	}

	public Boolean getConventionne() {
		return conventionne;
	}

	public Boolean getBlocage() {
		return blocage;
	}

	public Long getReglementId() {
		return reglementId;
	}

	public void setReglementId(Long reglementId) {
		this.reglementId = reglementId;
	}

	public Long getGovernorateId() {
		return governorateId;
	}

	public void setGovernorateId(Long governorateId) {
		this.governorateId = governorateId;
	}

	public Long getDelegationId() {
		return delegationId;
	}

	public void setDelegationId(Long delegationId) {
		this.delegationId = delegationId;
	}
	

	public Long getMotifId() {
		return motifId;
	}

	public void setMotifId(Long motifId) {
		this.motifId = motifId;
	}

	public String getGovernorateLabel() {
		return governorateLabel;
	}

	public void setGovernorateLabel(String governorateLabel) {
		this.governorateLabel = governorateLabel;
	}

	public String getDelegationLabel() {
		return delegationLabel;
	}

	public void setDelegationLabel(String delegationLabel) {
		this.delegationLabel = delegationLabel;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getReclamation() {
		return reclamation;
	}

	public void setReclamation(String reclamation) {
		this.reclamation = reclamation;
	}
	
	public List<VehiculeLoueurDTO> getVehicules() {
		return vehicules;
	}

	public void setVehicules(List<VehiculeLoueurDTO> vehicules) {
		this.vehicules = vehicules;
	}

	public String getRaisonSociale() {
		return raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}
	
}
