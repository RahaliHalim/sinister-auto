package com.gaconnecte.auxilium.repository.referential;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.referential.RefPricing;

/**
 * Spring Data JPA repository for the RefPricing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefPricingRepository extends JpaRepository<RefPricing,Long> {
    
}
