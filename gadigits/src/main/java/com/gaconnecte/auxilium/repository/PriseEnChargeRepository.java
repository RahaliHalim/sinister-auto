package com.gaconnecte.auxilium.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gaconnecte.auxilium.domain.Assitances;
import com.gaconnecte.auxilium.domain.PriseEnCharge;

@Repository
public interface PriseEnChargeRepository extends JpaRepository<PriseEnCharge, Long> {

    @Query("SELECT pec from PriseEnCharge pec WHERE pec.incidentDate <= :endDate AND pec.incidentDate >= :startDate")
    List<PriseEnCharge> findAllPriseEnChargeByDates(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT pec from PriseEnCharge pec WHERE pec.immatriculationVehicule = :imatriculation AND pec.incidentDate <= :endDate AND pec.incidentDate >= :startDate")
    Set<PriseEnCharge> findAllPriseEnChargemMatriculation(@Param("imatriculation") String imatriculation,
            @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT pec from PriseEnCharge pec WHERE pec.reference = :referenceGa AND pec.incidentDate <= :endDate AND pec.incidentDate >= :startDate")//CONVERT(varchar(10), montantTotalDevis)
    Set<PriseEnCharge> findAllPriseEnChargeByReference(@Param("referenceGa") String referenceGa,
            @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

   /* @Query("SELECT prise FROM PriseEnCharge prise WHERE lower(prise.reference) like %:filter% OR lower(prise.refSinister) like %:filter% "
            + "OR lower(prise.companyName) like %:filter% OR lower(prise.nomAgentAssurance) like %:filter% OR lower(prise.numeroContrat) like %:filter% "
            + "OR lower(prise.immatriculationVehicule) like %:filter% OR lower(prise.marque) like %:filter% OR lower(prise.compagnieAdverse) like %:filter% OR lower(prise.immatriculationTiers) like %:filter% "
            + "OR lower(prise.modeGestion) like %:filter% OR lower(prise.positionGa) like %:filter% "
            + "OR lower(prise.pointChock) like %:filter% OR lower(prise.lightShock) like %:filter% "
            + "OR lower(prise.chargeNom) like %:filter% OR lower(prise.chargePrenom) like %:filter% OR lower(prise.reparateur) like %:filter% "
            + "OR lower(prise.expert) like %:filter% OR lower(prise.bossNom) like %:filter% OR lower(prise.bossPrenom) like %:filter% OR lower(prise.responsableNom) like %:filter% "
            + "OR lower(prise.responsablePrenom) like %:filter% OR lower(prise.etatPrestation) like %:filter% OR lower(prise.approvPec) like %:filter% OR lower(prise.etapePrestation) like %:filter% "
            + "OR lower(prise.raisonSocialeAssure) like %:filter% OR lower(prise.raisonSocialTiers) like %:filter% OR lower(prise.motifGeneral) like %:filter% ")
    Page<PriseEnCharge> findAllWithFilter(@Param("filter") String filter, Pageable pageable);

    @Query("SELECT count(prise) FROM PriseEnCharge prise WHERE lower(prise.reference) like %:filter% OR lower(prise.refSinister) like %:filter% "
            + "OR lower(prise.companyName) like %:filter% OR lower(prise.nomAgentAssurance) like %:filter% OR lower(prise.numeroContrat) like %:filter% "
            + "OR lower(prise.immatriculationVehicule) like %:filter% OR lower(prise.marque) like %:filter% OR lower(prise.compagnieAdverse) like %:filter% OR lower(prise.immatriculationTiers) like %:filter% "
            + "OR lower(prise.modeGestion) like %:filter% OR lower(prise.positionGa) like %:filter% "
            + "OR lower(prise.pointChock) like %:filter% OR lower(prise.lightShock) like %:filter% "
            + "OR lower(prise.chargeNom) like %:filter% OR lower(prise.chargePrenom) like %:filter% OR lower(prise.reparateur) like %:filter% "
            + "OR lower(prise.expert) like %:filter% OR lower(prise.bossNom) like %:filter% OR lower(prise.bossPrenom) like %:filter% OR lower(prise.responsableNom) like %:filter% "
            + "OR lower(prise.responsablePrenom) like %:filter% OR lower(prise.etatPrestation) like %:filter% OR lower(prise.approvPec) like %:filter% OR lower(prise.etapePrestation) like %:filter% "
            + "OR lower(prise.raisonSocialeAssure) like %:filter% OR lower(prise.raisonSocialTiers) like %:filter% OR lower(prise.motifGeneral) like %:filter% ")
    Long countAllWithFilter(@Param("filter") String filter);*/
    
    @Query(value = "select * from public.findallpriseencharge (:profileId,:personId,:userExtraId)  /*#pageable*/", 
			countQuery="select count(*) from public.findallpriseencharge (:profileId,:personId,:userExtraId)",
			nativeQuery = true)
	Page<PriseEnCharge> findallpriseencharge( Pageable pageable, @Param("profileId") Long profileId,@Param("personId") Long personId,@Param("userExtraId") Long userExtraId);
    @Query(value = "select count(*) from public.findallpriseencharge (:profileId,:personId,:userExtraId)", 
			nativeQuery = true)
    Long countallpriseencharge( @Param("profileId") Long profileId,@Param("personId") Long personId,@Param("userExtraId") Long userExtraId);
	
    @Query(value = "select * from public.findallpriseenchargewithfilter (:filter,:profileId,:personId,:userExtraId)  /*#pageable*/", 
			countQuery="select count(*) from public.findallpriseenchargewithfilter (:filter,:profileId,:personId,:userExtraId)",
			nativeQuery = true)
    Page<PriseEnCharge> findAllWithFilter(@Param("filter") String filter, Pageable pageable,@Param("profileId") Long profileId,@Param("personId") Long personId,@Param("userExtraId") Long userExtraId);
    
    @Query(value = "select count(*) from public.findallpriseenchargewithfilter (:filter,:profileId,:personId,:userExtraId)", 
			nativeQuery = true)
    Long countAllWithFilter(@Param("filter") String filter,@Param("profileId") Long profileId,@Param("personId") Long personId,@Param("userExtraId") Long userExtraId);


}