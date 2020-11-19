package com.gaconnecte.auxilium.repository.referential;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.referential.RefPack;
import com.gaconnecte.auxilium.domain.referential.RefVehicleUsage;

/**
 * Spring Data JPA repository for the RefVehicleUsage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefVehicleUsageRepository extends JpaRepository<RefVehicleUsage, Long> {
        
}