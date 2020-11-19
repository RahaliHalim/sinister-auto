package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefModeGestion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;



/**
 * Spring Data JPA repository for the RefModeGestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefModeGestionRepository extends JpaRepository<RefModeGestion,Long> {

}
