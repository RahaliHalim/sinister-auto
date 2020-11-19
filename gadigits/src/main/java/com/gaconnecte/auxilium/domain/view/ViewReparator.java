package com.gaconnecte.auxilium.domain.view;


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A User.
 */
@Entity
@Table(name = "view_reparator")
public class ViewReparator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "raison_sociale")
    private String raisonSociale;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone")
    private String phone;
    
    @Column(name = "email")
    private String email;

    @Column(name = "governorate_label")
    private String governorateLabel;

    @Column(name = "delegation_label")
    private String delegationLabel;
    
    @Column(name = "activated")
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
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final ViewReparator other = (ViewReparator) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ViewReparator{" + "id=" + id + ", raisonSociale=" + raisonSociale + ", fullName=" + fullName + ", phone=" + phone + ", email=" + email + ", governorateLabel=" + governorateLabel + ", delegationLabel=" + delegationLabel + ", activated=" + activated + '}';
    }


}
