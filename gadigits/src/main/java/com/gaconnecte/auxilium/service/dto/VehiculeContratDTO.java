package com.gaconnecte.auxilium.service.dto;

import java.time.LocalDate;

public class VehiculeContratDTO {

    private Long id;
    private String immatriculation;
    private Long assureId;
    private Long contratId;
    private String assurePrenom;
    private String assureRaison;
    private String assureFullName;
    private String numeroContrat;
    private LocalDate debutValidite;
    private LocalDate finValidite;
    private String nomCompagnie;
    private String nomAgence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public Long getAssureId() {
        return assureId;
    }

    public void setAssureId(Long assureId) {
        this.assureId = assureId;
    }

    public Long getContratId() {
        return contratId;
    }

    public void setContratId(Long contratId) {
        this.contratId = contratId;
    }

    public String getAssurePrenom() {
        return assurePrenom;
    }

    public void setAssurePrenom(String assurePrenom) {
        this.assurePrenom = assurePrenom;
    }

    public String getAssureRaison() {
        return assureRaison;
    }

    public void setAssureRaison(String assureRaison) {
        this.assureRaison = assureRaison;
    }

    public String getAssureFullName() {
        return assureFullName;
    }

    public void setAssureFullName(String assureFullName) {
        this.assureFullName = assureFullName;
    }

    public String getNumeroContrat() {
        return numeroContrat;
    }

    public void setNumeroContrat(String numeroContrat) {
        this.numeroContrat = numeroContrat;
    }

    public LocalDate getDebutValidite() {
        return debutValidite;
    }

    public void setDebutValidite(LocalDate debutValidite) {
        this.debutValidite = debutValidite;
    }

    public LocalDate getFinValidite() {
        return finValidite;
    }

    public void setFinValidite(LocalDate finValidite) {
        this.finValidite = finValidite;
    }

    public String getNomCompagnie() {
        return nomCompagnie;
    }

    public void setNomCompagnie(String nomCompagnie) {
        this.nomCompagnie = nomCompagnie;
    }

    public String getNomAgence() {
        return nomAgence;
    }

    public void setNomAgence(String nomAgence) {
        this.nomAgence = nomAgence;
    }

}
