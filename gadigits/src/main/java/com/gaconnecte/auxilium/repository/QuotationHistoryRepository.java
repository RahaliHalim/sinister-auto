package com.gaconnecte.auxilium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.QuotationHistory;

/**
 * Spring Data JPA repository for the Assure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuotationHistoryRepository extends JpaRepository<QuotationHistory,Long> {

   
    
}