package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.CelluleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Cellule.
 */
public interface CelluleService {

    /**
     * Save a cellule.
     *
     * @param celluleDTO the entity to save
     * @return the persisted entity
     */
    CelluleDTO save(CelluleDTO celluleDTO);

    /**
     *  Get all the cellules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CelluleDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" cellule.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CelluleDTO findOne(Long id);

    /**
     *  Delete the "id" cellule.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    CelluleDTO findByName(String nom);
}
