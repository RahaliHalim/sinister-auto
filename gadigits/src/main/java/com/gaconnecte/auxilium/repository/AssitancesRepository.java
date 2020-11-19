package com.gaconnecte.auxilium.repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.gaconnecte.auxilium.domain.Assitances;

@Repository
public interface AssitancesRepository extends JpaRepository<Assitances,Long> {
	
	@Query("SELECT assi from Assitances assi WHERE  assi.incidentDate <= :endDate AND assi.incidentDate >= :startDate")
    List<Assitances> findAllAssistancesByDates( @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
	@Query("SELECT assi from Assitances assi WHERE assi.immatriculationVehicule = :imatriculation AND assi.incidentDate <= :endDate AND assi.incidentDate >= :startDate")
    Set<Assitances> findAllAssistancesByDatesmatriculation(@Param("imatriculation") String imatriculation, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
	@Query(value = "select count(*) from public.findallassistancesearchwithfilter (:filter , :startDate , :endDate, :immatriculation, :reference, :statutId, :partnerId, :agencyId, :vr )",
			nativeQuery = true)
	Long countAllAssistanceWithFilterDt(@Param("filter") String filter, @Param("startDate") String startDate, @Param("endDate") String endDate , @Param("immatriculation") String immatriculation, @Param("reference") String reference , @Param("statutId") Long statutId , @Param("partnerId") Long partnerId, @Param("agencyId") Long agencyId, @Param("vr") boolean vr);
	
	@Query(value = "select * from public.findallassistancesearchwithfilter (:filter , :startDate , :endDate, :immatriculation, :reference, :statutId, :partnerId, :agencyId, :vr)  /*#pageable*/",
			countQuery="select count(*) from public.findallassistancesearchwithfilter (:filter , :startDate , :endDate, :immatriculation, :reference, :statutId, :partnerId, :agencyId, :vr)",
			nativeQuery = true)
    Page<Assitances> findAllAssistancesWithFilter(@Param("filter") String filter ,@Param("startDate") String startDate, @Param("endDate") String endDate, Pageable pageable,@Param("immatriculation") String immatriculation, @Param("reference") String reference, @Param("statutId") Long statutId , @Param("partnerId") Long partnerId, @Param("agencyId") Long agencyId, @Param("vr") boolean vr);
	
	@Query(value = "SELECT count(*) from public.findallassistancesearchwithoutfilterdt (:startDate , :endDate, :immatriculation, :reference, :statutId, :partnerId, :agencyId, :vr ) " ,
			nativeQuery = true)   
    Long countAssistanceWithSearch(@Param("startDate") String startDate, @Param("endDate") String endDate , @Param("immatriculation") String immatriculation, @Param("reference") String reference , @Param("statutId") Long statutId , @Param("partnerId") Long partnerId, @Param("agencyId") Long agencyId, @Param("vr") boolean vr);
	
	@Query(value = "select * from public.findallassistancesearchwithoutfilterdt (:startDate , :endDate, :immatriculation, :reference, :statutId, :partnerId, :agencyId, :vr)  /*#pageable*/", 
			countQuery="select count(*) from public.findallassistancesearchwithoutfilterdt (:startDate , :endDate, :immatriculation, :reference, :statutId, :partnerId, :agencyId, :vr)",
			nativeQuery = true)
	Page<Assitances> findAllAssistanceWithoutFilter(@Param("startDate") String startDate, @Param("endDate") String endDate, Pageable pageable,@Param("immatriculation") String immatriculation, @Param("reference") String reference, @Param("statutId") Long statutId , @Param("partnerId") Long partnerId, @Param("agencyId") Long agencyId, @Param("vr") boolean vr);
	
	
}
