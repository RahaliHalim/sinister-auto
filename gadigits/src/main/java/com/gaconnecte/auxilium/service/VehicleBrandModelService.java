package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.VehicleBrand;
import com.gaconnecte.auxilium.domain.VehicleBrandModel;
import com.gaconnecte.auxilium.repository.VehicleBrandModelRepository;
import com.gaconnecte.auxilium.repository.search.VehicleBrandModelSearchRepository;
import com.gaconnecte.auxilium.service.dto.VehicleBrandModelDTO;
import com.gaconnecte.auxilium.service.mapper.VehicleBrandModelMapper;
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
 * Service Implementation for managing VehicleBrandModel.
 */
@Service
@Transactional
public class VehicleBrandModelService {

    private final Logger log = LoggerFactory.getLogger(VehicleBrandModelService.class);

    private final VehicleBrandModelRepository vehicleBrandModelRepository;

    private final VehicleBrandModelMapper vehicleBrandModelMapper;

    private final VehicleBrandModelSearchRepository vehicleBrandModelSearchRepository;

    public VehicleBrandModelService(VehicleBrandModelRepository vehicleBrandModelRepository, VehicleBrandModelMapper vehicleBrandModelMapper, VehicleBrandModelSearchRepository vehicleBrandModelSearchRepository) {
        this.vehicleBrandModelRepository = vehicleBrandModelRepository;
        this.vehicleBrandModelMapper = vehicleBrandModelMapper;
        this.vehicleBrandModelSearchRepository = vehicleBrandModelSearchRepository;
    }

    /**
     * Save a vehicleBrandModel.
     *
     * @param vehicleBrandModelDTO the entity to save
     * @return the persisted entity
     */
    public VehicleBrandModelDTO save(VehicleBrandModelDTO vehicleBrandModelDTO) {
        log.debug("Request to save VehicleBrandModel : {}", vehicleBrandModelDTO);
        VehicleBrandModel vehicleBrandModel = vehicleBrandModelMapper.toEntity(vehicleBrandModelDTO);
        vehicleBrandModel = vehicleBrandModelRepository.save(vehicleBrandModel);
        VehicleBrandModelDTO result = vehicleBrandModelMapper.toDto(vehicleBrandModel);
        vehicleBrandModelSearchRepository.save(vehicleBrandModel);
        return result;
    }

    /**
     *  Get all the vehicleBrandModels.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VehicleBrandModelDTO> findAll() {
        log.debug("Request to get all VehicleBrandModels");
        return vehicleBrandModelRepository.findAll().stream()
            .map(vehicleBrandModelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the vehicleBrandModels.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VehicleBrandModelDTO> findAllByBrand(Long id) {
        log.debug("Request to get all VehicleBrandModels by brand {}", id);
        return vehicleBrandModelRepository.findAllByBrandOrderByLabelAsc(new VehicleBrand(id)).stream()
            .map(vehicleBrandModelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     *  Get one vehicleBrandModel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public VehicleBrandModelDTO findOne(Long id) {
        log.debug("Request to get VehicleBrandModel : {}", id);
        VehicleBrandModel vehicleBrandModel = vehicleBrandModelRepository.findOne(id);
        return vehicleBrandModelMapper.toDto(vehicleBrandModel);
    }

    /**
     *  Delete the  vehicleBrandModel by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete VehicleBrandModel : {}", id);
        vehicleBrandModelRepository.delete(id);
        vehicleBrandModelSearchRepository.delete(id);
    }

    /**
     * Search for the vehicleBrandModel corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VehicleBrandModelDTO> search(String query) {
        log.debug("Request to search VehicleBrandModels for query {}", query);
        return StreamSupport
            .stream(vehicleBrandModelSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(vehicleBrandModelMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VehicleBrandModelDTO findByLabel(String label){

        VehicleBrandModel vehicleBrandModel = vehicleBrandModelRepository.findByLabel(label.toLowerCase().replaceAll("\\s", ""));
        return vehicleBrandModelMapper.toDto(vehicleBrandModel);
    }
}
