package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefTugTruck;
import com.gaconnecte.auxilium.domain.RefTypeService;


import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Spring Data JPA repository for the Contact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefTugTruckRepository extends JpaRepository<RefTugTruck, Long> {

     @Query("select distinct camion from RefTugTruck camion where camion.refTug.id =:refRemorqueurId")
     Page<RefTugTruck> findCamionsByRefRemorqueur(Pageable pageable,@Param("refRemorqueurId") Long refRemorqueurId);
     
     @Query("select truck from RefTugTruck truck where :serviceType member truck.serviceTypes")
     Set<RefTugTruck> findTrucksByServiceType(@Param("serviceType") RefTypeService serviceType);
     
     @Query("select truck from RefTugTruck truck where truck.refTug.societe.ville.governorate.id =:governorateId and :serviceType member truck.serviceTypes")
     Set<RefTugTruck> findTrucksByServiceTypeAndByGovernorate(@Param("serviceType") RefTypeService serviceType, @Param("governorateId") Long governorateId);
     
     @Query("select truck from RefTugTruck truck where truck.immatriculation = :immatriculation")
     RefTugTruck findTrucksByImmatriculation(@Param("immatriculation") String immatriculation);
    
}