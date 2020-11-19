package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefMateriel;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RefMateriel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefMaterielRepository extends JpaRepository<RefMateriel,Long> {
    
}
