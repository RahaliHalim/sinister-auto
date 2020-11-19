package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefPositionGa;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RefPositionGa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefPositionGaRepository extends JpaRepository<RefPositionGa,Long> {
    
}
