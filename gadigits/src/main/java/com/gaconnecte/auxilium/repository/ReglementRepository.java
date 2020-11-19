package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefModeReglement;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Reglement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReglementRepository extends JpaRepository<RefModeReglement,Long> {
    
}
