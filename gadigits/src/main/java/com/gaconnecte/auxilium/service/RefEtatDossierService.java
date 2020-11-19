package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefEtatDossierDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RefEtatDossier.
 */
public interface RefEtatDossierService {

    /**
     * Save a refEtatDossier.
     *
     * @param refEtatDossierDTO the entity to save
     * @return the persisted entity
     */
    RefEtatDossierDTO save(RefEtatDossierDTO refEtatDossierDTO);

    /**
     *  Get all the refEtatDossiers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefEtatDossierDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" refEtatDossier.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefEtatDossierDTO findOne(Long id);

    /**
     *  Delete the "id" refEtatDossier.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the refEtatDossier corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefEtatDossierDTO> search(String query, Pageable pageable);
}
