package com.gaconnecte.auxilium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * A RefRemorqueur.
 */
@Entity
@Table(name = "ref_remorqueur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "refremorqueur")
public class RefRemorqueur implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;
  
	@Column(name = "telephone")
	private String telephone;

	@Column(name = "mail")
	private String mail; 
	
	@Column(name = "device_id")
	private String deviceId;

    @Column(name = "has_habillage", nullable = false)
	private Boolean hasHabillage;
	@Column(name = "is_conventionne")
	private Boolean isConventionne;
	
	@Column(name = "is_bloque")
	private Boolean isBloque;
	
	
	@Column(name = "reclamation")
	private String reclamation;

	@Column(name = "is_free")
	private Boolean isFree;
	
	@Column(name = "is_connected")
	private Boolean isConnected;
	

	public String getReclamation() {
		return reclamation;
	}

	public void setReclamation(String reclamation) {
		this.reclamation = reclamation;
	}
	
	public RefRemorqueur reclamation(String reclamation) {
		this.reclamation = reclamation;
		return this;
	}

	@Column(name = "is_delete")
	private Boolean isDelete;


	@Column(name = "latitude")
	private Double latitude;
	
	@Column(name = "longitude")
	private Double longitude;
	
	@Column(name = "nombre_camion")
	private Integer nombreCamion;

	@OneToOne(optional = false)
	@NotNull
	@JoinColumn(unique = true)
	private PersonneMorale societe;
	@Column(name = "mdp")
	private String mdp;

	
	@OneToMany(mappedBy = "remorqueur", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VisAVis> visAViss = new ArrayList<>();

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Boolean getHasHabillage() {
		return hasHabillage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    public String getTelephone() {
	        return telephone;
	    }

    public RefRemorqueur telephone(String telephone) {
	        this.telephone = telephone;
	        return this;
	    }

	public void setTelephone(String telephone) {
	        this.telephone = telephone;
	    }
	
	public Boolean getIsDelete() {
		return isDelete;
	}

	
	
	public Boolean getIsConnected() {
		return isConnected;
	}

	public void setIsConnected(Boolean isConnected) {
		this.isConnected = isConnected;
	}

	public RefRemorqueur isDelete(Boolean isDelete) {
		this.isDelete = isDelete;
		return this;
	}
	
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	   public Boolean isHasHabillage() {
			return hasHabillage;
		}

		public RefRemorqueur hasHabillage(Boolean hasHabillage) {
			this.hasHabillage = hasHabillage;
			return this;
		}

		public void setHasHabillage(Boolean hasHabillage) {
			this.hasHabillage = hasHabillage;
		}

	public Integer getNombreCamion() {
		return nombreCamion;
	}

	public RefRemorqueur nombreCamion(Integer nombreCamion) {
		this.nombreCamion = nombreCamion;
		return this;
	}

	public void setNombreCamion(Integer nombreCamion) {
		this.nombreCamion = nombreCamion;
	}

	public PersonneMorale getSociete() {
		return societe;
	}

	public RefRemorqueur societe(PersonneMorale personneMorale) {
		this.societe = personneMorale;
		return this;
	}

	public List<VisAVis> getVisAViss() {
		return visAViss;
	}

	public void setVisAViss(List<VisAVis> visAViss) {
		this.visAViss = visAViss;
	}

	public void setSociete(PersonneMorale personneMorale) {
		this.societe = personneMorale;
	}
	public Boolean getIsBloque() {
		return isBloque;
	}
   public RefRemorqueur isBloque(Boolean isBloque) {
		this.isBloque = isBloque;
		return this;
	}
	public void setIsBloque(Boolean isBloque) {
		this.isBloque = isBloque;
	}
	public Boolean getIsFree() {
		return isFree;
	}
	public void setIsFree(Boolean isFree) {
		this.isFree = isFree;
	}

	public Boolean getIsConventionne() {
		return isConventionne;
	}
	 public RefRemorqueur isConventionne(Boolean isConventionne) {
			this.isConventionne = isConventionne;
			return this;
		}
	public void setIsConventionne(Boolean isConventionne) {
		this.isConventionne = isConventionne;
	}	

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		RefRemorqueur refRemorqueur = (RefRemorqueur) o;
		if (refRemorqueur.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), refRemorqueur.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "RefRemorqueur [id=" + id +  ", isBloque=" + isBloque + ", nombreCamion=" + nombreCamion + ", isDelete="
				+ isDelete +", hasHabillage=" + hasHabillage + ", telephone=" + telephone+"]";
	}

}
