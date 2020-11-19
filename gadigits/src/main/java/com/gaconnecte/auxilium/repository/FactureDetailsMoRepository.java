package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.FactureDetailsMo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FactureDetailsMo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactureDetailsMoRepository extends JpaRepository<FactureDetailsMo,Long> {
    
}
