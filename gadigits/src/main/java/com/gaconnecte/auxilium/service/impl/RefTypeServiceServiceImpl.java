package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefTypeServiceService;
import com.gaconnecte.auxilium.domain.RefTypeService;
import com.gaconnecte.auxilium.repository.RefTypeServiceRepository;
import com.gaconnecte.auxilium.repository.search.RefTypeServiceSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefTypeServiceDTO;
import com.gaconnecte.auxilium.service.mapper.RefTypeServiceMapper;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
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
public class RefTypeServiceServiceImpl implements RefTypeServiceService{

    private final Logger log = LoggerFactory.getLogger(RefTypeServiceServiceImpl.class);

    private final RefTypeServiceRepository refTypeServiceRepository;

    private final RefTypeServiceMapper refTypeServiceMapper;

    private final RefTypeServiceSearchRepository refTypeServiceSearchRepository;

    public RefTypeServiceServiceImpl(RefTypeServiceRepository refTypeServiceRepository, RefTypeServiceMapper refTypeServiceMapper, RefTypeServiceSearchRepository refTypeServiceSearchRepository) {
        this.refTypeServiceRepository = refTypeServiceRepository;
        this.refTypeServiceMapper = refTypeServiceMapper;
        this.refTypeServiceSearchRepository = refTypeServiceSearchRepository;
    }

    /**
     * Save a refTypeService.
     *
     * @param refTypeServiceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefTypeServiceDTO save(RefTypeServiceDTO refTypeServiceDTO) {
        log.debug("Request to save RefTypeService : {}", refTypeServiceDTO);
        RefTypeService refTypeService = refTypeServiceMapper.toEntity(refTypeServiceDTO);
        refTypeService = refTypeServiceRepository.save(refTypeService);
        RefTypeServiceDTO result = refTypeServiceMapper.toDto(refTypeService);
        //refTypeServiceSearchRepository.save(refTypeService);
        return result;
    }

    /**
     *  Get all the refTypeServices.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<RefTypeServiceDTO> findAll() {
        log.debug("Request to get all RefTypeServices");
        Set<RefTypeService> serviceTypes = refTypeServiceRepository.findAllServiceTypes();
        if (CollectionUtils.isNotEmpty(serviceTypes)) {
            return serviceTypes.stream().map(refTypeServiceMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    /**
     *  Get all the refTypeServices.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<RefTypeServiceDTO> findAllActive() {
        log.debug("Request to get all RefTypeServices");
        Set<RefTypeService> serviceTypes = refTypeServiceRepository.findAllByActiveOrderByIdAsc(true);
        if (CollectionUtils.isNotEmpty(serviceTypes)) {
            return serviceTypes.stream().map(refTypeServiceMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }
    
    /**
     *  Get one refTypeService by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefTypeServiceDTO findOne(Long id) {
        log.debug("Request to get RefTypeService : {}", id);
        RefTypeService refTypeService = refTypeServiceRepository.findOne(id);
        return refTypeServiceMapper.toDto(refTypeService);
    }

    /**
     *  Delete the  refTypeService by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefTypeService : {}", id);
        refTypeServiceRepository.delete(id);
        refTypeServiceSearchRepository.delete(id);
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
    public Page<RefTypeServiceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefTypeServices for query {}", query);
        Page<RefTypeService> result = refTypeServiceSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refTypeServiceMapper::toDto);
    }
}
