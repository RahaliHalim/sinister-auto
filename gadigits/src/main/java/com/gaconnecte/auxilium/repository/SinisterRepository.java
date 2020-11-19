package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.SinisterPec;
import com.gaconnecte.auxilium.domain.ViewSinisterPec;
import com.gaconnecte.auxilium.domain.app.Sinister;
import com.gaconnecte.auxilium.domain.view.ViewSinisterPrestation;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.Set;

/**
 * Spring Data JPA repository for the Sinister entity.
 */
@Repository
public interface SinisterRepository extends JpaRepository<Sinister, Long> {

    @Query("SELECT sinister from Sinister sinister where sinister.deleted = false")
    Set<Sinister> findAllSinister();

    @Query("SELECT sinister FROM Sinister sinister where sinister.vehicle.id = :vehicleId")
    Sinister findByVehicle(@Param("vehicleId") Long vehicleId);
    
    @Query("SELECT sinister FROM Sinister sinister where sinister.vehicle.id = :vehicleId and incidentDate = :incidentDate")
    List<Sinister> findByVehicleAndIncidentDate(@Param("vehicleId") Long vehicleId, @Param("incidentDate") LocalDate incidentDate);

    @Query("SELECT sinister FROM Sinister sinister where sinister.vehicle.id = :vehiculeId and incidentDate = :incidentDate and sinister.status.id = :statusId"  )
    List<Sinister> findByVehicleIdAndIncidentDateAndStatus(@Param("vehiculeId") Long vehiculeId, @Param("incidentDate") LocalDate incidentDate, @Param("statusId") Long statusId);

    @Query("SELECT sinister from Sinister sinister where sinister.vehicle.immatriculationVehicule = :vehicleRegistration")
    Set<Sinister> findByVehicleRegistration(@Param("vehicleRegistration") String vehicleRegistration);

    @Query("SELECT sinister FROM Sinister sinister where sinister.contract.id = :contratId")
    List<Sinister> findByContratId(@Param("contratId") Long contratId);
    
    @Query("SELECT sp from Sinister sp WHERE sp.creationDate <= :endDate AND sp.creationDate >= :startDate order by sp.partner")
    Set<Sinister> findAllSinisterReport1ByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT sp from Sinister sp WHERE sp.partner.id = :partnerId AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate order by sp.partner")
    Set<Sinister> findAllSinisterReport1ByPartnerAndDates(@Param("partnerId") Long partnerId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    @Query("SELECT sinisterPec from SinisterPec sinisterPec where sinisterPec.step = 1")
    List<SinisterPec> getAllExternalNewDemands();
    
    @Query("SELECT viewSinisterPrestation from ViewSinisterPrestation viewSinisterPrestation where viewSinisterPrestation.vehiculeId = :vehiculeId")
    Set<ViewSinisterPrestation> findViewPrestationsByVehicleRegistration(@Param("vehiculeId") Long vehiculeId);
    
    @Query("SELECT viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.vehiculeId = :vehiculeId")
    Set<ViewSinisterPec> findViewPecByVehicleRegistration(@Param("vehiculeId") Long vehiculeId);
}
