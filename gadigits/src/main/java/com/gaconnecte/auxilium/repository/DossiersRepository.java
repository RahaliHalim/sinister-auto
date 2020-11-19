package com.gaconnecte.auxilium.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.Dossiers;
@Repository
public interface DossiersRepository extends JpaRepository<Dossiers, Long>  {
	
	
	@Query("SELECT d from Dossiers d WHERE  d.incidentDate <= :endDate AND d.incidentDate >= :startDate")
    List<Dossiers> findAllDossiersByDates( @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	@Query("SELECT d from Dossiers d WHERE d.immatriculationVehicule = :imatriculation AND d.incidentDate <= :endDate AND d.incidentDate >= :startDate")
    Set<Dossiers> findAllDossiersByDatesmatriculation(@Param("imatriculation") String imatriculation, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	@Query("SELECT d from Dossiers d WHERE d.reference = :referenceGa AND d.incidentDate <= :endDate AND d.incidentDate >= :startDate")
	Set<Dossiers> findAllDossiersByReference(@Param("referenceGa") String referenceGa, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
}
