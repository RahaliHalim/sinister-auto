package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Lien entity.
 */
public class LienDTO implements Serializable {

    private Long id;

    private String name;

    private String url;

    private Set<CelluleDTO> cellules = new HashSet<>();

    private Set<AuthorityDTO> authorities = new HashSet<>();

    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<CelluleDTO> getCellules() {
        return cellules;
    }

    public void setCellules(Set<CelluleDTO> cellules) {
        this.cellules = cellules;
    }

    public Set<AuthorityDTO> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityDTO> authorities) {
        this.authorities = authorities;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LienDTO lienDTO = (LienDTO) o;
        if(lienDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lienDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LienDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}