package com.gaconnecte.auxilium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.DetailsQuotation;

@SuppressWarnings("unused")
@Repository
public interface DetailsQuotationRepository extends JpaRepository<DetailsQuotation, Long> {

    @Query("SELECT detailsQuotation from DetailsQuotation detailsQuotation where detailsQuotation.sinisterPecId =:id")
    DetailsQuotation findOneBySinisterPecId(@Param("id") Long id);

}