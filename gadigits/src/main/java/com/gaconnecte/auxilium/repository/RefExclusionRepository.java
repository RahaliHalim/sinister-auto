package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefExclusion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;



/**
 * Spring Data JPA repository for the RefModeGestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefExclusionRepository extends JpaRepository<RefExclusion,Long> {


}
