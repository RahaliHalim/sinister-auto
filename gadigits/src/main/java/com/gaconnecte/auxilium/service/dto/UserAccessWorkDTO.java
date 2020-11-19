/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.dto;

import java.util.List;

/**
 *
 * @author hannibaal
 */
public class UserAccessWorkDTO {
    private Long id;// id of profile to add or remove
    private boolean addFlag;
    private List<UserProfileDTO> profiles;
    private List<EntityProfileAccessDTO> entityProfileAccesss;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAddFlag() {
        return addFlag;
    }

    public void setAddFlag(boolean addFlag) {
        this.addFlag = addFlag;
    }

    public List<UserProfileDTO> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<UserProfileDTO> profiles) {
        this.profiles = profiles;
    }

    public List<EntityProfileAccessDTO> getEntityProfileAccesss() {
        return entityProfileAccesss;
    }

    public void setEntityProfileAccesss(List<EntityProfileAccessDTO> entityProfileAccesss) {
        this.entityProfileAccesss = entityProfileAccesss;
    }

    @Override
    public String toString() {
        return "UserAccessWorkDTO{" + "id=" + id + ", profiles=" + profiles + ", entityProfileAccesss=" + entityProfileAccesss + '}';
    }

    
    
}
