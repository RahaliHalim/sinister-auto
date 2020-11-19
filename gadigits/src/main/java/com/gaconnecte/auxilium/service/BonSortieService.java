package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.BonSortieDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BonSortie.
 */
public interface BonSortieService {

    /**
     * Save a bonSortie.
     *
     * @param bonSortieDTO the entity to save
     * @return the persisted entity
     */
    BonSortieDTO save(BonSortieDTO bonSortieDTO);

    /**
     *  Get all the bonSorties.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<BonSortieDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" bonSortie.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BonSortieDTO findOne(Long id);

    /**
     *  Delete the "id" bonSortie.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the bonSortie corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<BonSortieDTO> search(String query, Pageable pageable);



    Long findNewNumBonSortie();

}
