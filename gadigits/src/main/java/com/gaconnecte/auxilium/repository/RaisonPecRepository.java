package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RaisonPec;
import com.gaconnecte.auxilium.domain.Reason;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the RaisonPec entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RaisonPecRepository extends JpaRepository<RaisonPec,Long> {
	
	@Query("SELECT reasonPec from RaisonPec reasonPec where reasonPec.statusPec.id = 6 ")
    List<RaisonPec> findAllMotifReopened();
	
	@Query("SELECT reasonPec from RaisonPec reasonPec where reasonPec.statusPec.id = 5 ")
    List<RaisonPec> findAllMotifRefused();
	
	@Query("SELECT reasonPec from RaisonPec reasonPec where reasonPec.statusPec.id = 4 ")
    List<RaisonPec> findAllMotifCanceled();
	
	@Query("SELECT reasonPec from RaisonPec reasonPec where reasonPec.statusPec.id =:stepId")
    List<RaisonPec> findMotifsByStepId(@Param("stepId") Long stepId);

	
	@Query("SELECT reasonPec from RaisonPec reasonPec where reasonPec.operation.id =:operationId")
    List<RaisonPec> findMotifsByOperationId(@Param("operationId") Long operationId);
	
	@Query("SELECT reasonPec from RaisonPec reasonPec where reasonPec.pecStatusChangeMatrix.id =:changeMatrixId")
    List<RaisonPec> findMotifsByStatusPecStatusChangeMatrix(@Param("changeMatrixId") Long changeMatrixId);
    
	
	@Query("SELECT reasonPec from RaisonPec reasonPec where reasonPec.label=:label")
    RaisonPec findWithLabel(@Param("label") String label);
}
