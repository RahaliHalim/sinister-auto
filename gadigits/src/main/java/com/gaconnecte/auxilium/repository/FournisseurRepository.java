package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Fournisseur;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Fournisseur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur,Long> {
    
}
