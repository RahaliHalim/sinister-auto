package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.PersonneMorale;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PersonneMorale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonneMoraleRepository extends JpaRepository<PersonneMorale,Long> {
    
}
