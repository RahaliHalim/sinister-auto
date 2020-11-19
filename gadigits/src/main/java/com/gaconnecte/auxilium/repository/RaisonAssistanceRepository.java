package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RaisonAssistance;
import com.gaconnecte.auxilium.domain.Reason;
import com.gaconnecte.auxilium.domain.referential.RefStatusSinister;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the RaisonAssistance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RaisonAssistanceRepository extends JpaRepository<RaisonAssistance,Long> {
    List<RaisonAssistance> findAllByStatus(RefStatusSinister status);
    
	
	@Query("SELECT raisonAssistance from RaisonAssistance raisonAssistance where raisonAssistance.operation.id =:id")
    List<RaisonAssistance> findAllByOperation(@Param("id") Long id);
    
    
}
