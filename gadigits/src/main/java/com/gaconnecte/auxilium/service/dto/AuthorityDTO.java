package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Assure entity.
 */
public class AuthorityDTO implements Serializable {

    private String name;

     private Boolean isInterne;

     private Boolean isActive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsInterne() {
        return isInterne;
    }

    public void setIsInterne(Boolean isInterne) {
        this.isInterne = isInterne;
    }

     @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuthorityDTO authorityDTO = (AuthorityDTO) o;
        if(authorityDTO.getName() == null || getName() == null) {
            return false;
        }
        return Objects.equals(getName(), authorityDTO.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {
        return "AuthorityDTO{" +
            "name=" + getName() +
            "}";
    }
}