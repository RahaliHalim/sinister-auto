package com.gaconnecte.auxilium.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "group_details")
public class GroupDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "mode_id")
    private RefModeGestion refModeGestion;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Partner client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public RefModeGestion getRefModeGestion() {
        return refModeGestion;
    }

    public void setRefModeGestion(RefModeGestion refModeGestion) {
        this.refModeGestion = refModeGestion;
    }

    public Partner getClient() {
        return client;
    }

    public void setClient(Partner client) {
        this.client = client;
    }

}
