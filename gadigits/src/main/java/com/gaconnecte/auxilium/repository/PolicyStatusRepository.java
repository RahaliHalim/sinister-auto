package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.PolicyStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PolicyStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PolicyStatusRepository extends JpaRepository<PolicyStatus,Long> {
    
}
