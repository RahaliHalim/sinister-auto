package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.VehiculeAssure;
import org.springframework.stereotype.Repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the VehiculeAssure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehiculeAssureRepository extends JpaRepository<VehiculeAssure,Long> {
	
	
    public VehiculeAssure findByImmatriculationVehicule(String immatriculationVehicule);
    

    @Query("select distinct vehiculeAssure from VehiculeAssure vehiculeAssure where vehiculeAssure.contrat.id =:contratId")
    Page<VehiculeAssure> findByContrat(Pageable pageable,@Param("contratId") Long contratId);

    
    @Query("SELECT vehiculeAssure.modele.brand.label from VehiculeAssure vehiculeAssure where vehiculeAssure.id =:vehiculeId")
	String findMarqueByVehicule(@Param("vehiculeId") Long vehiculeId);
    @Query("delete from VehiculeAssure vehiculeAssure where vehiculeAssure.contrat.id =:id")
	@Modifying
    void deleteByContrat(@Param("id") Long id);
    
    @Query("SELECT v FROM VehiculeAssure v where v.immatriculationVehicule = :immatriculationVehicule ")
    VehiculeAssure findByImmatriculation(@Param("immatriculationVehicule") String immatriculationVehicule);
    
    @Query("SELECT v FROM VehiculeAssure v where v.contrat.client.companyName = :company and v.immatriculationVehicule = :immatriculation ")
    VehiculeAssure findByCompagnyNameAndImmatriculation(@Param("company") String company, @Param("immatriculation") String immatriculation);
    
    @Query("SELECT v FROM VehiculeAssure v where v.contrat.client.id = :clientId and v.immatriculationVehicule = :immatriculation ")
    VehiculeAssure findByClientIdAndImmatriculation(@Param("clientId") Long clientId, @Param("immatriculation") String immatriculation);
    
    @Query("SELECT distinct v FROM VehiculeAssure v where v.immatriculationVehicule = :immatriculationVehicule ")
    Set<VehiculeAssure> findListByImmatriculation(@Param("immatriculationVehicule") String immatriculationVehicule);
   
    
}
