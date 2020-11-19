package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Grille;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Grille entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrilleRepository extends JpaRepository<Grille,Long> {
    
}
