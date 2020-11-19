
package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.view.ViewVehiclePieces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface ViewVehiclePiecesRepository extends JpaRepository<ViewVehiclePieces, Long> {
    
    @Query(value="select * from view_vehicle_piece where type_id = :typeId ORDER BY label ASC LIMIT 1000", nativeQuery=true)
	List<ViewVehiclePieces> findVehiclePiecesByType(@Param("typeId") Long typeId);
    
    @Query("select distinct piece from ViewVehiclePieces piece where piece.reference = :reference and piece.type = :typeId ORDER BY piece.reference ASC")
    ViewVehiclePieces getVehiclePiecesByTypeAndReference(@Param("reference") String reference, @Param("typeId") Long typeId);
    
    @Query("select distinct piece from ViewVehiclePieces piece where piece.code = :code and piece.type = :typeId ORDER BY piece.code ASC")
	ViewVehiclePieces getVehiclePiecesByTypeAndCode(@Param("code") String code, @Param("typeId") Long typeId);
    
    @Query(value="select * from view_vehicle_piece  where reference LIKE :reference% and type_id = :typeId limit 10", nativeQuery=true)
	List<ViewVehiclePieces> getVehiclePiecesByTypeAndTapedReference(@Param("reference") String reference, @Param("typeId") Long typeId);
    
    @Query(value="select * from view_vehicle_piece  where label LIKE :designation% and type_id = :typeId limit 10", nativeQuery=true)
	List<ViewVehiclePieces> getVehiclePiecesByTypeAndTapedDesignation(@Param("designation") String designation, @Param("typeId") Long typeId);
    
    @Query("select distinct piece from ViewVehiclePieces piece where piece.label = :designation and piece.type = :typeId ORDER BY piece.label ASC")
	ViewVehiclePieces getVehiclePiecesByTypeAndDesignation(@Param("designation") String designation, @Param("typeId") Long typeId);
    
    @Query("select piece from ViewVehiclePieces piece where piece.label = :designation and piece.type = :typeId ORDER BY piece.label ASC")
	List<ViewVehiclePieces> getVehiclePiecesByTypeAndDesignationAutoComplete(@Param("designation") String designation, @Param("typeId") Long typeId);
    
    @Query("select piece from ViewVehiclePieces piece where piece.reference = :reference and piece.type = :typeId ORDER BY piece.reference ASC")
    List<ViewVehiclePieces> getVehiclePiecesByTypeAndReferenceRef(@Param("reference") String reference, @Param("typeId") Long typeId);
}