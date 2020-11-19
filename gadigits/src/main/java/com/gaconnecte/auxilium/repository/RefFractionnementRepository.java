package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefFractionnement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RefFractionnement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefFractionnementRepository extends JpaRepository<RefFractionnement,Long> {
    
}
