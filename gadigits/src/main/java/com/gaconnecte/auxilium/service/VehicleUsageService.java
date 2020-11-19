package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.VehicleUsage;
import com.gaconnecte.auxilium.repository.VehicleUsageRepository;
import com.gaconnecte.auxilium.repository.search.VehicleUsageSearchRepository;
import com.gaconnecte.auxilium.service.dto.VehicleUsageDTO;
import com.gaconnecte.auxilium.service.mapper.VehicleUsageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing VehicleUsage.
 */
@Service
@Transactional
public class VehicleUsageService {

    private final Logger log = LoggerFactory.getLogger(VehicleUsageService.class);

    private final VehicleUsageRepository vehicleUsageRepository;

    private final VehicleUsageMapper vehicleUsageMapper;

    private final VehicleUsageSearchRepository vehicleUsageSearchRepository;

    public VehicleUsageService(VehicleUsageRepository vehicleUsageRepository, VehicleUsageMapper vehicleUsageMapper, VehicleUsageSearchRepository vehicleUsageSearchRepository) {
        this.vehicleUsageRepository = vehicleUsageRepository;
        this.vehicleUsageMapper = vehicleUsageMapper;
        this.vehicleUsageSearchRepository = vehicleUsageSearchRepository;
    }

    /**
     * Save a vehicleUsage.
     *
     * @param vehicleUsageDTO the entity to save
     * @return the persisted entity
     */
    public VehicleUsageDTO save(VehicleUsageDTO vehicleUsageDTO) {
        log.debug("Request to save VehicleUsage : {}", vehicleUsageDTO);
        VehicleUsage vehicleUsage = vehicleUsageMapper.toEntity(vehicleUsageDTO);
        vehicleUsage = vehicleUsageRepository.save(vehicleUsage);
        VehicleUsageDTO result = vehicleUsageMapper.toDto(vehicleUsage);
        vehicleUsageSearchRepository.save(vehicleUsage);
        return result;
    }

    /**
     *  Get all the vehicleUsages.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VehicleUsageDTO> findAll() {
        log.debug("Request to get all VehicleUsages");
        return vehicleUsageRepository.findAll().stream()
            .map(vehicleUsageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one vehicleUsage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public VehicleUsageDTO findOne(Long id) {
        log.debug("Request to get VehicleUsage : {}", id);
        VehicleUsage vehicleUsage = vehicleUsageRepository.findOne(id);
        return vehicleUsageMapper.toDto(vehicleUsage);
    }

    /**
     *  Delete the  vehicleUsage by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete VehicleUsage : {}", id);
        vehicleUsageRepository.delete(id);
        vehicleUsageSearchRepository.delete(id);
    }

    /**
     * Search for the vehicleUsage corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VehicleUsageDTO> search(String query) {
        log.debug("Request to search VehicleUsages for query {}", query);
        return StreamSupport
            .stream(vehicleUsageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(vehicleUsageMapper::toDto)
            .collect(Collectors.toList());
    }
}
