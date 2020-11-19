package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.ValidationDevis;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ValidationDevis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValidationDevisRepository extends JpaRepository<ValidationDevis,Long> {
    
}
