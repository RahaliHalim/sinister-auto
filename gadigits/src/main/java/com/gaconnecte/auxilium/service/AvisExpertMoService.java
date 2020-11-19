package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.AvisExpertMoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AvisExpertMo.
 */
public interface AvisExpertMoService {

    /**
     * Save a avisExpertMo.
     *
     * @param avisExpertMoDTO the entity to save
     * @return the persisted entity
     */
    AvisExpertMoDTO save(AvisExpertMoDTO avisExpertMoDTO);

    /**
     *  Get all the avisExpertMos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AvisExpertMoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" avisExpertMo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AvisExpertMoDTO findOne(Long id);

    /**
     *  Delete the "id" avisExpertMo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the avisExpertMo corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AvisExpertMoDTO> search(String query, Pageable pageable);
}
