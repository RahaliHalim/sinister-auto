/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.Utils.Constants;
import com.gaconnecte.auxilium.repository.ViewVehiclePiecesRepository;
import com.gaconnecte.auxilium.service.dto.ViewVehiclePiecesDTO;
import com.gaconnecte.auxilium.service.mapper.ViewVehiclePiecesMapper;
import com.gaconnecte.auxilium.domain.view.ViewVehiclePieces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing view VehiclePieces.
 */
@Service
@Transactional
public class ViewVehiclePiecesService {

    private final Logger log = LoggerFactory.getLogger(ViewVehiclePiecesService.class);

    private final ViewVehiclePiecesRepository viewVehiclePiecesRepository;
    private final ViewVehiclePiecesMapper viewVehiclePiecesMapper;

    public ViewVehiclePiecesService(ViewVehiclePiecesRepository viewVehiclePiecesRepository, ViewVehiclePiecesMapper viewVehiclePiecesMapper) {
        this.viewVehiclePiecesRepository = viewVehiclePiecesRepository;
        this.viewVehiclePiecesMapper = viewVehiclePiecesMapper;
        
    }

    /**
     * Get all the vehicle pieces .
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ViewVehiclePiecesDTO> findAll() {
        log.debug("Request to get all view vehicle pieces.");
        return viewVehiclePiecesRepository.findAll().stream()
                .map(viewVehiclePiecesMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
	public List<ViewVehiclePiecesDTO> findVehiclePieceByType(Long id) {
		log.debug("Request to get  all view vehicle Pieces By TYpe", id);
		List<ViewVehiclePieces> pieces = viewVehiclePiecesRepository.findVehiclePiecesByType(id);
		return viewVehiclePiecesMapper.toDto(pieces);
	}

    @Transactional(readOnly = true)
	public ViewVehiclePiecesDTO getVehiclePiecesByTypeAndReference(String reference, Long id) {
		log.debug("Request to get  all view vehicle Pieces By TYpe and reference", id);
		if(reference != null) {
			ViewVehiclePieces pieces = viewVehiclePiecesRepository.getVehiclePiecesByTypeAndReference(reference.toUpperCase(), id);
			return viewVehiclePiecesMapper.toDto(pieces);
		}
		return new ViewVehiclePiecesDTO();
    }
    
    @Transactional(readOnly = true)
	public ViewVehiclePiecesDTO getVehiclePiecesByTypeAndCode(String code, Long id) {
		log.debug("Request to get  all view vehicle Pieces By TYpe and code", id);
		ViewVehiclePieces pieces = viewVehiclePiecesRepository.getVehiclePiecesByTypeAndCode(code.toUpperCase(), id);
		return viewVehiclePiecesMapper.toDto(pieces);
	}

    
    @Transactional(readOnly = true)
	public List<ViewVehiclePiecesDTO> getVehiclePiecesByTypeAndTapedReference(String reference, Long id) {
		log.debug("Request to get  all view vehicle Pieces taped By TYpe and reference", id);
		if(reference != null) {
			List<ViewVehiclePieces> pieces = viewVehiclePiecesRepository.getVehiclePiecesByTypeAndTapedReference(reference.toUpperCase(), id);
			return viewVehiclePiecesMapper.toDto(pieces);
		}
		return new ArrayList<>();
	}
    @Transactional(readOnly = true)
	public List<ViewVehiclePiecesDTO> getVehiclePiecesByTypeAndTapedDesignation(String designation, Long id) {
		log.debug("Request to get  all view vehicle Pieces taped By TYpe and designation", id);
		if(designation != null) {
			List<ViewVehiclePieces> pieces = viewVehiclePiecesRepository.getVehiclePiecesByTypeAndTapedDesignation(designation.toUpperCase(), id);
			return viewVehiclePiecesMapper.toDto(pieces);
		}
		return new ArrayList<>();
	}

    @Transactional(readOnly = true)
	public ViewVehiclePiecesDTO getVehiclePiecesByTypeAndDesignation(String designation, Long id) {
		log.debug("Request to get  all view vehicle Pieces By TYpe and designation", id);
		if(designation != null) {
			ViewVehiclePieces pieces = viewVehiclePiecesRepository.getVehiclePiecesByTypeAndDesignation(designation.toUpperCase(), id);
			return viewVehiclePiecesMapper.toDto(pieces);
		}
		return new ViewVehiclePiecesDTO();
	}
    
    @Transactional(readOnly = true)
	public ViewVehiclePiecesDTO getVehiclePiecesByTypeAndDesignationAutoComplete(String designation, Long id) {
		log.debug("Request to get  all view vehicle Pieces By TYpe and designation", id);
		if(designation != null) {
			List<ViewVehiclePieces> pieces = viewVehiclePiecesRepository.getVehiclePiecesByTypeAndDesignationAutoComplete(designation.toUpperCase(), id);
			ViewVehiclePieces p = pieces.get(0);
			return viewVehiclePiecesMapper.toDto(p);
		}
		return new ViewVehiclePiecesDTO();
	}
    
    @Transactional(readOnly = true)
	public ViewVehiclePiecesDTO getVehiclePiecesByTypeAndReferenceAutoComplete(String reference, Long id) {
		log.debug("Request to get  all view vehicle Pieces By TYpe and reference", id);
		if(reference != null) {
			List<ViewVehiclePieces> pieces = viewVehiclePiecesRepository.getVehiclePiecesByTypeAndReferenceRef(reference.toUpperCase(), id);
			ViewVehiclePieces p = pieces.get(0);
			return viewVehiclePiecesMapper.toDto(p);
		}
		return new ViewVehiclePiecesDTO();
    }
    
    @Transactional(readOnly = true)
	public Boolean getVehiclePiecesByTypeAndDesignationIsPresent(String designation, Long id) {
		log.debug("Request to get  all view vehicle Pieces By TYpe and designation", id);
		if(designation != null) {
			List<ViewVehiclePieces> pieces = viewVehiclePiecesRepository.getVehiclePiecesByTypeAndDesignationAutoComplete(designation.toUpperCase(), id);
			if(pieces.size() > 1) return false;
			return true;
		}
		return true;
	}
    
    @Transactional(readOnly = true)
    public Page<ViewVehiclePiecesDTO> findAllVehiculePieces(Pageable pageable) {
        log.debug("Request to get all view vehicle pieces.");
        return viewVehiclePiecesRepository.findAll(pageable)
                .map(viewVehiclePiecesMapper::toDto);
    }

}
