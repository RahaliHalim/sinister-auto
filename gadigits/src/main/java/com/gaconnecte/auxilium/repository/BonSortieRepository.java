package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.BonSortie;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BonSortie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BonSortieRepository extends JpaRepository<BonSortie,Long> {
    @Query (" select COALESCE( max( bs.numero ) +1 ,1)  from BonSortie bs ")
    Long findNewNumBonSortie();
   
}

