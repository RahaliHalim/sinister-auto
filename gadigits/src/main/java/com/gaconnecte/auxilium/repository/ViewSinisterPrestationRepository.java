/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.view.ViewSinisterPrestation;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ViewSinisterPrestation entity.
 * @author hannibaal
 */
@Repository
public interface ViewSinisterPrestationRepository extends JpaRepository<ViewSinisterPrestation, Long> {
    
    Set<ViewSinisterPrestation> findAllByStatusId(Long statusId);
    
    
    @Query("SELECT count(prestation) FROM ViewSinisterPrestation prestation WHERE prestation.statusId =:status and ( lower(prestation.reference) like %:filter% OR  lower(prestation.registrationNumber)  like %:filter%"
            + " OR  lower(prestation.fullName) like %:filter% OR  lower(prestation.serviceType)  like %:filter% OR  lower(prestation.incidentLocationLabel)  like %:filter% "
            + " OR  lower(prestation.destinationLocationLabel)  like %:filter% OR  lower(prestation.affectedTugLabel)  like %:filter%)"
            + " OR  lower(prestation.charge)  like %:filter%")
    Long countAllWithFilter(@Param("filter") String filter, @Param("status") Long status);
    
    @Query("SELECT prestation FROM ViewSinisterPrestation prestation WHERE  prestation.statusId =:status and (lower(prestation.reference) like %:filter% OR lower(prestation.registrationNumber)  like %:filter%"
            + " OR  lower(prestation.fullName) like %:filter% OR  lower(prestation.serviceType)  like %:filter% OR lower(prestation.incidentLocationLabel)  like %:filter% OR  lower(prestation.destinationLocationLabel)  like %:filter% OR  lower(prestation.affectedTugLabel)  like %:filter%"
            + " OR  lower(prestation.charge)  like %:filter%)")
    Page<ViewSinisterPrestation> findAllWithFilter(@Param("filter") String filter, Pageable pageable, @Param("status") Long status);
    
    @Query("SELECT count(prestation) FROM ViewSinisterPrestation prestation WHERE prestation.statusId =:status")
    Long countClosed( @Param("status") Long status);


    @Query("SELECT prestation FROM ViewSinisterPrestation prestation WHERE prestation.statusId =:status")
    Page<ViewSinisterPrestation> findAllPrest(Pageable pageable, @Param("status") Long status);
    
    
    /*@Query("SELECT count(prestation) FROM ViewSinisterPrestation prestation WHERE prestation.serviceTypeId = 12 and prestation.statusId =:status and ( lower(prestation.reference) like %:filter% OR  lower(prestation.registrationNumber)  like %:filter%"
            + " OR  lower(prestation.fullName) like %:filter% OR  lower(prestation.loueurLabel)  like %:filter% OR  lower(prestation.incidentGovernorateLabel)  like %:filter% "
            + " OR  lower(prestation.deliveryGovernorateLabel)  like %:filter%"
            + " OR  lower(prestation.charge)  like %:filter% )")
    Long countAllVrWithFilter(@Param("filter") String filter, @Param("status") Long status);
    
    @Query("SELECT count(prestation) FROM ViewSinisterPrestation prestation WHERE prestation.serviceTypeId = 12 and prestation.statusId =:status")
    Long countVr( @Param("status") Long status);

    @Query("SELECT prestation FROM ViewSinisterPrestation prestation WHERE prestation.serviceTypeId = 12 and prestation.statusId =:status and ( lower(prestation.reference) like %:filter% OR  lower(prestation.registrationNumber)  like %:filter%"
            + " OR  lower(prestation.fullName) like %:filter% OR  lower(prestation.loueurLabel)  like %:filter% OR  lower(prestation.incidentGovernorateLabel)  like %:filter% "
            + " OR  lower(prestation.deliveryGovernorateLabel)  like %:filter%"
            + " OR  lower(prestation.charge)  like %:filter% )")
    Page<ViewSinisterPrestation> findAllVrWithFilter(@Param("filter") String filter, Pageable pageable, @Param("status") Long status);
    
    @Query("SELECT prestation FROM ViewSinisterPrestation prestation WHERE prestation.serviceTypeId = 12 and prestation.statusId =:status")
    Page<ViewSinisterPrestation> findAllVrPrest(Pageable pageable, @Param("status") Long status);*/


}