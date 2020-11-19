package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.AvisExpertPiece;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AvisExpertPiece entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvisExpertPieceRepository extends JpaRepository<AvisExpertPiece,Long> {
    
}
