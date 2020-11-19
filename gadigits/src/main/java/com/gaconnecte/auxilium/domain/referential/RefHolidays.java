package com.gaconnecte.auxilium.domain.referential;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.gaconnecte.auxilium.domain.AbstractElementaryEntity;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;

/**
 * Holidays references.
 */
@Entity
@Table(name = "ref_holidays")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ref_holidays")
public class RefHolidays extends AbstractElementaryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
