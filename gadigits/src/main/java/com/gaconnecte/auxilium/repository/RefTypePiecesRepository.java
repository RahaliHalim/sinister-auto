package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefTypePieces;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RefTypePieces entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefTypePiecesRepository extends JpaRepository<RefTypePieces,Long> {
    
}
