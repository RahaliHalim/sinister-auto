package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.VatRate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.Set;


/**
 * Spring Data JPA repository for the VatRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VatRateRepository extends JpaRepository<VatRate,Long> {
    
    @Query("SELECT vatRate FROM VatRate vatRate WHERE vatRate.id IN (SELECT MAX(vatRate.id) FROM VatRate vatRate GROUP BY vatRate.vatRate) AND vatRate.active = true")
    Set<VatRate> findAllVatRate();
}
