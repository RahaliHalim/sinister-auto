package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.AmendmentType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AmendmentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmendmentTypeRepository extends JpaRepository<AmendmentType,Long> {
    
}
