package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.VehiclePieceType;
import com.gaconnecte.auxilium.repository.VehiclePieceTypeRepository;
import com.gaconnecte.auxilium.repository.search.VehiclePieceTypeSearchRepository;
import com.gaconnecte.auxilium.service.dto.VehiclePieceTypeDTO;
import com.gaconnecte.auxilium.service.mapper.VehiclePieceTypeMapper;
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
 * Service Implementation for managing VehiclePieceType.
 */
@Service
@Transactional
public class VehiclePieceTypeService {

    private final Logger log = LoggerFactory.getLogger(VehiclePieceTypeService.class);

    private final VehiclePieceTypeRepository vehiclePieceTypeRepository;

    private final VehiclePieceTypeMapper vehiclePieceTypeMapper;

    private final VehiclePieceTypeSearchRepository vehiclePieceTypeSearchRepository;

    public VehiclePieceTypeService(VehiclePieceTypeRepository vehiclePieceTypeRepository, VehiclePieceTypeMapper vehiclePieceTypeMapper, VehiclePieceTypeSearchRepository vehiclePieceTypeSearchRepository) {
        this.vehiclePieceTypeRepository = vehiclePieceTypeRepository;
        this.vehiclePieceTypeMapper = vehiclePieceTypeMapper;
        this.vehiclePieceTypeSearchRepository = vehiclePieceTypeSearchRepository;
    }

    /**
     * Save a vehiclePieceType.
     *
     * @param vehiclePieceTypeDTO the entity to save
     * @return the persisted entity
     */
    public VehiclePieceTypeDTO save(VehiclePieceTypeDTO vehiclePieceTypeDTO) {
        log.debug("Request to save VehiclePieceType : {}", vehiclePieceTypeDTO);
        VehiclePieceType vehiclePieceType = vehiclePieceTypeMapper.toEntity(vehiclePieceTypeDTO);
        vehiclePieceType = vehiclePieceTypeRepository.save(vehiclePieceType);
        VehiclePieceTypeDTO result = vehiclePieceTypeMapper.toDto(vehiclePieceType);
        vehiclePieceTypeSearchRepository.save(vehiclePieceType);
        return result;
    }

    /**
     *  Get all the vehiclePieceTypes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VehiclePieceTypeDTO> findAll() {
        log.debug("Request to get all VehiclePieceTypes");
        return vehiclePieceTypeRepository.findAll().stream()
            .map(vehiclePieceTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one vehiclePieceType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public VehiclePieceTypeDTO findOne(Long id) {
        log.debug("Request to get VehiclePieceType : {}", id);
        VehiclePieceType vehiclePieceType = vehiclePieceTypeRepository.findOne(id);
        return vehiclePieceTypeMapper.toDto(vehiclePieceType);
    }

    /**
     *  Delete the  vehiclePieceType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete VehiclePieceType : {}", id);
        vehiclePieceTypeRepository.delete(id);
        vehiclePieceTypeSearchRepository.delete(id);
    }

    /**
     * Search for the vehiclePieceType corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VehiclePieceTypeDTO> search(String query) {
        log.debug("Request to search VehiclePieceTypes for query {}", query);
        return StreamSupport
            .stream(vehiclePieceTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(vehiclePieceTypeMapper::toDto)
            .collect(Collectors.toList());
    }
}
