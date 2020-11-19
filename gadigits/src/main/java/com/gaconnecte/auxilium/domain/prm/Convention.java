package com.gaconnecte.auxilium.domain.prm;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.AbstractElementaryEntity;
import com.gaconnecte.auxilium.domain.Partner;
import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.domain.referential.RefPack;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "prm_convention")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prm_convention")
public class Convention extends AbstractElementaryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "ref_client_id")
    private Partner client;

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

    @OneToMany(mappedBy = "convention", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RefPack> packs = new ArrayList<>();
    
    @OneToMany(mappedBy = "convention", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ConventionAmendment> conventionAmendments = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

    /**
     * Returns value of client
     *
     * @return
     */
    public Partner getClient() {
        return client;
    }

    /**
     * Sets new value of client
     *
     * @param
     */
    public void setClient(Partner client) {
        this.client = client;
    }

    /**
     * Returns value of creationDate
     *
     * @return
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Sets new value of creationDate
     *
     * @param
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    

    public List<ConventionAmendment> getConventionAmendments() {
		return conventionAmendments;
	}

	public void setConventionAmendments(List<ConventionAmendment> conventionAmendments) {
		this.conventionAmendments = conventionAmendments;
	}

	/**
     * Returns value of createUser
     *
     * @return
     */
    public User getCreateUser() {
        return createUser;
    }

    /**
     * Sets new value of createUser
     *
     * @param
     */
    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    /**
     * Returns value of updateDate
     *
     * @return
     */
    public LocalDate getUpdateDate() {
        return updateDate;
    }

    /**
     * Sets new value of updateDate
     *
     * @param
     */
    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * Returns value of updateUser
     *
     * @return
     */
    public User getUpdateUser() {
        return updateUser;
    }

    /**
     * Sets new value of updateUser
     *
     * @param
     */
    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * Returns value of startDate
     *
     * @return
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets new value of startDate
     *
     * @param
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns value of endDate
     *
     * @return
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets new value of endDate
     *
     * @param
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<RefPack> getPacks() {
        return packs;
    }

    public void setPacks(List<RefPack> packs) {
        this.packs = packs;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

}
