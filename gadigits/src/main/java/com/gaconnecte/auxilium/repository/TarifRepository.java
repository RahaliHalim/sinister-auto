package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Tarif;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Tarif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifRepository extends JpaRepository<Tarif,Long> {
    
}
