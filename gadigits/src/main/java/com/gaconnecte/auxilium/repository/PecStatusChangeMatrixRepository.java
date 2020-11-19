package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.PecStatusChangeMatrix;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PecStatusChangeMatrix entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PecStatusChangeMatrixRepository extends JpaRepository<PecStatusChangeMatrix,Long> {
    
}
