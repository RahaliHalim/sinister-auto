package com.gaconnecte.auxilium.domain;
import com.gaconnecte.auxilium.domain.app.Attachment;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RefBareme.
 */
@Entity
@Table(name = "ref_bareme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "refbareme")
public class RefBareme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Max(value = 99999999)
    @Column(name = "code", nullable = false)
    private Integer code;

    @NotNull
    @Max(value = 99999999)
    @Column(name = "responsabilite_x", nullable = false)
    private Integer responsabiliteX;

    @NotNull
    @Max(value = 99999999)
    @Column(name = "responsabilite_y", nullable = false)
    private Integer responsabiliteY;

    @NotNull
    @Size(max = 2000)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Size(max = 256)
    @Column(name = "title", length = 256, nullable = false)
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public RefBareme code(Integer code) {
        this.code = code;
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getResponsabiliteX() {
        return responsabiliteX;
    }

    public RefBareme responsabiliteX(Integer responsabiliteX) {
        this.responsabiliteX = responsabiliteX;
        return this;
    }

    public void setResponsabiliteX(Integer responsabiliteX) {
        this.responsabiliteX = responsabiliteX;
    }

    public Integer getResponsabiliteY() {
        return responsabiliteY;
    }

    public RefBareme responsabiliteY(Integer responsabiliteY) {
        this.responsabiliteY = responsabiliteY;
        return this;
    }

    public void setResponsabiliteY(Integer responsabiliteY) {
        this.responsabiliteY = responsabiliteY;
    }

    public String getDescription() {
        return description;
    }

    public RefBareme description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RefBareme refBareme = (RefBareme) o;
        if (refBareme.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refBareme.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefBareme{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", responsabiliteX='" + getResponsabiliteX() + "'" +
            ", responsabiliteY='" + getResponsabiliteY() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
