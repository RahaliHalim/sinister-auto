package com.gaconnecte.auxilium.service;
import com.gaconnecte.auxilium.service.dto.TugPricingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing TugPricing.
 */
public interface TugPricingService {

    /**
     * Save a TugPricing.
     *
     * @param TugPricingDTO the entity to save
     * @return the persisted entity
     */
	TugPricingDTO save(TugPricingDTO tugPricingDTO);

    /**
     *  Get all the TugPricings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TugPricingDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" TugPricing.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TugPricingDTO findBy(Long id);

    /**
     *  Delete the "id" TugPricing.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

}
