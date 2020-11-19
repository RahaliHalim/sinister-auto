package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.app.SinisterPrestation;
import com.gaconnecte.auxilium.domain.referential.RefStatusSinister;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the SinisterPrestation entity.
 */
@Repository
public interface SinisterPrestationRepository extends JpaRepository<SinisterPrestation, Long> {

    @Query("SELECT sinisterPrestation from SinisterPrestation sinisterPrestation")
    Set<SinisterPrestation> findAllSinisterPrestation();
    
    Set<SinisterPrestation> findAllByStatus(RefStatusSinister status);

    @Query("SELECT sp from SinisterPrestation sp WHERE sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
    Set<SinisterPrestation> findAllSinisterPrestationReport1ByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT sp from SinisterPrestation sp WHERE sp.sinister.partner.id = :partnerId AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
    Set<SinisterPrestation> findAllSinisterPrestationReport1ByPartnerAndDates(@Param("partnerId") Long partnerId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT sp from SinisterPrestation sp WHERE sp.sinister.id =:id ")
   Set< SinisterPrestation> findSinisterPrestationBySinisterId(@Param("id") Long id);

    @Query("SELECT sp from SinisterPrestation sp WHERE sp.status.id = 1 and sp.affectedTruckId = :id ")
    Set<SinisterPrestation> findSinisterPrestationActiveTruck(@Param("id") Long id);
    
    @Query("SELECT COUNT(*) from SinisterPrestation sinisterPrestation where sinisterPrestation.sinister.vehicle.id = :idVehicule and sinisterPrestation.status.id = 3")
    Long findCountSinisterPrestationForVehicule(@Param("idVehicule") Long idVehicule);
    
    @Query("SELECT DISTINCT(sp.affectedTugId) FROM SinisterPrestation sp WHERE (sp.status = 2 OR sp.status = 3) AND sp.affectedTugId IS NOT NULL")
    Set<Long> findAllTugWithClosedOrCanceledPrestation();
    
    @Query("SELECT sp FROM SinisterPrestation sp WHERE sp.affectedTugId =:affectedTugId")
    Set<SinisterPrestation> findAllSinisterPrestationClosedOrCanceledGroupByTug(@Param("affectedTugId") Long affectedTugId);

    @Query("SELECT sp FROM SinisterPrestation sp WHERE sp.status = 3 AND sp.affectedTugId =:affectedTugId")
    Set<SinisterPrestation> findAllSinisterPrestationClosedGroupByTug(@Param("affectedTugId") Long affectedTugId);

    @Query("SELECT sp FROM SinisterPrestation sp WHERE sp.status = 3 AND sp.creationDate BETWEEN :startDate AND :endDate AND sp.affectedTugId =:affectedTugId")
    Set<SinisterPrestation> findAllSinisterPrestationClosedGroupByTug(@Param("affectedTugId") Long affectedTugId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT sp FROM SinisterPrestation sp WHERE sp.status = 3 and sp.sinister.partner.id =:partnerId")
    Set<SinisterPrestation> findAllSinisterPrestationClosedOrCanceledGroupByPartner(@Param("partnerId") Long partnerId);

    @Query("SELECT sp FROM SinisterPrestation sp WHERE sp.status = 3 AND sp.creationDate BETWEEN :startDate AND :endDate and sp.sinister.partner.id =:partnerId")
    Set<SinisterPrestation> findAllSinisterPrestationClosedOrCanceledGroupByPartner(@Param("partnerId") Long partnerId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
  
    @Query("SELECT COUNT(*) from SinisterPrestation sinisterPrestation where sinisterPrestation.serviceType.id = 12")
    Long countAllVr();
}
