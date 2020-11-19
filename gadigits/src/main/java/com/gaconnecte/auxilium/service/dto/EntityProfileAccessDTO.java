/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.dto;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author hannibaal
 */
public class EntityProfileAccessDTO {
    
    private Long id;

    private String label;

    private Set<ProfileAccessDTO> profileAccesss = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<ProfileAccessDTO> getProfileAccesss() {
        if( profileAccesss == null ) profileAccesss = new HashSet<>();
        return profileAccesss;
    }

    public void setProfileAccesss(Set<ProfileAccessDTO> profileAccesss) {
        this.profileAccesss = profileAccesss;
    }

    public void addProfileAccesss(ProfileAccessDTO profileAccess) {
        if(this.profileAccesss == null) {
            this.profileAccesss = new HashSet<>();
        }
        if(this.profileAccesss.contains(profileAccess)) {
            for (Iterator<ProfileAccessDTO> it = this.profileAccesss.iterator(); it.hasNext(); ) {
                ProfileAccessDTO p = it.next();
                if(p.equals(profileAccess)) {
                    p.addProfileId(profileAccess.getProfileId());
                    break;
                }
            }
        } else {
            profileAccess.addProfileId(profileAccess.getProfileId());
            this.profileAccesss.add(profileAccess);
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final EntityProfileAccessDTO other = (EntityProfileAccessDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityProfileAccessDTO{" + "id=" + id + ", label=" + label + ", profileAccesss=" + profileAccesss + '}';
    }
    
    
}
