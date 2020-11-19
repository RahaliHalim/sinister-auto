package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.FacturePieces;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FacturePieces entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacturePiecesRepository extends JpaRepository<FacturePieces,Long> {
    
}
