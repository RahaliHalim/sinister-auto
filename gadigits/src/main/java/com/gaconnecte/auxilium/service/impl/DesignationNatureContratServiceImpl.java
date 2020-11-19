package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.DesignationNatureContratService;
import com.gaconnecte.auxilium.domain.DesignationNatureContrat;
import com.gaconnecte.auxilium.repository.DesignationNatureContratRepository;
import com.gaconnecte.auxilium.repository.search.DesignationNatureContratSearchRepository;
import com.gaconnecte.auxilium.service.dto.DesignationNatureContratDTO;
import com.gaconnecte.auxilium.service.mapper.DesignationNatureContratMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DesignationNatureContrat.
 */
@Service
@Transactional
public class DesignationNatureContratServiceImpl implements DesignationNatureContratService{

    private final Logger log = LoggerFactory.getLogger(DesignationNatureContratServiceImpl.class);

    private final DesignationNatureContratRepository designationNatureContratRepository;

    private final DesignationNatureContratMapper designationNatureContratMapper;

    private final DesignationNatureContratSearchRepository designationNatureContratSearchRepository;

    public DesignationNatureContratServiceImpl(DesignationNatureContratRepository designationNatureContratRepository, DesignationNatureContratMapper designationNatureContratMapper, DesignationNatureContratSearchRepository designationNatureContratSearchRepository) {
        this.designationNatureContratRepository = designationNatureContratRepository;
        this.designationNatureContratMapper = designationNatureContratMapper;
        this.designationNatureContratSearchRepository = designationNatureContratSearchRepository;
    }

    /**
     * Save a designationNatureContrat.
     *
     * @param designationNatureContratDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DesignationNatureContratDTO save(DesignationNatureContratDTO designationNatureContratDTO) {
        log.debug("Request to save DesignationNatureContrat : {}", designationNatureContratDTO);
        DesignationNatureContrat designationNatureContrat = designationNatureContratMapper.toEntity(designationNatureContratDTO);
        designationNatureContrat = designationNatureContratRepository.save(designationNatureContrat);
        DesignationNatureContratDTO result = designationNatureContratMapper.toDto(designationNatureContrat);
        designationNatureContratSearchRepository.save(designationNatureContrat);
        return result;
    }

    /**
     *  Get all the designationNatureContrats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DesignationNatureContratDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DesignationNatureContrats");
        return designationNatureContratRepository.findAll(pageable)
            .map(designationNatureContratMapper::toDto);
    }

    /**
     *  Get one designationNatureContrat by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DesignationNatureContratDTO findOne(Long id) {
        log.debug("Request to get DesignationNatureContrat : {}", id);
        DesignationNatureContrat designationNatureContrat = designationNatureContratRepository.findOne(id);
        return designationNatureContratMapper.toDto(designationNatureContrat);
    }

    /**
     *  Delete the  designationNatureContrat by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DesignationNatureContrat : {}", id);
        designationNatureContratRepository.delete(id);
        designationNatureContratSearchRepository.delete(id);
    }

    /**
     * Search for the designationNatureContrat corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DesignationNatureContratDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DesignationNatureContrats for query {}", query);
        Page<DesignationNatureContrat> result = designationNatureContratSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(designationNatureContratMapper::toDto);
    }
}
