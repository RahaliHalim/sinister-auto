package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.view.ViewPolicy;
import com.gaconnecte.auxilium.domain.view.ViewPolicyIndicator;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the ViewPolicy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ViewPolicyRepository extends JpaRepository<ViewPolicy,Long> {
    
    @Query("SELECT policy FROM ViewPolicy policy WHERE lower(policy.policyNumber) like %:filter% OR  lower(policy.companyName)  like %:filter%"
            + " OR  lower(policy.agencyName)  like %:filter% OR  lower(policy.registrationNumber)  like %:filter% OR  lower(policy.policyHolderName)  like %:filter%")
    Page<ViewPolicy> findAllWithFilter(@Param("filter") String filter, Pageable pageable);

    @Query("SELECT count(policy) FROM ViewPolicy policy WHERE lower(policy.policyNumber) like %:filter% OR  lower(policy.companyName)  like %:filter%"
            + " OR  lower(policy.agencyName)  like %:filter% OR  lower(policy.registrationNumber)  like %:filter% OR  lower(policy.policyHolderName)  like %:filter%")
    Long countAllWithFilter(@Param("filter") String filter);

    @Query(value = "SELECT agency_label, usage_label, count(pack_id), zone_label " + 
    		"FROM view_policy_agency " + 
    		"GROUP BY agency_label, usage_label, zone_label " + 
    		"ORDER BY agency_label, usage_label, zone_label", nativeQuery = true)
    String[][] findAllPolicyIndicators();

    @Query(value = "SELECT agency_label, usage_label, pack_number, zone_label from public.function_policy_indicator(:companyId, :zoneId, :packId, :startDate, :endDate)", nativeQuery = true)
    String[][] findAllPolicyIndicators(@Param("companyId") Long companyId, @Param("zoneId") Long zoneId, @Param("packId") Long packId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
}
