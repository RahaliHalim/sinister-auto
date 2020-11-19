package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.ServiceAssurance;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ServiceAssurance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceAssuranceRepository extends JpaRepository<ServiceAssurance,Long> {
    
}
