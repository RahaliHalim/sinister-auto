package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.AgentGeneralDTO;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AgentGeneral.
 */
public interface AgentGeneralService {

    /**
     * Save a agentGeneral.
     *
     * @param agentGeneralDTO the entity to save
     * @return the persisted entity
     */
    AgentGeneralDTO save(AgentGeneralDTO agentGeneralDTO);

    
    /**
     *  Get all the agentGenerals.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AgentGeneralDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" agentGeneral.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AgentGeneralDTO findOne(Long id);

    /**
     *  Get the agence agentGeneral.
     *
     *  @param id the id of the entity
     *  @return the entity
    
    List<AgentAgence> findAgencesByAgent(Long id);
    
   
     *  Delete the "id" agentGeneral.
     *
     *  @param id the id of the entity
     */
   
    void delete(Long id);

    /**
     * Search for the agentGeneral corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AgentGeneralDTO> search(String query, Pageable pageable);
}
