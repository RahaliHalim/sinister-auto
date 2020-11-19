package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.DesignationFractionnement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DesignationFractionnement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesignationFractionnementRepository extends JpaRepository<DesignationFractionnement,Long> {
    
}
