package com.gaconnecte.auxilium.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gaconnecte.auxilium.service.dto.ReferentielDTO;;

public interface ReferentielService {

    /**
     * Save a referentiel.
     *
     * @param referentielDTO the entity to save
     * @return the persisted entity
     */
	ReferentielDTO save(ReferentielDTO referentielDTO);

    /**
     *  Get all the referentiel.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ReferentielDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" referentiel.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ReferentielDTO findOne(Long id);

    /**
     *  Delete the "id" referentiel.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

}