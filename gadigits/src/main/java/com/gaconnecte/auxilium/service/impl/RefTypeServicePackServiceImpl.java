package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefTypeServicePackService;
import com.gaconnecte.auxilium.domain.RefTugTruck;
import com.gaconnecte.auxilium.domain.RefTypeServicePack;
import com.gaconnecte.auxilium.repository.RefTypeServicePackRepository;
import com.gaconnecte.auxilium.repository.search.RefTypeServicePackSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefTugTruckDTO;
import com.gaconnecte.auxilium.service.dto.RefTypeServicePackDTO;
import com.gaconnecte.auxilium.service.mapper.RefTypeServicePackMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RefTypeService.
 */
@Service
@Transactional
public class RefTypeServicePackServiceImpl implements RefTypeServicePackService{

    private final Logger log = LoggerFactory.getLogger(RefTypeServicePackServiceImpl.class);

    private final RefTypeServicePackRepository refTypeServicePackRepository;

    private final RefTypeServicePackMapper refTypeServicePackMapper;

    private final RefTypeServicePackSearchRepository refTypeServicePackSearchRepository;

    public RefTypeServicePackServiceImpl(RefTypeServicePackRepository refTypeServicePackRepository, RefTypeServicePackMapper refTypeServicePackMapper, RefTypeServicePackSearchRepository refTypeServicePackSearchRepository) {
        this.refTypeServicePackRepository = refTypeServicePackRepository;
        this.refTypeServicePackMapper = refTypeServicePackMapper;
        this.refTypeServicePackSearchRepository = refTypeServicePackSearchRepository;
    }

    /**
     * Save a refTypeService.
     *
     * @param refTypeServiceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefTypeServicePackDTO save(RefTypeServicePackDTO refTypeServicePackDTO) {
        log.debug("Request to save RefTypeServicePack : {}", refTypeServicePackDTO);
        RefTypeServicePack refTypeServicePack = refTypeServicePackMapper.toEntity(refTypeServicePackDTO);
        refTypeServicePack = refTypeServicePackRepository.save(refTypeServicePack);
        RefTypeServicePackDTO result = refTypeServicePackMapper.toDto(refTypeServicePack);
        refTypeServicePackSearchRepository.save(refTypeServicePack);
        return result;
    }

  
    /**
     *  Get all the refTypeServices.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefTypeServicePackDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefTypeServicePacks");
        return refTypeServicePackRepository.findAll(pageable)
            .map(refTypeServicePackMapper::toDto);
    }

    /**
     *  Get one refTypeService by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefTypeServicePackDTO findOne(Long id) {
        log.debug("Request to get RefTypeServicePack : {}", id);
        RefTypeServicePack refTypeServicePack = refTypeServicePackRepository.findOne(id);
        return refTypeServicePackMapper.toDto(refTypeServicePack);
    }
    /**
     *  Get one refTypeService by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefTypeServicePackDTO findByTypeService(Long id) {
        log.debug("Request to get refTypeServicePacks : {}", id);
        RefTypeServicePack refTypeServicePack = refTypeServicePackRepository.findPacksByTypeService(id);
        return refTypeServicePackMapper.toDto(refTypeServicePack);
    }
    
 
    /**
     *  Delete the  refTypeService by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefTypeServicePack : {}", id);
        refTypeServicePackRepository.delete(id);
        refTypeServicePackSearchRepository.delete(id);
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public Page<RefTypeServicePackDTO> findPacksByRefTypeService(Pageable pageable, Long serviceId) {
        log.debug("Request to get RefTypeServicePacks : {}", serviceId);
        Page<RefTypeServicePack> refTypeServicePacks = refTypeServicePackRepository.findPacksByRefTypeService(pageable, serviceId);
        return refTypeServicePacks.map(refTypeServicePackMapper::toDto);
         
    }
    /**
     * Search for the refTypeService corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefTypeServicePackDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefTypeServicePacks for query {}", query);
        Page<RefTypeServicePack> result = refTypeServicePackSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refTypeServicePackMapper::toDto);
    }
}
