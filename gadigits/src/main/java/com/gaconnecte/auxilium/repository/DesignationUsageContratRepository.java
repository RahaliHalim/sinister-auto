package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Contact;
import com.gaconnecte.auxilium.domain.DesignationUsageContrat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DesignationUsageContrat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesignationUsageContratRepository extends JpaRepository<DesignationUsageContrat,Long> {
	
	@Query(value ="select * from designation_usage_contrat where compagnie_id=?1 and ref_usage_contrat_id=?2", nativeQuery = true)
    DesignationUsageContrat findOneByCompagnieIdAndUsageContratiD(Long compagnieId, Long refUsageContratId);
    
}
