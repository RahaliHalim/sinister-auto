package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.PolicyReceiptStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PolicyReceiptStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PolicyReceiptStatusRepository extends JpaRepository<PolicyReceiptStatus,Long> {
    
}
