package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefEtatDossier;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RefEtatDossier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefEtatDossierRepository extends JpaRepository<RefEtatDossier,Long> {
    
}
