package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Assure;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import org.springframework.data.repository.query.Param;
import java.time.ZonedDateTime;

/**
 * Spring Data JPA repository for the Assure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssureRepository extends JpaRepository<Assure, Long> {
    @Query("SELECT count(*) from Assure assure where assure.dateCreation between :convertedDateD and :convertedDateF")
    Long countAssure(@Param("convertedDateD") ZonedDateTime convertedDateD,  @Param("convertedDateF") ZonedDateTime convertedDateF);
}
