package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.PolicyType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PolicyType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PolicyTypeRepository extends JpaRepository<PolicyType,Long> {
    
}
