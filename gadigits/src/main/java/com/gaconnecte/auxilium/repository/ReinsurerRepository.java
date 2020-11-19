package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Reinsurer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Reinsurer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReinsurerRepository extends JpaRepository<Reinsurer,Long> {
    
}
