package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefNatureExpertiseService;
import com.gaconnecte.auxilium.domain.RefNatureExpertise;
import com.gaconnecte.auxilium.repository.RefNatureExpertiseRepository;
import com.gaconnecte.auxilium.repository.search.RefNatureExpertiseSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefNatureExpertiseDTO;
import com.gaconnecte.auxilium.service.mapper.RefNatureExpertiseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RefNatureExpertise.
 */
@Service
@Transactional
public class RefNatureExpertiseServiceImpl implements RefNatureExpertiseService{

    private final Logger log = LoggerFactory.getLogger(RefNatureExpertiseServiceImpl.class);

    private final RefNatureExpertiseRepository refNatureExpertiseRepository;

    private final RefNatureExpertiseMapper refNatureExpertiseMapper;

    private final RefNatureExpertiseSearchRepository refNatureExpertiseSearchRepository;

    public RefNatureExpertiseServiceImpl(RefNatureExpertiseRepository refNatureExpertiseRepository, RefNatureExpertiseMapper refNatureExpertiseMapper, RefNatureExpertiseSearchRepository refNatureExpertiseSearchRepository) {
        this.refNatureExpertiseRepository = refNatureExpertiseRepository;
        this.refNatureExpertiseMapper = refNatureExpertiseMapper;
        this.refNatureExpertiseSearchRepository = refNatureExpertiseSearchRepository;
    }

    /**
     * Save a refNatureExpertise.
     *
     * @param refNatureExpertiseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefNatureExpertiseDTO save(RefNatureExpertiseDTO refNatureExpertiseDTO) {
        log.debug("Request to save RefNatureExpertise : {}", refNatureExpertiseDTO);
        RefNatureExpertise refNatureExpertise = refNatureExpertiseMapper.toEntity(refNatureExpertiseDTO);
        refNatureExpertise = refNatureExpertiseRepository.save(refNatureExpertise);
        RefNatureExpertiseDTO result = refNatureExpertiseMapper.toDto(refNatureExpertise);
        refNatureExpertiseSearchRepository.save(refNatureExpertise);
        return result;
    }

    /**
     *  Get all the refNatureExpertises.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefNatureExpertiseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefNatureExpertises");
        return refNatureExpertiseRepository.findAll(pageable)
            .map(refNatureExpertiseMapper::toDto);
    }

    /**
     *  Get one refNatureExpertise by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefNatureExpertiseDTO findOne(Long id) {
        log.debug("Request to get RefNatureExpertise : {}", id);
        RefNatureExpertise refNatureExpertise = refNatureExpertiseRepository.findOne(id);
        return refNatureExpertiseMapper.toDto(refNatureExpertise);
    }

    /**
     *  Delete the  refNatureExpertise by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefNatureExpertise : {}", id);
        refNatureExpertiseRepository.delete(id);
        refNatureExpertiseSearchRepository.delete(id);
    }

    /**
     * Search for the refNatureExpertise corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefNatureExpertiseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefNatureExpertises for query {}", query);
        Page<RefNatureExpertise> result = refNatureExpertiseSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refNatureExpertiseMapper::toDto);
    }
}
