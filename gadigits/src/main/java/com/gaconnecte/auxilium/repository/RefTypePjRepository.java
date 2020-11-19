package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefTypePj;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RefTypePj entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefTypePjRepository extends JpaRepository<RefTypePj,Long> {
    
}
