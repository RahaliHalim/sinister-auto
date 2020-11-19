package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.AgentGeneralService;
import com.gaconnecte.auxilium.domain.AgentGeneral;
import com.gaconnecte.auxilium.repository.AgentGeneralRepository;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.repository.search.AgentGeneralSearchRepository;
import com.gaconnecte.auxilium.service.dto.AgentGeneralDTO;
import com.gaconnecte.auxilium.service.mapper.AgentGeneralMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Service Implementation for managing AgentGeneral.
 */
@Service
@Transactional
public class AgentGeneralServiceImpl implements AgentGeneralService{

    private final Logger log = LoggerFactory.getLogger(AgentGeneralServiceImpl.class);

    private final AgentGeneralRepository agentGeneralRepository;

    private final AgentGeneralMapper agentGeneralMapper;
    
    private final UserRepository userRepository;
    
    private final AgentGeneralSearchRepository agentGeneralSearchRepository;

    public AgentGeneralServiceImpl(AgentGeneralRepository agentGeneralRepository, AgentGeneralMapper agentGeneralMapper, AgentGeneralSearchRepository agentGeneralSearchRepository, UserRepository userRepository) {
        this.agentGeneralRepository = agentGeneralRepository;
        this.agentGeneralMapper = agentGeneralMapper;
        this.agentGeneralSearchRepository = agentGeneralSearchRepository;
        this.userRepository = userRepository;
    }

    /**
     * 
     * Save a agentGeneral.
     *
     * @param agentGeneralDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AgentGeneralDTO save(AgentGeneralDTO agentGeneralDTO) {
        log.debug("Request to save AgentGeneral : {}", agentGeneralDTO);
        AgentGeneral agentGeneral = agentGeneralMapper.toEntity(agentGeneralDTO);
        agentGeneral = agentGeneralRepository.save(agentGeneral);
        /*
        if (agentGeneralDTO.getAgences() != null) {
        	Set<AgentAgence> listAgentAgence = new HashSet<>();
        	for (Long agence : agentGeneralDTO.getAgences()) {
        	
                	AgentAgence agag= new AgentAgence();
                	
                	agag.setAgentgeneral(agentGeneral);
                	agag.setAgence(refAgenceRepository.getOne(agence));
                	listAgentAgence.add(agag);
                	
                }
        	agentGeneral.getListAgence().clear();
        	agentGeneral.getListAgence().addAll(listAgentAgence);
        	agentGeneralRepository.save(agentGeneral);
                }
        */
        AgentGeneralDTO result = agentGeneralMapper.toDto(agentGeneral);
        agentGeneralSearchRepository.save(agentGeneral);
        return result;
    }

    /**
     *  Get all the agentGenerals.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AgentGeneralDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AgentGenerals");
        return agentGeneralRepository.findAll(pageable)
            .map(agentGeneralMapper::toDto);
    }

    /**
     *  Get one agentGeneral by id.
     *
     *  @param id the id of the entity
     *  @return the entity
    
    @Override
    @Transactional(readOnly = true)
    public List<AgentAgence> findAgencesByAgent(Long id) {
        log.debug("Request to get Agence : {}", id);
        AgentAgence agentAgence = agentGeneralRepository.findAgencesByAgent(id));
        return agentGeneralMapper.toDto(agentGeneral);
    }
    */
    
    /**
     *  Get one agentGeneral by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AgentGeneralDTO findOne(Long id) {
        log.debug("Request to get AgentGeneral : {}", id);
        AgentGeneral agentGeneral = agentGeneralRepository.findOne(id);
        return agentGeneralMapper.toDto(agentGeneral);
    }

    /**
     *  Delete the  agentGeneral by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AgentGeneral : {}", id);
        agentGeneralRepository.delete(id);
        agentGeneralSearchRepository.delete(id);
    }

    /**
     * Search for the agentGeneral corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AgentGeneralDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AgentGenerals for query {}", query);
        Page<AgentGeneral> result = agentGeneralSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(agentGeneralMapper::toDto);
    }


}
