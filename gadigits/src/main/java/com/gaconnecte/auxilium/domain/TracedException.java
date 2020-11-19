package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A TracedException.
 */
@Entity
@Table(name = "traced_exception")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TracedException implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "dated")
    private ZonedDateTime dated;

    @Column(name = "service")
    private String service;

    @Column(name = "message")
    private String message;

    @Column(name = "stack_trace")
    private String stackTrace;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TracedException name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getDated() {
        return dated;
    }

    public TracedException dated(ZonedDateTime dated) {
        this.dated = dated;
        return this;
    }

    public void setDated(ZonedDateTime dated) {
        this.dated = dated;
    }

    public String getService() {
        return service;
    }

    public TracedException service(String service) {
        this.service = service;
        return this;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMessage() {
        return message;
    }

    public TracedException message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public TracedException stackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
        return this;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TracedException tracedException = (TracedException) o;
        if (tracedException.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tracedException.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TracedException{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dated='" + getDated() + "'" +
            ", service='" + getService() + "'" +
            ", message='" + getMessage() + "'" +
            ", stackTrace='" + getStackTrace() + "'" +
            "}";
    }
}
