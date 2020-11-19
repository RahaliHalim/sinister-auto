package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.QuotationStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing quotationStatusDTO.
 */
public interface QuotationStatusService {

    /**
     * Save a quotationStatusDTO.
     *
     * @param quotationStatusDTO the entity to save
     * @return the persisted entity
     */
	QuotationStatusDTO save(QuotationStatusDTO quotationStatusDTO);

    /**
     *  Get all the quotationStatusDTOs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<QuotationStatusDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" quotationStatusDTO.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    QuotationStatusDTO findOne(Long id);

    /**
     *  Delete the "id" quotationStatusDTO.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

}
