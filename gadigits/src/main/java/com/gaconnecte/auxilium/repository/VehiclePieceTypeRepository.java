package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.VehiclePieceType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VehiclePieceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehiclePieceTypeRepository extends JpaRepository<VehiclePieceType,Long> {
    
}
