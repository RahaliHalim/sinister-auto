package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.StampDuty;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.math.BigDecimal;


/**
 * Spring Data JPA repository for the StampDuty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StampDutyRepository extends JpaRepository<StampDuty,Long> {
    
    @Query("SELECT stampDuty from StampDuty stampDuty where stampDuty.active = true")
    StampDuty findActiveStampDuty();
}
