package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.Vehicle;
import com.gaconnecte.auxilium.repository.VehicleRepository;
import com.gaconnecte.auxilium.repository.search.VehicleSearchRepository;
import com.gaconnecte.auxilium.service.dto.VehicleDTO;
import com.gaconnecte.auxilium.service.mapper.VehicleMapper;
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
 * Service Implementation for managing Vehicle.
 */
@Service
@Transactional
public class VehicleService {

    private final Logger log = LoggerFactory.getLogger(VehicleService.class);

    private final VehicleRepository vehicleRepository;

    private final VehicleMapper vehicleMapper;

    private final VehicleSearchRepository vehicleSearchRepository;

    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper, VehicleSearchRepository vehicleSearchRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
        this.vehicleSearchRepository = vehicleSearchRepository;
    }

    /**
     * Save a vehicle.
     *
     * @param vehicleDTO the entity to save
     * @return the persisted entity
     */
    public VehicleDTO save(VehicleDTO vehicleDTO) {
        log.debug("Request to save Vehicle : {}", vehicleDTO);
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
        vehicle = vehicleRepository.save(vehicle);
        VehicleDTO result = vehicleMapper.toDto(vehicle);
        vehicleSearchRepository.save(vehicle);
        return result;
    }

    /**
     *  Get all the vehicles.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VehicleDTO> findAll() {
        log.debug("Request to get all Vehicles");
        return vehicleRepository.findAll().stream()
            .map(vehicleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one vehicle by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public VehicleDTO findOne(Long id) {
        log.debug("Request to get Vehicle : {}", id);
        Vehicle vehicle = vehicleRepository.findOne(id);
        return vehicleMapper.toDto(vehicle);
    }

    /**
     *  Delete the  vehicle by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Vehicle : {}", id);
        vehicleRepository.delete(id);
        vehicleSearchRepository.delete(id);
    }

    /**
     * Search for the vehicle corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VehicleDTO> search(String query) {
        log.debug("Request to search Vehicles for query {}", query);
        return StreamSupport
            .stream(vehicleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(vehicleMapper::toDto)
            .collect(Collectors.toList());
    }
}
