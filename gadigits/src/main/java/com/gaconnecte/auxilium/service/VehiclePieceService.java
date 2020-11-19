package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.VehiclePiece;
import com.gaconnecte.auxilium.domain.VehiclePieceType;
import com.gaconnecte.auxilium.repository.VehiclePieceRepository;
import com.gaconnecte.auxilium.repository.ViewExtractPieceRepository;
import com.gaconnecte.auxilium.repository.search.VehiclePieceSearchRepository;
import com.gaconnecte.auxilium.service.dto.VehiclePieceDTO;
import com.gaconnecte.auxilium.service.dto.ViewExtractPieceDTO;
import com.gaconnecte.auxilium.service.mapper.VehiclePieceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.StringUtils;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing VehiclePiece.
 */
@Service
@Transactional
public class VehiclePieceService {

    private final Logger log = LoggerFactory.getLogger(VehiclePieceService.class);

    private final VehiclePieceRepository vehiclePieceRepository;

    private final VehiclePieceMapper vehiclePieceMapper;

    private final VehiclePieceSearchRepository vehiclePieceSearchRepository;

    public VehiclePieceService(VehiclePieceRepository vehiclePieceRepository, VehiclePieceMapper vehiclePieceMapper, VehiclePieceSearchRepository vehiclePieceSearchRepository ) {
        this.vehiclePieceRepository = vehiclePieceRepository;
        this.vehiclePieceMapper = vehiclePieceMapper;
        this.vehiclePieceSearchRepository = vehiclePieceSearchRepository;
    }

    /**
     * Save a vehiclePiece.
     *
     * @param vehiclePieceDTO the entity to save
     * @return the persisted entity
     */
    public VehiclePieceDTO save(VehiclePieceDTO vehiclePieceDTO) {
        log.debug("Request to save VehiclePiece : {}", vehiclePieceDTO);
        VehiclePiece vehiclePiece = vehiclePieceMapper.toEntity(vehiclePieceDTO);
        VehiclePiece vehiclePiece1 = vehiclePieceRepository.findVehiclePieceByReferenceAndType(vehiclePieceDTO.getCode(), vehiclePieceDTO.getTypeId());
        if(vehiclePiece1 != null) { // piece exist
            vehiclePiece.setId(vehiclePiece1.getId());
        }
        vehiclePiece.setLabel(vehiclePieceDTO.getLabel().toUpperCase());
        vehiclePiece.setCode(vehiclePieceDTO.getCode().toUpperCase());
        vehiclePiece.setReference(vehiclePieceDTO.getReference().toUpperCase());
        vehiclePiece = vehiclePieceRepository.save(vehiclePiece);
        VehiclePieceDTO result = vehiclePieceMapper.toDto(vehiclePiece);
        //vehiclePieceSearchRepository.save(vehiclePiece);
        return result;
    }

    /**
     *  Get all the vehiclePieces.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VehiclePieceDTO> findAll() {
        log.debug("Request to get all VehiclePieces");
        return vehiclePieceRepository.findAll().stream()
            .map(vehiclePieceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one vehiclePiece by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public VehiclePieceDTO findOne(Long id) {
        log.debug("Request to get VehiclePiece : {}", id);
        VehiclePiece vehiclePiece = vehiclePieceRepository.findOne(id);
        return vehiclePieceMapper.toDto(vehiclePiece);
    }

    /**
     *  Delete the  vehiclePiece by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete VehiclePiece : {}", id);
        vehiclePieceRepository.delete(id);
        vehiclePieceSearchRepository.delete(id);
    }

    /**
     * Search for the vehiclePiece corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VehiclePieceDTO> search(String query) {
        log.debug("Request to search VehiclePieces for query {}", query);
        return StreamSupport
            .stream(vehiclePieceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(vehiclePieceMapper::toDto)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
	public List<VehiclePieceDTO> findVehiclePieceByType(Long id) {
		log.debug("Request to get  all vehicle Pieces By TYpe", id);
		List<VehiclePiece> pieces = vehiclePieceRepository.findVehiclePiecesByType(id);
		return vehiclePieceMapper.toDto(pieces);
	}

    @Transactional(readOnly = true)
    public VehiclePiece findpiece(String reference, Long typeId) {
		log.debug("Request to get Vehicle Piece : {}", reference);
		VehiclePiece piece = vehiclePieceRepository.findPiece(reference, typeId);
		return piece;
	}

    @Transactional(readOnly = true)
    public VehiclePieceDTO findVehiclePieceByReferenceAndType(String reference, Long typeId) {
		log.debug("Request to get Vehicle Piece : {}", reference);
		List<VehiclePiece> piece = vehiclePieceRepository.findVehiclePieceByReferenceGTAndType(reference, typeId);
		VehiclePiece p = piece.get(0);
		return vehiclePieceMapper.toDto(p);
	}

    @Transactional(readOnly = true)
    public VehiclePieceDTO findVehiclePieceByDesignationAndType(String designation, Long typeId) {
		log.debug("Request to get Vehicle Piece : {}", designation);
		VehiclePiece piece = vehiclePieceRepository.findVehiclePieceByDesignationAndType(designation, typeId);
		return vehiclePieceMapper.toDto(piece);
	}

    public VehiclePiece findOrCreateVehiclePieceByReferenceAndType(String code, String designation, String reference, Long typeId) {
		log.debug("Request to get Vehicle Piece : {}", code);
		VehiclePiece piece = vehiclePieceRepository.findVehiclePieceByReferenceAndType(code, typeId);
                if(piece == null) { // create it
                    piece = new VehiclePiece();
                    piece.setActive(Boolean.TRUE);
                    piece.setCode(code.toUpperCase());
                    piece.setReference(reference.toUpperCase());
                    piece.setLabel(designation.toUpperCase());
                    piece.setType(new VehiclePieceType(typeId));
                    piece.setSource("GT ESTIMATE");
                    piece  = vehiclePieceRepository.save(piece);
                }else {
                	if(!code.equals(reference)) {
                        piece.setReference(reference.toUpperCase());
                        piece.setLabel(designation.toUpperCase());
                		piece  = vehiclePieceRepository.save(piece);
                	}
                }
		return piece;
	}

 
}
