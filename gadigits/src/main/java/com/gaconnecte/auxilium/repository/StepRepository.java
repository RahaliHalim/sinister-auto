package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Step;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Step entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StepRepository extends JpaRepository<Step,Long> {
    
}
