package com.gaconnecte.auxilium.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.gaconnecte.auxilium.domain.enumeration.TypeObservation;

/**
 * A DTO for the Observation entity.
 */
public class ObservationDTO implements Serializable {

    private Long id;

    //@NotNull
    @Size(max = 2000)
    private String commentaire;

    private ZonedDateTime date;

    private TypeObservation type;

    private Long userId;

    private String userLogin;
    
    private String firstName;
    
    private String lastName;

    private Long sinisterPecId;
    
    private Long sinisterPrestationId;

    private Long devisId;

    private Long prestationId;

    public Long getPrestationId() {
		return prestationId;
	}

	public void setPrestationId(Long prestationId) {
		this.prestationId = prestationId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public TypeObservation getType() {
        return type;
    }

    public void setType(TypeObservation type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

   

    public Long getSinisterPecId() {
		return sinisterPecId;
	}

	public void setSinisterPecId(Long sinisterPecId) {
		this.sinisterPecId = sinisterPecId;
	}

	public Long getDevisId() {
        return devisId;
    }

    public void setDevisId(Long devisId) {
        this.devisId = devisId;
    }

    public Long getSinisterPrestationId() {
		return sinisterPrestationId;
	}

	public void setSinisterPrestationId(Long sinisterPrestationId) {
		this.sinisterPrestationId = sinisterPrestationId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObservationDTO observationDTO = (ObservationDTO) o;
        if(observationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), observationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
	public String toString() {
		return "ObservationDTO [id=" + id + ", commentaire=" + commentaire + ", date=" + date + ", type=" + type
				+ ", userId=" + userId + ", userLogin=" + userLogin + ", sinisterPecId=" + sinisterPecId + ", devisId="
				+ devisId + ", prestationId=" + prestationId + "]";
	}
}
