package com.gaconnecte.auxilium.domain.prm;

import com.gaconnecte.auxilium.domain.SinisterPec;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.domain.referential.RefPack;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * cities references.
 */
@Entity
@Table(name = "prm_convention_amendment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prm_convention_amendment")
public class ConventionAmendment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "old_ref_pack_id")
    private Long oldRefPackId;

    @ManyToOne
    @JoinColumn(name = "convention_id")
    private Convention convention;

    @Column(name = "serial")
    private Integer serial;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @ManyToOne(optional = true)
    @JoinColumn(name = "creation_user_id")
    private User createUser;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne(optional = true)
    @JoinColumn(name = "update_user_id")
    private User updateUser;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @OneToOne(mappedBy = "amendment", cascade = CascadeType.ALL)
    @JoinColumn(name = "ref_pack_id")
    private RefPack refPack;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Convention getConvention() {
        return convention;
    }

    public void setConvention(Convention convention) {
        this.convention = convention;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public User getCreateUser() {
        return createUser;
    }

    public RefPack getRefPack() {
        return refPack;
    }

    public void setRefPack(RefPack refPack) {
        this.refPack = refPack;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public void setOldRefPackId(Long oldRefPackId) {
        this.oldRefPackId = oldRefPackId;
    }

    public Long getOldRefPackId() {
        return oldRefPackId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final ConventionAmendment other = (ConventionAmendment) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ConventionAmendment [id=" + id + ", convention=" + convention + ", serial=" + serial + ", creationDate="
                + creationDate + ", createUser=" + createUser + ", updateDate=" + updateDate + ", updateUser="
                + updateUser + ", startDate=" + startDate + ", endDate=" + endDate + ", refPack=" + refPack
                + ", attachment=" + attachment + "]";
    }

}
