package com.gaconnecte.auxilium.repository;


import com.gaconnecte.auxilium.domain.TugPricing;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Tug Pricing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TugPricingRepository extends JpaRepository<TugPricing,Long> {
	  @Query("select  tugPricing from TugPricing tugPricing where tugPricing.tug.id =:refRemorqueurId")
	     TugPricing findBy(@Param("refRemorqueurId") Long refRemorqueurId);    
}
