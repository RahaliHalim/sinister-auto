package com.gaconnecte.auxilium.domain.view;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "view_souscription_ga")
public class ViewSouscriptionGa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    
    @Column(name = "compagnie_id")
    private Long compagnieId;
    
    
    @Column(name = "compagnie")
    private String compagnie; 
    
    
    @Column(name = "numero_contrat")
    private String numeroContrat;
    
    
    @Column(name = "governorate_id")
    private Long zoneId; 
    
    @Column(name = "zone")
    private String zone; 
    
    @Column(name = "pack_id")
    private Long packId; 
    
    @Column(name = "pack_souscrit")
    private String pack; 
    
    
    @Column(name = "prime_ht")
    private Long primeHt;
    
    
    @Column(name = "prime_ttc")
    private Long primeTtc; 

    @Column(name = "part_reass")
    private Long partRea; 
    
    
    @Column(name = "creation_date")
    private LocalDate dateCreation;
    
    
    @Column(name = "service_id")
    private Long serviceId; 
    
    
    @Column(name = "service")
    private String service; 
    
    
    @Column(name = "charge_id")
    private Long Idcharge; 
    
    
    @Column(name = "charge")
    private String Charge;

	@Override
	public String toString() {
		return "ViewSouscriptionGa [id=" + id + ", compagnieId=" + compagnieId + ", compagnie=" + compagnie
				+ ", numeroContrat=" + numeroContrat + ", zoneId=" + zoneId + ", zone=" + zone + ", packId=" + packId
				+ ", pack=" + pack + ", primeHt=" + primeHt + ", primeTtc=" + primeTtc + ", partRea=" + partRea
				+ ", dateCreation=" + dateCreation + ", serviceId=" + serviceId + ", service=" + service + ", Idcharge="
				+ Idcharge + ", Charge=" + Charge + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCompagnieId() {
		return compagnieId;
	}

	public void setCompagnieId(Long compagnieId) {
		this.compagnieId = compagnieId;
	}

	public String getCompagnie() {
		return compagnie;
	}

	public void setCompagnie(String compagnie) {
		this.compagnie = compagnie;
	}

	public String getNumeroContrat() {
		return numeroContrat;
	}

	public void setNumeroContrat(String numeroContrat) {
		this.numeroContrat = numeroContrat;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public Long getPackId() {
		return packId;
	}

	public void setPackId(Long packId) {
		this.packId = packId;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public Long getPrimeHt() {
		return primeHt;
	}

	public void setPrimeHt(Long primeHt) {
		this.primeHt = primeHt;
	}

	public Long getPrimeTtc() {
		return primeTtc;
	}

	public void setPrimeTtc(Long primeTtc) {
		this.primeTtc = primeTtc;
	}

	public Long getPartRea() {
		return partRea;
	}

	public void setPartRea(Long partRea) {
		this.partRea = partRea;
	}

	public LocalDate getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDate dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Long getIdcharge() {
		return Idcharge;
	}

	public void setIdcharge(Long idcharge) {
		Idcharge = idcharge;
	}

	public String getCharge() {
		return Charge;
	}

	public void setCharge(String charge) {
		Charge = charge;
	} 
    
}
