package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefMaterielService;
import com.gaconnecte.auxilium.domain.RefMateriel;
import com.gaconnecte.auxilium.repository.RefMaterielRepository;
import com.gaconnecte.auxilium.repository.search.RefMaterielSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefMaterielDTO;
import com.gaconnecte.auxilium.service.mapper.RefMaterielMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RefMateriel.
 */
@Service
@Transactional
public class RefMaterielServiceImpl implements RefMaterielService{

    private final Logger log = LoggerFactory.getLogger(RefMaterielServiceImpl.class);

    private final RefMaterielRepository refMaterielRepository;

    private final RefMaterielMapper refMaterielMapper;

    private final RefMaterielSearchRepository refMaterielSearchRepository;

    public RefMaterielServiceImpl(RefMaterielRepository refMaterielRepository, RefMaterielMapper refMaterielMapper, RefMaterielSearchRepository refMaterielSearchRepository) {
        this.refMaterielRepository = refMaterielRepository;
        this.refMaterielMapper = refMaterielMapper;
        this.refMaterielSearchRepository = refMaterielSearchRepository;
    }

    /**
     * Save a refMateriel.
     *
     * @param refMaterielDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefMaterielDTO save(RefMaterielDTO refMaterielDTO) {
        log.debug("Request to save RefMateriel : {}", refMaterielDTO);
        RefMateriel refMateriel = refMaterielMapper.toEntity(refMaterielDTO);
        refMateriel = refMaterielRepository.save(refMateriel);
        RefMaterielDTO result = refMaterielMapper.toDto(refMateriel);
        refMaterielSearchRepository.save(refMateriel);
        return result;
    }

    /**
     *  Get all the refMateriels.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefMaterielDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefMateriels");
        return refMaterielRepository.findAll(pageable)
            .map(refMaterielMapper::toDto);
    }

    /**
     *  Get one refMateriel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefMaterielDTO findOne(Long id) {
        log.debug("Request to get RefMateriel : {}", id);
        RefMateriel refMateriel = refMaterielRepository.findOne(id);
        return refMaterielMapper.toDto(refMateriel);
    }

    /**
     *  Delete the  refMateriel by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefMateriel : {}", id);
        refMaterielRepository.delete(id);
        refMaterielSearchRepository.delete(id);
    }

    /**
     * Search for the refMateriel corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefMaterielDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefMateriels for query {}", query);
        Page<RefMateriel> result = refMaterielSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refMaterielMapper::toDto);
    }
}
