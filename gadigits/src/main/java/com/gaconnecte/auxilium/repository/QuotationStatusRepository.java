package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.QuotationStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SinisterTypeSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuotationStatusRepository extends JpaRepository<QuotationStatus, Long> {

}
