package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.PolicyNature;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PolicyNature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PolicyNatureRepository extends JpaRepository<PolicyNature,Long> {
    
}
