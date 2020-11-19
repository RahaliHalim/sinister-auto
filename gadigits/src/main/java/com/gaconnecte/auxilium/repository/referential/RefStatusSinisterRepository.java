package com.gaconnecte.auxilium.repository.referential;

import com.gaconnecte.auxilium.domain.referential.RefStatusSinister;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the RefStatusSinister entity.
 */
@Repository
public interface RefStatusSinisterRepository extends JpaRepository<RefStatusSinister, Long> {

    
}
