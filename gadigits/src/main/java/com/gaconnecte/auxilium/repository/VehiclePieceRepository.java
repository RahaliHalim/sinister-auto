package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.VehiclePiece;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Set;


/**
 * Spring Data JPA repository for the VehiclePiece entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehiclePieceRepository extends JpaRepository<VehiclePiece,Long> {
    
    @Query("select distinct piece from  VehiclePiece piece where piece.type.id = :typeId")
    List<VehiclePiece> findVehiclePiecesByType(@Param("typeId") Long typeId);
    
    @Query("select piece from  VehiclePiece piece where piece.code = :reference and piece.type.id = :typeId ")
    VehiclePiece findPiece(@Param("reference") String reference, @Param("typeId") Long typeId);

    @Query("select piece from VehiclePiece piece where piece.label = :designation and piece.type.id = :typeId ")
    VehiclePiece findVehiclePieceByDesignationAndType(@Param("designation") String designation, @Param("typeId") Long typeId);

    @Query("select piece from VehiclePiece piece where piece.code = :reference and piece.type.id = :typeId ")
    VehiclePiece findVehiclePieceByReferenceAndType(@Param("reference") String reference, @Param("typeId") Long typeId);
    
    @Query("select piece from VehiclePiece piece where piece.reference = :reference and piece.type.id = :typeId ")
    List<VehiclePiece> findVehiclePieceByReferenceGTAndType(@Param("reference") String reference, @Param("typeId") Long typeId);
}
