package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Delegation;
import com.gaconnecte.auxilium.domain.Governorate;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Delegation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DelegationRepository extends JpaRepository<Delegation, Long> {
    
    List<Delegation> findAllByGovernorate(Governorate governorate);
    
    @Query("select delegation from Delegation delegation where delegation.label =:label")
    Delegation findDelegationByName(@Param("label") String label);
    
    @Query("SELECT delegation from Delegation delegation where delegation.addedGageo is Not true ")
	List<Delegation> findAllDelNotGageo();
}
