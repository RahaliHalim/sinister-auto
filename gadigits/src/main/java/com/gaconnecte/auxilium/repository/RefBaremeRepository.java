package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefBareme;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.*;
import java.util.Set;

/**
 * Spring Data JPA repository for the RefBareme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefBaremeRepository extends JpaRepository<RefBareme,Long> {
    
    @Query("SELECT refBareme FROM RefBareme refBareme")
    Set<RefBareme> findAllRefBaremes();
}
