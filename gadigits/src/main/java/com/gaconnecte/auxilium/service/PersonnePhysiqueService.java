package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.PersonnePhysiqueDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PersonnePhysique.
 */
public interface PersonnePhysiqueService {

    /**
     * Save a personnePhysique.
     *
     * @param personnePhysiqueDTO the entity to save
     * @return the persisted entity
     */
    PersonnePhysiqueDTO save(PersonnePhysiqueDTO personnePhysiqueDTO);

    /**
     *  Get all the personnePhysiques.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonnePhysiqueDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" personnePhysique.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonnePhysiqueDTO findOne(Long id);

    /**
     *  Delete the "id" personnePhysique.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the personnePhysique corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonnePhysiqueDTO> search(String query, Pageable pageable);
}
