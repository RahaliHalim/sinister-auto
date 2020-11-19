package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.VehicleBrand;
import com.gaconnecte.auxilium.repository.VehicleBrandRepository;
import com.gaconnecte.auxilium.repository.search.VehicleBrandSearchRepository;
import com.gaconnecte.auxilium.service.dto.VehicleBrandDTO;
import com.gaconnecte.auxilium.service.mapper.VehicleBrandMapper;
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
 * Service Implementation for managing VehicleBrand.
 */
@Service
@Transactional
public class VehicleBrandService {

    private final Logger log = LoggerFactory.getLogger(VehicleBrandService.class);

    private final VehicleBrandRepository vehicleBrandRepository;

    private final VehicleBrandMapper vehicleBrandMapper;

    private final VehicleBrandSearchRepository vehicleBrandSearchRepository;

    public VehicleBrandService(VehicleBrandRepository vehicleBrandRepository, VehicleBrandMapper vehicleBrandMapper, VehicleBrandSearchRepository vehicleBrandSearchRepository) {
        this.vehicleBrandRepository = vehicleBrandRepository;
        this.vehicleBrandMapper = vehicleBrandMapper;
        this.vehicleBrandSearchRepository = vehicleBrandSearchRepository;
    }

    /**
     * Save a vehicleBrand.
     *
     * @param vehicleBrandDTO the entity to save
     * @return the persisted entity
     */
    public VehicleBrandDTO save(VehicleBrandDTO vehicleBrandDTO) {
        log.debug("Request to save VehicleBrand : {}", vehicleBrandDTO);
        VehicleBrand vehicleBrand = vehicleBrandMapper.toEntity(vehicleBrandDTO);
        vehicleBrand = vehicleBrandRepository.save(vehicleBrand);
        VehicleBrandDTO result = vehicleBrandMapper.toDto(vehicleBrand);
        vehicleBrandSearchRepository.save(vehicleBrand);
        return result;
    }

    /**
     *  Get all the vehicleBrands.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VehicleBrandDTO> findAll() {
        log.debug("Request to get all VehicleBrands");
        return vehicleBrandRepository.findAll().stream()
            .map(vehicleBrandMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one vehicleBrand by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public VehicleBrandDTO findOne(Long id) {
        log.debug("Request to get VehicleBrand : {}", id);
        VehicleBrand vehicleBrand = vehicleBrandRepository.findOne(id);
        return vehicleBrandMapper.toDto(vehicleBrand);
    }

    /**
     *  Delete the  vehicleBrand by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete VehicleBrand : {}", id);
        vehicleBrandRepository.delete(id);
        vehicleBrandSearchRepository.delete(id);
    }

    /**
     * Search for the vehicleBrand corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VehicleBrandDTO> search(String query) {
        log.debug("Request to search VehicleBrands for query {}", query);
        return StreamSupport
            .stream(vehicleBrandSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(vehicleBrandMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VehicleBrandDTO findByLabel(String label){

        VehicleBrand vehicleBrand = vehicleBrandRepository.findByLabel(label.toLowerCase().replaceAll("\\s", ""));
        return vehicleBrandMapper.toDto(vehicleBrand);
    }
}
