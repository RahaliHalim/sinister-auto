package com.gaconnecte.auxilium.repository.referential;

import com.gaconnecte.auxilium.domain.Partner;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.RefTypeService;
import com.gaconnecte.auxilium.domain.prm.ConventionAmendment;
import com.gaconnecte.auxilium.domain.referential.RefPack;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the RefPack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefPackRepository extends JpaRepository<RefPack, Long> {
     
	@Query("SELECT pack from RefPack pack")
	Set<RefPack> findAllPacks();
        //Set<RefPack> findByServiceType(RefTypeService refTypeService);
        
        @Query("select pack from RefPack pack where pack.id = :packId and :serviceType member pack.serviceTypes")
        RefPack findPackIfServiceTypeAuthorized(@Param("packId") Long packId, @Param("serviceType") RefTypeService serviceType);

        @Query("select pack from RefPack pack where pack.convention.client = :client")
        Set<RefPack> findAllByClient(@Param("client") Partner client);

        @Query("select pack from RefPack pack where pack.amendment.id IS NOT NULL AND pack.amendment.convention.client.id = :partnerId")
        Set<RefPack> findAllByPartnerIdAndAmendment(@Param("partnerId") Long partnerId);
        
        @Query("select pack from RefPack pack where pack.amendment.id IS NOT NULL AND (:dateNow BETWEEN pack.amendment.startDate AND pack.amendment.endDate) AND pack.amendment.convention.client.id = :partnerId")
        Set<RefPack> findAllActifByPartnerIdAndAmendment(@Param("partnerId") Long partnerId, @Param("dateNow") LocalDate dateNow);
        
        @Query("select amendment from ConventionAmendment amendment where amendment.id IS NOT NULL AND (:dateNow BETWEEN amendment.startDate AND amendment.endDate) AND amendment.convention.client.id = :partnerId")
        Set<ConventionAmendment> findAllAmendmentsActifByPartnerIdAndAmendment(@Param("partnerId") Long partnerId, @Param("dateNow") LocalDate dateNow);


}