package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Delegation;
import com.gaconnecte.auxilium.domain.Governorate;
import com.gaconnecte.auxilium.domain.NotifAlertUser;
import com.gaconnecte.auxilium.domain.Region;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Governorate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GovernorateRepository extends JpaRepository<Governorate,Long> {

	List<Governorate> findAllByRegion(Region region);
        Governorate findByLabel(String label);
        
        @Query("SELECT governorate from Governorate governorate where governorate.addedGageo is Not true ")
    	List<Governorate> findAllGovNotGageo();
}
