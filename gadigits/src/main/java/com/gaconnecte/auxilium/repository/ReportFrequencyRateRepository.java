/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.ReportFrequencyRate;

import java.time.LocalDate;
import java.util.Set;

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
public interface ReportFrequencyRateRepository extends JpaRepository<ReportFrequencyRate, Long>{
    @Query("SELECT sp from ReportFrequencyRate sp WHERE sp.creationDate <= :endDate AND sp.creationDate >= :startDate  order by sp.partnerId")
    Set<ReportFrequencyRate> findAllReportFrequencyRateByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT sp from ReportFrequencyRate sp WHERE sp.partnerId = :partnerId AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate order by sp.partnerId")
    Set<ReportFrequencyRate> findAllReportFrequencyRateByPartnerAndDates(@Param("partnerId") Long partnerId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    
    @Query( "SELECT count(report) FROM ReportFrequencyRate report WHERE (report.creationDate <= :endDate AND report.creationDate >= :startDate) AND "
            + " (  lower(report.partnerName)  like %:filter% OR  lower(report.usageLabel)  like %:filter% "
            + " OR  lower(report.incidentNature)  like %:filter% "
            + " OR  lower(report.typeService)  like %:filter% "
            //+"OR CAST(report.frequencyRate AS text) like %:filter% "
            + ")")
    
    Long countAllWithFilter(@Param("filter") String filter,@Param("startDate") LocalDate startDate,  @Param("endDate") LocalDate endDate);  
   
   @Query("SELECT report FROM ReportFrequencyRate report WHERE (report.creationDate <= :endDate AND report.creationDate >= :startDate) AND "
            + " (  lower(report.partnerName)  like %:filter% OR  lower(report.usageLabel)  like %:filter% "
            + " OR  lower(report.incidentNature)  like %:filter% "  + " OR  lower(report.typeService)  like %:filter% )" 
           // +"OR CAST(report.frequencyRate AS text) like %:filter% ) "
            )
   Page<ReportFrequencyRate> findAllWithFilter(@Param("filter") String filter, Pageable pageable,@Param("startDate") LocalDate startDate,  @Param("endDate") LocalDate endDate);
   
    
   @Query("SELECT count(report) FROM ReportFrequencyRate report WHERE report.creationDate <= :endDate AND report.creationDate >= :startDate")
   Long countAllwithoutfilter(@Param("startDate") LocalDate startDate,  @Param("endDate") LocalDate endDate);
   
   
   
   @Query("SELECT report FROM ReportFrequencyRate report WHERE report.creationDate <= :endDate AND report.creationDate >= :startDate")
   Page<ReportFrequencyRate> findAllWithoutFilter( Pageable pageable,@Param("startDate") LocalDate startDate,  @Param("endDate") LocalDate endDate);
    
   @Query("SELECT count(report) from ReportFrequencyRate report WHERE (report.partnerId = :partnerId AND report.creationDate <= :endDate AND report.creationDate >= :startDate )AND "
           + " (  lower(report.partnerName)  like %:filter% OR  lower(report.usageLabel)  like %:filter% "
           + " OR  lower(report.incidentNature)  like %:filter% "
           + " OR  lower(report.typeService)  like %:filter% "

           //+" OR CAST(report.frequencyRate AS text) like %:filter% "
           +")")
   Long countAllByPartnerAndDatesAndFilter(@Param("filter") String filter,@Param("partnerId") Long partnerId,@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
   
   
   @Query( "SELECT report from ReportFrequencyRate report WHERE (report.partnerId = :partnerId AND report.creationDate <= :endDate AND report.creationDate >= :startDate )AND "
           + " (  lower(report.partnerName)  like %:filter% OR  lower(report.usageLabel)  like %:filter% "
           + " OR  lower(report.incidentNature)  like %:filter% "
           + " OR  lower(report.typeService)  like %:filter% "
           //+"OR   CAST(report.frequencyRate AS text) like %:filter%"
           +" ) ")
   Page<ReportFrequencyRate> findAllByPartnerAndDatesAndFilter(@Param("filter") String filter, Pageable pageable,@Param("partnerId") Long partnerId,@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

   
   @Query("SELECT count(report) from ReportFrequencyRate report WHERE report.partnerId = :partnerId AND report.creationDate <= :endDate AND report.creationDate >= :startDate")
   Long countAllByPartnerAndDates(@Param("partnerId") Long partnerId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
   
   @Query("SELECT report from ReportFrequencyRate report WHERE report.partnerId = :partnerId AND report.creationDate <= :endDate AND report.creationDate >= :startDate")
   Page<ReportFrequencyRate> findAllByPartnerAndDates(Pageable pageable,@Param("partnerId") Long partnerId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
   
}
