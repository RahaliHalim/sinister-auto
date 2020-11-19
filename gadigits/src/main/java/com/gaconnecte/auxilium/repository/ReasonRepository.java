package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Reason;
import com.gaconnecte.auxilium.domain.SinisterPec;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Reason entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReasonRepository extends JpaRepository<Reason,Long> {
    
	@Query("SELECT reason from Reason reason where reason.step.id = 7 ")
    List<Reason> findAllMotifReopened();
	
	@Query("SELECT reason from Reason reason where reason.step.id = 5 ")
    List<Reason> findAllMotifRefused();
	
	@Query("SELECT reason from Reason reason where reason.step.id = 4 ")
    List<Reason> findAllMotifCanceled();
	
	@Query("SELECT reason from Reason reason where reason.step.id =:stepId")
    List<Reason> findMotifsByStepId(@Param("stepId") Long stepId);
	

}
