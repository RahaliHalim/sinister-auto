package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefTugTruck;
import com.gaconnecte.auxilium.domain.VehiculeLoueur;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the VehiculeLoueur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehiculeLoueurRepository extends JpaRepository<VehiculeLoueur,Long> {
	
	
	@Query("select vehicule from VehiculeLoueur vehicule where vehicule.immatriculation = :immatriculation")
	VehiculeLoueur findVehiculesByImmatriculation(@Param("immatriculation") String immatriculation);
	
	@Query("select distinct vehicule from VehiculeLoueur vehicule where vehicule.loueur.id =:loueurId")
    Page<VehiculeLoueur> findVehiculesByLoueur(Pageable pageable,@Param("loueurId") Long loueurId);
    
}
