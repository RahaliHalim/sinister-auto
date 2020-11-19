package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the UserCellule entity.
 */
public class UserCelluleDTO implements Serializable {

    private Long id;

    private Long userId;

    private String userLogin;

    private Long celluleId;

    private String celluleName;

    private boolean userActivated;

    private String userLangKey;

    private String userCreatedBy;

    private Instant userCreatedDate;

    private String userLastModifiedBy;

    private Instant userLastModifiedDate;

    private Set<AuthorityDTO> authorities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCelluleId() {
        return celluleId;
    }

    public void setCelluleId(Long celluleId) {
        this.celluleId = celluleId;
    }

    public String getCelluleName() {
        return celluleName;
    }

    public void setCelluleName(String celluleName) {
        this.celluleName = celluleName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public boolean getUserActivated() {
        return userActivated;
    }

    public void setUserActivated(boolean userActivated) {
        this.userActivated = userActivated;
    }

    public String getUserLangKey() {
        return userLangKey;
    }

    public void setUserLangKey(String userLangKey) {
        this.userLangKey = userLangKey;
    }

    public String getUserCreatedBy() {
        return userCreatedBy;
    }

    public void setUserCreatedBy(String userCreatedBy) {
        this.userCreatedBy = userCreatedBy;
    }

    public Instant getUserCreatedDate() {
        return userCreatedDate;
    }

    public void setUserCreatedDate(Instant userCreatedDate) {
        this.userCreatedDate = userCreatedDate;
    }

    public String getUserLastModifiedBy() {
        return userLastModifiedBy;
    }

    public void setUserLastModifiedBy(String userLastModifiedBy) {
        this.userLastModifiedBy = userLastModifiedBy;
    }

    public Instant getUserLastModifiedDate() {
        return userLastModifiedDate;
    }

    public void setUserLastModifiedDate(Instant userLastModifiedDate) {
        this.userLastModifiedDate = userLastModifiedDate;
    }


     public Set<AuthorityDTO> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityDTO> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserCelluleDTO userCelluleDTO = (UserCelluleDTO) o;
        if(userCelluleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userCelluleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserCelluleDTO{" +
            "id=" + getId() +
            "}";
    }
}