package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.PersonneMoraleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PersonneMorale.
 */
public interface PersonneMoraleService {

    /**
     * Save a personneMorale.
     *
     * @param personneMoraleDTO the entity to save
     * @return the persisted entity
     */
    PersonneMoraleDTO save(PersonneMoraleDTO personneMoraleDTO);

    /**
     *  Get all the personneMorales.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonneMoraleDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" personneMorale.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonneMoraleDTO findOne(Long id);

    /**
     *  Delete the "id" personneMorale.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the personneMorale corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonneMoraleDTO> search(String query, Pageable pageable);
}
