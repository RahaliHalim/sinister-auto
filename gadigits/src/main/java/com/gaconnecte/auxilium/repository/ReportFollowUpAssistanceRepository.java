/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.ReportFollowUpAssistance;
import com.gaconnecte.auxilium.domain.ReportTugPerformance;

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
public interface ReportFollowUpAssistanceRepository extends JpaRepository<ReportFollowUpAssistance, Long>{
    @Query("SELECT sp from ReportFollowUpAssistance sp WHERE sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
    List<ReportFollowUpAssistance> findAllReportFollowUpAssistanceByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT sp from ReportFollowUpAssistance sp WHERE sp.partnerId = :partnerId AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
    List<ReportFollowUpAssistance> findAllReportFollowUpAssistanceByPartnerAndDates(@Param("partnerId") Long partnerId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT count(sp) from ReportFollowUpAssistance sp WHERE sp.partnerId = :partnerId AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
    Long countReportFollowUpAssistanceByPartnerAndDates(@Param("partnerId") Long partnerId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT count(report) FROM ReportFollowUpAssistance report WHERE report.creationDate <= :endDate AND report.creationDate >= :startDate")
    Long countAllS(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT count(report) FROM ReportFollowUpAssistance report WHERE report.creationDate <= :endDate AND report.creationDate >= :startDate AND report.partnerId = :partnerId")
    Long countAllSWithPartner(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("partnerId") Long partnerId);
    
    @Query("SELECT report FROM ReportFollowUpAssistance report WHERE report.creationDate <= :endDate AND report.creationDate >= :startDate")
    Page<ReportFollowUpAssistance> findAllS(Pageable pageable, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT report FROM ReportFollowUpAssistance report WHERE report.creationDate <= :endDate AND report.creationDate >= :startDate AND report.partnerId = :partnerId")
    Page<ReportFollowUpAssistance> findAllSWithPartner(Pageable pageable, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("partnerId") Long partnerId);
    
    @Query("SELECT count(report) FROM ReportFollowUpAssistance report WHERE (report.creationDate <= :endDate AND report.creationDate >= :startDate) AND (lower(report.reference) like %:filter% OR  lower(report.registrationNumber)  like %:filter%"
            + " OR  lower(report.fullName)  like %:filter% OR  lower(report.partnerName)  like %:filter% OR  lower(report.packLabel)  like %:filter%"
            + " OR  lower(report.usageLabel)  like %:filter% OR  lower(report.serviceType)  like %:filter% OR  lower(report.incidentNature)  like %:filter%"
            + " OR  lower(report.incidentMonth)  like %:filter% OR  lower(report.affectedTugLabel)  like %:filter% OR  lower(report.incidentLocationLabel)  like %:filter%"
            + " OR  lower(report.destinationLocationLabel)  like %:filter% OR  lower(report.statusLabel)  like %:filter% OR  lower(tugGovernorateLabel)  like %:filter%  )")
    Long countAllWithFilter(@Param("filter") String filter, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT count(report) FROM ReportFollowUpAssistance report WHERE (report.creationDate <= :endDate AND report.creationDate >= :startDate) AND (lower(report.reference) like %:filter% OR  lower(report.registrationNumber)  like %:filter%"
            + " OR  lower(report.fullName)  like %:filter% OR  lower(report.partnerName)  like %:filter% OR  lower(report.packLabel)  like %:filter%"
            + " OR  lower(report.usageLabel)  like %:filter% OR  lower(report.serviceType)  like %:filter% OR  lower(report.incidentNature)  like %:filter%"
            + " OR  lower(report.incidentMonth)  like %:filter% OR  lower(report.affectedTugLabel)  like %:filter% OR  lower(report.incidentLocationLabel)  like %:filter%"
            + " OR  lower(report.destinationLocationLabel)  like %:filter% OR  lower(report.statusLabel)  like %:filter% OR  lower(tugGovernorateLabel)  like %:filter%  ) AND report.partnerId = :partnerId")
    Long countAllWithFilterWithPartner(@Param("filter") String filter, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("partnerId") Long partnerId);
    
    @Query("SELECT report FROM ReportFollowUpAssistance report WHERE (report.creationDate <= :endDate AND report.creationDate >= :startDate) AND (lower(report.reference) like %:filter% OR  lower(report.registrationNumber)  like %:filter%"
            + " OR  lower(report.fullName)  like %:filter% OR  lower(report.partnerName)  like %:filter% OR  lower(report.packLabel)  like %:filter%"
            + " OR  lower(report.usageLabel)  like %:filter% OR  lower(report.serviceType)  like %:filter% OR  lower(report.incidentNature)  like %:filter%"
            + " OR  lower(report.incidentMonth)  like %:filter% OR  lower(report.affectedTugLabel)  like %:filter% OR  lower(report.incidentLocationLabel)  like %:filter%"
            + " OR  lower(report.destinationLocationLabel)  like %:filter% OR  lower(report.statusLabel)  like %:filter% OR  lower(tugGovernorateLabel)  like %:filter%  )")
    Page<ReportFollowUpAssistance> findAllWithFilter(@Param("filter") String filter, Pageable pageable, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT report FROM ReportFollowUpAssistance report WHERE (report.creationDate <= :endDate AND report.creationDate >= :startDate) AND (lower(report.reference) like %:filter% OR  lower(report.registrationNumber)  like %:filter%"
            + " OR  lower(report.fullName)  like %:filter% OR  lower(report.partnerName)  like %:filter% OR  lower(report.packLabel)  like %:filter%"
            + " OR  lower(report.usageLabel)  like %:filter% OR  lower(report.serviceType)  like %:filter% OR  lower(report.incidentNature)  like %:filter%"
            + " OR  lower(report.incidentMonth)  like %:filter% OR  lower(report.affectedTugLabel)  like %:filter% OR  lower(report.incidentLocationLabel)  like %:filter%"
            + " OR  lower(report.destinationLocationLabel)  like %:filter% OR  lower(report.statusLabel)  like %:filter% OR  lower(tugGovernorateLabel)  like %:filter% ) AND report.partnerId = :partnerId")
    Page<ReportFollowUpAssistance> findAllWithFilterWithPartner(@Param("filter") String filter, Pageable pageable, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("partnerId") Long partnerId);
    
    /*@Query(value = "CASE"
    		+" WHEN :partnerId IS NOT NULL THEN (SELECT count(report) FROM report_follow_up_assistance report WHERE report.creation_date <= :endDate AND report.creation_date >= :startDate AND report.partner_id = :partnerId)"
    		+ " ELSE (SELECT count(report) FROM report_follow_up_assistance report WHERE report.creation_date <= :endDate AND report.creation_date >= :startDate)"
    		+ " END", nativeQuery = true)
    Long countAllS(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("partnerId") Long partnerId);
    
    @Query(value = "CASE"
    		+" WHEN :partnerId IS NOT NULL THEN (SELECT report FROM report_follow_up_assistance report WHERE report.creation_date <= :endDate AND report.creation_date >= :startDate AND report.partner_id = :partnerId)"
    		+ " ELSE (SELECT report FROM report_follow_up_assistance report WHERE report.creation_date <= :endDate AND report.creation_date >= :startDate)"
    		+ " END ", nativeQuery = true)
    Page<ReportFollowUpAssistance> findAllS(Pageable pageable, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("partnerId") Long partnerId);
    
    @Query(value = "CASE"
    		+ " WHEN :partnerId IS NOT NULL THEN"
    		+ " (SELECT count(report) FROM report_follow_up_assistance report WHERE (report.creation_date <= :endDate AND report.creation_date >= :startDate) AND (lower(report.reference) like %:filter% OR  lower(report.registration_number)  like %:filter%"
            + " OR  lower(report.full_name)  like %:filter% OR  lower(report.partner_name)  like %:filter% OR  lower(report.pack_label)  like %:filter%"
            + " OR  lower(report.usage_label)  like %:filter% OR  lower(report.service_type)  like %:filter% OR  lower(report.incident_nature)  like %:filter%"
            + " OR  lower(report.incident_month)  like %:filter% OR  lower(report.affected_tug_label)  like %:filter% OR  lower(report.incident_location_label)  like %:filter%"
            + " OR  lower(report.destination_location_label)  like %:filter% OR  lower(report.status_label)  like %:filter% ) AND report.partner_id = :partnerId)"
            + " ELSE"
            + " (SELECT count(report) FROM report_follow_up_assistance report WHERE (report.creation_date <= :endDate AND report.creation_date >= :startDate) AND (lower(report.reference) like %:filter% OR  lower(report.registration_number)  like %:filter%"
            + " OR  lower(report.full_name)  like %:filter% OR  lower(report.partner_name)  like %:filter% OR  lower(report.pack_label)  like %:filter%"
            + " OR  lower(report.usage_label)  like %:filter% OR  lower(report.service_type)  like %:filter% OR  lower(report.incident_nature)  like %:filter%"
            + " OR  lower(report.incident_month)  like %:filter% OR  lower(report.affected_tug_label)  like %:filter% OR  lower(report.incident_location_label)  like %:filter%"
            + " OR  lower(report.destination_location_label)  like %:filter% OR  lower(report.status_label)  like %:filter% ))"
            + " END", nativeQuery = true)
    Long countAllWithFilter(@Param("filter") String filter, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("partnerId") Long partnerId);
    
    @Query(value = "CASE"
    		+ " WHEN :partnerId IS NOT NULL THEN"
    		+ " (SELECT report FROM report_follow_up_assistance report WHERE (report.creation_date <= :endDate AND report.creation_date >= :startDate) AND (lower(report.reference) like %:filter% OR  lower(report.registration_number)  like %:filter%"
            + " OR  lower(report.full_name)  like %:filter% OR  lower(report.partner_name)  like %:filter% OR  lower(report.pack_label)  like %:filter%"
            + " OR  lower(report.usage_label)  like %:filter% OR  lower(report.service_type)  like %:filter% OR  lower(report.incident_nature)  like %:filter%"
            + " OR  lower(report.incident_month)  like %:filter% OR  lower(report.affected_tug_label)  like %:filter% OR  lower(report.incident_location_label)  like %:filter%"
            + " OR  lower(report.destination_location_label)  like %:filter% OR  lower(report.status_label)  like %:filter% ) AND report.partner_id = :partnerId)"
            + " ELSE"
            + " (SELECT report FROM report_follow_up_assistance report WHERE (report.creation_date <= :endDate AND report.creation_date >= :startDate) AND (lower(report.reference) like %:filter% OR  lower(report.registration_number)  like %:filter%"
            + " OR  lower(report.full_name)  like %:filter% OR  lower(report.partner_name)  like %:filter% OR  lower(report.pack_label)  like %:filter%"
            + " OR  lower(report.usage_label)  like %:filter% OR  lower(report.service_type)  like %:filter% OR  lower(report.incident_nature)  like %:filter%"
            + " OR  lower(report.incident_month)  like %:filter% OR  lower(report.affected_tug_label)  like %:filter% OR  lower(report.incident_location_label)  like %:filter%"
            + " OR  lower(report.destination_location_label)  like %:filter% OR  lower(report.status_label)  like %:filter% ))"
            + " END ", nativeQuery = true)
    Page<ReportFollowUpAssistance> findAllWithFilter(@Param("filter") String filter, Pageable pageable, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("partnerId") Long partnerId);*/
}
