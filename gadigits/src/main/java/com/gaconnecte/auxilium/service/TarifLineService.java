package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.TarifLineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing TarifLine.
 */
public interface TarifLineService {

    /**
     * Save a tarifLine.
     *
     * @param tarifDTO the entity to save
     * @return the persisted entity
     */
    TarifLineDTO save(TarifLineDTO tarifLineDTO);

    /**
     *  Get all the tarifLines.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TarifLineDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" tarifLine.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TarifLineDTO findOne(Long id);

    /**
     *  Delete the "id" tarifLine.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

}
