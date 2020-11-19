package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefModeReglementService;
import com.gaconnecte.auxilium.domain.RefModeReglement;
import com.gaconnecte.auxilium.repository.RefModeReglementRepository;
import com.gaconnecte.auxilium.repository.search.RefModeReglementSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefModeReglementDTO;
import com.gaconnecte.auxilium.service.mapper.RefModeReglementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RefModeReglement.
 */
@Service
@Transactional
public class RefModeReglementServiceImpl implements RefModeReglementService{

    private final Logger log = LoggerFactory.getLogger(RefModeReglementServiceImpl.class);

    private final RefModeReglementRepository refModeReglementRepository;

    private final RefModeReglementMapper refModeReglementMapper;

    private final RefModeReglementSearchRepository refModeReglementSearchRepository;

    public RefModeReglementServiceImpl(RefModeReglementRepository refModeReglementRepository, RefModeReglementMapper refModeReglementMapper, RefModeReglementSearchRepository refModeReglementSearchRepository) {
        this.refModeReglementRepository = refModeReglementRepository;
        this.refModeReglementMapper = refModeReglementMapper;
        this.refModeReglementSearchRepository = refModeReglementSearchRepository;
    }

    /**
     * Save a refModeReglement.
     *
     * @param refModeReglementDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefModeReglementDTO save(RefModeReglementDTO refModeReglementDTO) {
        log.debug("Request to save RefModeReglement : {}", refModeReglementDTO);
        RefModeReglement refModeReglement = refModeReglementMapper.toEntity(refModeReglementDTO);
        refModeReglement = refModeReglementRepository.save(refModeReglement);
        RefModeReglementDTO result = refModeReglementMapper.toDto(refModeReglement);
        refModeReglementSearchRepository.save(refModeReglement);
        return result;
    }

    /**
     *  Get all the refModeReglements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefModeReglementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefModeReglements");
        return refModeReglementRepository.findAll(pageable)
            .map(refModeReglementMapper::toDto);
    }

    /**
     *  Get one refModeReglement by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefModeReglementDTO findOne(Long id) {
        log.debug("Request to get RefModeReglement : {}", id);
        RefModeReglement refModeReglement = refModeReglementRepository.findOne(id);
        return refModeReglementMapper.toDto(refModeReglement);
    }

    /**
     *  Delete the  refModeReglement by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefModeReglement : {}", id);
        refModeReglementRepository.delete(id);
        refModeReglementSearchRepository.delete(id);
    }

    /**
     * Search for the refModeReglement corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefModeReglementDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefModeReglements for query {}", query);
        Page<RefModeReglement> result = refModeReglementSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refModeReglementMapper::toDto);
    }
}
