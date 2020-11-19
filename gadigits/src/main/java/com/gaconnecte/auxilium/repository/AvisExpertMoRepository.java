package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.AvisExpertMo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AvisExpertMo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvisExpertMoRepository extends JpaRepository<AvisExpertMo,Long> {
    
}
