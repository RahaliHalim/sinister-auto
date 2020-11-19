package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.DesignationTypeContratService;
import com.gaconnecte.auxilium.domain.DesignationTypeContrat;
import com.gaconnecte.auxilium.repository.DesignationTypeContratRepository;
import com.gaconnecte.auxilium.repository.search.DesignationTypeContratSearchRepository;
import com.gaconnecte.auxilium.service.dto.DesignationTypeContratDTO;
import com.gaconnecte.auxilium.service.mapper.DesignationTypeContratMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DesignationTypeContrat.
 */
@Service
@Transactional
public class DesignationTypeContratServiceImpl implements DesignationTypeContratService{

    private final Logger log = LoggerFactory.getLogger(DesignationTypeContratServiceImpl.class);

    private final DesignationTypeContratRepository designationTypeContratRepository;

    private final DesignationTypeContratMapper designationTypeContratMapper;

    private final DesignationTypeContratSearchRepository designationTypeContratSearchRepository;

    public DesignationTypeContratServiceImpl(DesignationTypeContratRepository designationTypeContratRepository, DesignationTypeContratMapper designationTypeContratMapper, DesignationTypeContratSearchRepository designationTypeContratSearchRepository) {
        this.designationTypeContratRepository = designationTypeContratRepository;
        this.designationTypeContratMapper = designationTypeContratMapper;
        this.designationTypeContratSearchRepository = designationTypeContratSearchRepository;
    }

    /**
     * Save a designationTypeContrat.
     *
     * @param designationTypeContratDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DesignationTypeContratDTO save(DesignationTypeContratDTO designationTypeContratDTO) {
        log.debug("Request to save DesignationTypeContrat : {}", designationTypeContratDTO);
        DesignationTypeContrat designationTypeContrat = designationTypeContratMapper.toEntity(designationTypeContratDTO);
        designationTypeContrat = designationTypeContratRepository.save(designationTypeContrat);
        DesignationTypeContratDTO result = designationTypeContratMapper.toDto(designationTypeContrat);
        designationTypeContratSearchRepository.save(designationTypeContrat);
        return result;
    }

    /**
     *  Get all the designationTypeContrats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DesignationTypeContratDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DesignationTypeContrats");
        return designationTypeContratRepository.findAll(pageable)
            .map(designationTypeContratMapper::toDto);
    }

    /**
     *  Get one designationTypeContrat by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DesignationTypeContratDTO findOne(Long id) {
        log.debug("Request to get DesignationTypeContrat : {}", id);
        DesignationTypeContrat designationTypeContrat = designationTypeContratRepository.findOne(id);
        return designationTypeContratMapper.toDto(designationTypeContrat);
    }

    /**
     *  Delete the  designationTypeContrat by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DesignationTypeContrat : {}", id);
        designationTypeContratRepository.delete(id);
        designationTypeContratSearchRepository.delete(id);
    }

    /**
     * Search for the designationTypeContrat corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DesignationTypeContratDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DesignationTypeContrats for query {}", query);
        Page<DesignationTypeContrat> result = designationTypeContratSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(designationTypeContratMapper::toDto);
    }
}
