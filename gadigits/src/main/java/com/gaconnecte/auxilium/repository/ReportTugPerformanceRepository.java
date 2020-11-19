/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.ReportTugPerformance;
import com.gaconnecte.auxilium.domain.view.ViewPolicy;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hannibaal
 */
@Repository
public interface ReportTugPerformanceRepository extends JpaRepository<ReportTugPerformance, Long>{
    @Query("SELECT sp from ReportTugPerformance sp WHERE sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
    List<ReportTugPerformance> findAllReportTugPerformanceByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT sp from ReportTugPerformance sp WHERE sp.partnerId = :partnerId AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
    List<ReportTugPerformance> findAllReportTugPerformanceByPartnerAndDates(@Param("partnerId") Long partnerId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    
    @Query("SELECT count(report) FROM ReportTugPerformance report WHERE (report.creationDate <= :endDate AND report.creationDate >= :startDate) AND (lower(report.affectedTugLabel) like %:filter% OR  lower(report.reference)  like %:filter%"
            + " OR  lower(report.registrationNumber)  like %:filter% OR  lower(report.partnerName)  like %:filter% OR  lower(report.serviceTypeLabel)  like %:filter%"
            + " OR  lower(report.usageLabel)  like %:filter% OR  lower(report.incidentGovernorateLabel)  like %:filter% OR  lower(report.cancelGroundsDescription)  like %:filter%)")
    
    Long countAllWithFilter2(@Param("filter") String filter, @Param("startDate") LocalDate startDate,  @Param("endDate") LocalDate endDate);
    
   
    
    @Query("SELECT report FROM ReportTugPerformance report WHERE (report.creationDate <= :endDate AND report.creationDate >= :startDate) AND (lower(report.affectedTugLabel) like %:filter% OR  lower(report.reference)  like %:filter%"
             + " OR  lower(report.registrationNumber)  like %:filter% OR  lower(report.partnerName)  like %:filter% OR  lower(report.serviceTypeLabel)  like %:filter%"
             + " OR  lower(report.usageLabel)  like %:filter% OR  lower(report.incidentGovernorateLabel)  like %:filter% OR  lower(report.cancelGroundsDescription)  like %:filter%)")
    Page<ReportTugPerformance> findAllWithFilter2(@Param("filter") String filter, Pageable pageable, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
     
   
    @Query("SELECT report FROM ReportTugPerformance report WHERE report.creationDate <= :endDate AND report.creationDate >= :startDate")
    Page<ReportTugPerformance> findAllS(Pageable pageable, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT count(report) FROM ReportTugPerformance report WHERE report.creationDate <= :endDate AND report.creationDate >= :startDate")
    Long countAllS(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    
    
    @Query(value = "select count(*) from public.findallReportTugPerfwithfilter (:filter,:startDate,:endDate,:partnerId,:tugId,:zoneId) ",
    		nativeQuery = true)
    Long countAllWithFilter(@Param("filter") String filter, @Param("startDate") String startDate,  @Param("endDate") String endDate,@Param("partnerId") Integer partnerId,@Param("tugId") Integer tugId,@Param("zoneId") Integer zoneId);
    
    @Query(value = "select * from public.findallreporttugperfwithfilter(:filter, :startDate, :endDate,:partnerId,:tugId,:zoneId)  /*#pageable*/",
			countQuery="select count(*) from public.findallreporttugperfwithfilter(:filter,:startDate,:endDate,:partnerId,:tugId,:zoneId) ", 
			nativeQuery = true)
    Page<ReportTugPerformance> findAllWithFilter(@Param("filter") String filter, Pageable pageable, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("partnerId") Integer partnerId,@Param("tugId") Integer tugId,@Param("zoneId") Integer zoneId);
       
    
    
    @Query(value = "select count(*) from public.findallreporttugperwithoutfilterdt(:startDate,:endDate,:partnerId,:tugId,:zoneId) ",
    		nativeQuery = true)
    Long countAllwithoutfilterdt(@Param("startDate") String startDate,  @Param("endDate") String endDate,@Param("partnerId") Integer partnerId,@Param("tugId") Integer tugId,@Param("zoneId") Integer zoneId);
    
    @Query(value = "select * from public.findallreporttugperwithoutfilterdt( :startDate, :endDate,:partnerId,:tugId,:zoneId)  /*#pageable*/",
			countQuery="select count(*) from public.findallreporttugperwithoutfilterdt(:startDate,:endDate,:partnerId,:tugId,:zoneId) ", 
			nativeQuery = true)
    Page<ReportTugPerformance> findallwithoutfilterdt( Pageable pageable, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("partnerId") Integer partnerId,@Param("tugId") Integer tugId,@Param("zoneId") Integer zoneId);
       
}
