package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.TarifLine;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the tarif line entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifLineRepository extends JpaRepository<TarifLine,Long> {
    
}
