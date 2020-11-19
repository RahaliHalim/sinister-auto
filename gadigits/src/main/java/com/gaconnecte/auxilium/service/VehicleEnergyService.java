package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.VehicleEnergy;
import com.gaconnecte.auxilium.repository.VehicleEnergyRepository;
import com.gaconnecte.auxilium.repository.search.VehicleEnergySearchRepository;
import com.gaconnecte.auxilium.service.dto.VehicleEnergyDTO;
import com.gaconnecte.auxilium.service.mapper.VehicleEnergyMapper;
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
 * Service Implementation for managing VehicleEnergy.
 */
@Service
@Transactional
public class VehicleEnergyService {

    private final Logger log = LoggerFactory.getLogger(VehicleEnergyService.class);

    private final VehicleEnergyRepository vehicleEnergyRepository;

    private final VehicleEnergyMapper vehicleEnergyMapper;

    private final VehicleEnergySearchRepository vehicleEnergySearchRepository;

    public VehicleEnergyService(VehicleEnergyRepository vehicleEnergyRepository, VehicleEnergyMapper vehicleEnergyMapper, VehicleEnergySearchRepository vehicleEnergySearchRepository) {
        this.vehicleEnergyRepository = vehicleEnergyRepository;
        this.vehicleEnergyMapper = vehicleEnergyMapper;
        this.vehicleEnergySearchRepository = vehicleEnergySearchRepository;
    }

    /**
     * Save a vehicleEnergy.
     *
     * @param vehicleEnergyDTO the entity to save
     * @return the persisted entity
     */
    public VehicleEnergyDTO save(VehicleEnergyDTO vehicleEnergyDTO) {
        log.debug("Request to save VehicleEnergy : {}", vehicleEnergyDTO);
        VehicleEnergy vehicleEnergy = vehicleEnergyMapper.toEntity(vehicleEnergyDTO);
        vehicleEnergy = vehicleEnergyRepository.save(vehicleEnergy);
        VehicleEnergyDTO result = vehicleEnergyMapper.toDto(vehicleEnergy);
        vehicleEnergySearchRepository.save(vehicleEnergy);
        return result;
    }

    /**
     *  Get all the vehicleEnergies.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VehicleEnergyDTO> findAll() {
        log.debug("Request to get all VehicleEnergies");
        return vehicleEnergyRepository.findAll().stream()
            .map(vehicleEnergyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one vehicleEnergy by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public VehicleEnergyDTO findOne(Long id) {
        log.debug("Request to get VehicleEnergy : {}", id);
        VehicleEnergy vehicleEnergy = vehicleEnergyRepository.findOne(id);
        return vehicleEnergyMapper.toDto(vehicleEnergy);
    }

    /**
     *  Delete the  vehicleEnergy by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete VehicleEnergy : {}", id);
        vehicleEnergyRepository.delete(id);
        vehicleEnergySearchRepository.delete(id);
    }

    /**
     * Search for the vehicleEnergy corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VehicleEnergyDTO> search(String query) {
        log.debug("Request to search VehicleEnergies for query {}", query);
        return StreamSupport
            .stream(vehicleEnergySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(vehicleEnergyMapper::toDto)
            .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public VehicleEnergyDTO findByLabel(String label){
        
        VehicleEnergy vehicleEnergy = vehicleEnergyRepository.findByLabel(label.toLowerCase().replaceAll("\\s", ""));
        
        return vehicleEnergyMapper.toDto(vehicleEnergy);
    }
}
