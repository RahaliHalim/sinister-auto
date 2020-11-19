package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.TarifDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Tarif.
 */
public interface TarifService {

    /**
     * Save a tarif.
     *
     * @param tarifDTO the entity to save
     * @return the persisted entity
     */
    TarifDTO save(TarifDTO tarifDTO);

    /**
     *  Get all the tarifs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TarifDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" tarif.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TarifDTO findOne(Long id);

    /**
     *  Delete the "id" tarif.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

}
