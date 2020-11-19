/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.dto;

import java.util.Objects;

/**
 *
 * @author hannibaal
 */
public class ViewReparatorDTO {

    private Long id;

    private String raisonSociale;

    private String fullName;

    private String phone;
    
    private String email;

    private String governorateLabel;

    private String delegationLabel;
    
    private Boolean activated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
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
        final ViewReparatorDTO other = (ViewReparatorDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ViewReparatorDTO{" + "id=" + id + ", raisonSociale=" + raisonSociale + ", fullName=" + fullName + ", phone=" + phone + ", email=" + email + ", governorateLabel=" + governorateLabel + ", delegationLabel=" + delegationLabel + ", activated=" + activated + '}';
    }

}
