package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.PieceJointe;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Spring Data JPA repository for the PieceJointe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PieceJointeRepository extends JpaRepository<PieceJointe,Long> {

    @Query("select distinct piece_jointe from PieceJointe piece_jointe where piece_jointe.prestation.id =:prestationId and prestation.isDelete= false")
    Page<PieceJointe> findPieceJointesByPrestation(Pageable pageable,@Param("prestationId") Long prestationId);

   @Query("select distinct piece_jointe from PieceJointe piece_jointe where piece_jointe.devis.id =:devisId")
    Page<PieceJointe> findPieceJointesByDevis(Pageable pageable,@Param("devisId") Long devisId);
    
}
