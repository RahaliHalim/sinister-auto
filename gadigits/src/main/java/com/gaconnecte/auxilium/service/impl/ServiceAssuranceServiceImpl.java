package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.ServiceAssuranceService;
import com.gaconnecte.auxilium.domain.ServiceAssurance;
import com.gaconnecte.auxilium.repository.ServiceAssuranceRepository;
import com.gaconnecte.auxilium.repository.search.ServiceAssuranceSearchRepository;
import com.gaconnecte.auxilium.service.dto.ServiceAssuranceDTO;
import com.gaconnecte.auxilium.service.mapper.ServiceAssuranceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ServiceAssurance.
 */
@Service
@Transactional
public class ServiceAssuranceServiceImpl implements ServiceAssuranceService{

    private final Logger log = LoggerFactory.getLogger(ServiceAssuranceServiceImpl.class);

    private final ServiceAssuranceRepository serviceAssuranceRepository;

    private final ServiceAssuranceMapper serviceAssuranceMapper;

    private final ServiceAssuranceSearchRepository serviceAssuranceSearchRepository;

    public ServiceAssuranceServiceImpl(ServiceAssuranceRepository serviceAssuranceRepository, ServiceAssuranceMapper serviceAssuranceMapper, ServiceAssuranceSearchRepository serviceAssuranceSearchRepository) {
        this.serviceAssuranceRepository = serviceAssuranceRepository;
        this.serviceAssuranceMapper = serviceAssuranceMapper;
        this.serviceAssuranceSearchRepository = serviceAssuranceSearchRepository;
    }

    /**import com.gaconnecte.auxilium.repository.search.ServiceAssuranceSearchRepository;
     * Save a serviceAssurance.
     *
     * @param serviceAssuranceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ServiceAssuranceDTO save(ServiceAssuranceDTO serviceAssuranceDTO) {
        log.debug("Request to save ServiceAssurance : {}", serviceAssuranceDTO);
        ServiceAssurance serviceAssurance = serviceAssuranceMapper.toEntity(serviceAssuranceDTO);
        serviceAssurance = serviceAssuranceRepository.save(serviceAssurance);
        ServiceAssuranceDTO result = serviceAssuranceMapper.toDto(serviceAssurance);
        serviceAssuranceSearchRepository.save(serviceAssurance);
        return result;
    }

    /**
     *  Get all the serviceAssurances.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceAssuranceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceAssurances");
        return serviceAssuranceRepository.findAll(pageable)
            .map(serviceAssuranceMapper::toDto);
    }

    /**
     *  Get one serviceAssurance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ServiceAssuranceDTO findOne(Long id) {
        log.debug("Request to get ServiceAssurance : {}", id);
        ServiceAssurance serviceAssurance = serviceAssuranceRepository.findOne(id);
        return serviceAssuranceMapper.toDto(serviceAssurance);
    }

    /**
     *  Delete the  serviceAssurance by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceAssurance : {}", id);
        serviceAssuranceRepository.delete(id);
        serviceAssuranceSearchRepository.delete(id);
    }

    /**
     * Search for the serviceAssurance corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceAssuranceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ServiceAssurances for query {}", query);
        Page<ServiceAssurance> result = serviceAssuranceSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(serviceAssuranceMapper::toDto);
    }
}
