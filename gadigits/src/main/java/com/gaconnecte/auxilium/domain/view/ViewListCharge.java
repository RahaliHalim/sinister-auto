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
@Table(name = "view_list_charge")
public class ViewListCharge implements Serializable{
    private static final long serialVersionUID = 1L;
    
    
    @Id
    private Long id;
    
    @Column(name = "nom")
    private String nomCharge;
    
    @Column(name = "prenom")
    private String prenomCharge;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomCharge() {
		return nomCharge;
	}

	public void setNomCharge(String nomCharge) {
		this.nomCharge = nomCharge;
	}

	public String getPrenomCharge() {
		return prenomCharge;
	}

	public void setPrenomCharge(String prenomCharge) {
		this.prenomCharge = prenomCharge;
	}

	@Override
	public String toString() {
		return "ViewListCharge [id=" + id + ", nomCharge=" + nomCharge + ", prenomCharge=" + prenomCharge + "]";
	}
    
    
    
}
