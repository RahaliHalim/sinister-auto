package com.gaconnecte.auxilium.service.referential;

import com.gaconnecte.auxilium.service.referential.dto.RefPricingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RefPricing.
 */
public interface RefPricingService {

    /**
     * Save a refPricing.
     *
     * @param refPricingDTO the entity to save
     * @return the persisted entity
     */
    RefPricingDTO save(RefPricingDTO refPricingDTO);

    /**
     *  Get all the refPricings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefPricingDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" refPricing.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefPricingDTO findOne(Long id);

    /**
     *  Delete the "id" refPricing.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

}
