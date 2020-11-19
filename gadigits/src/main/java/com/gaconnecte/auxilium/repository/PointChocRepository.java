package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.PointChoc;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PointChoc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointChocRepository extends JpaRepository<PointChoc,Long> {
    
}
