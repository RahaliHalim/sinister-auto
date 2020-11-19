package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Facture;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Facture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactureRepository extends JpaRepository<Facture,Long> {
    
}
