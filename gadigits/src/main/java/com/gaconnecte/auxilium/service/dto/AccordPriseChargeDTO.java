package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;

public class AccordPriseChargeDTO implements Serializable {

    /**
     *
     */
    /*private DevisDTO devis;
    private VehiculeAssureDTO vehiculeAssure;
    private AssureDTO assure;
    private ExpertDTO expert;
    private ReparateurDTO reparateur;
    private DossierDTO dossier;
    private ContratAssuranceDTO contratAssurance;
    private PrestationDTO prestation;
    private String reparateurNom;
    private String assureNom;
    private String assureGsm;
    private PartnerDTO refCompagnie;
    private VehicleUsageDTO refUsageContrat;
    private PrestationPECDTO prestationPEC;
    private AgencyDTO refAgence;*/
    private Long quotationId;
    private SinisterPecDTO sinisterPecDTO;

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public SinisterPecDTO getSinisterPecDTO() {
        return sinisterPecDTO;
    }

    public void setSinisterPecDTO(SinisterPecDTO sinisterPecDTO) {
        this.sinisterPecDTO = sinisterPecDTO;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sinisterPecDTO == null) ? 0 : sinisterPecDTO.hashCode());
       
        return result;
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
        AccordPriseChargeDTO other = (AccordPriseChargeDTO) obj;
        if (sinisterPecDTO == null) {
            if (other.sinisterPecDTO != null) {
                return false;
            }
        } 
        return true;
    }

    @Override
    public String toString() {
        return "AccordPriseChargeDTO [sinisterPecDTO=" + sinisterPecDTO + "]";
    }

}
