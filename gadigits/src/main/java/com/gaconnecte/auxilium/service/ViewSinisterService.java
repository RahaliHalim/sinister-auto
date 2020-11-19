/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.Utils.Constants;
import com.gaconnecte.auxilium.domain.view.ViewSinisterPrestation;
import com.gaconnecte.auxilium.repository.ViewSinisterPrestationRepository;
import com.gaconnecte.auxilium.repository.ViewSinisterRepository;
import com.gaconnecte.auxilium.service.dto.ViewPolicyDTO;
import com.gaconnecte.auxilium.service.dto.ViewSinisterDTO;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPrestationDTO;
import com.gaconnecte.auxilium.service.mapper.ViewSinisterMapper;
import com.gaconnecte.auxilium.service.mapper.ViewSinisterPrestationMapper;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hannibaal
 */
/**
 * Service Implementation for managing view sinister.
 */
@Service
@Transactional
public class ViewSinisterService {

    private final Logger log = LoggerFactory.getLogger(ViewSinisterService.class);

    private final ViewSinisterRepository viewSinisterRepository;
    private final ViewSinisterMapper viewSinisterMapper;
    private final ViewSinisterPrestationRepository viewSinisterPrestationRepository;
    private final ViewSinisterPrestationMapper viewSinisterPrestationMapper;

    public ViewSinisterService(ViewSinisterRepository viewSinisterRepository, ViewSinisterPrestationRepository viewSinisterPrestationRepository, 
            ViewSinisterMapper viewSinisterMapper, ViewSinisterPrestationMapper viewSinisterPrestationMapper) {
        this.viewSinisterRepository = viewSinisterRepository;
        this.viewSinisterMapper = viewSinisterMapper;
        this.viewSinisterPrestationRepository = viewSinisterPrestationRepository;
        this.viewSinisterPrestationMapper = viewSinisterPrestationMapper;
        
    }

    /**
     * Get all the ReportTugPerformance lines.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ViewSinisterDTO> findAll() {
        log.debug("Request to get all view sinister.");
        return viewSinisterRepository.findAll().stream()
                .map(viewSinisterMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     *  Get all the SinisterPrestation.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)    
    public Set<ViewSinisterPrestationDTO> findInProgressService() {
        log.debug("Request to get all sinister prestation in progress");
        Set<ViewSinisterPrestation> sinisterPrestations = viewSinisterPrestationRepository.findAllByStatusId(Constants.STATUS_IN_PROGRESS);
        
        if(CollectionUtils.isNotEmpty(sinisterPrestations)) {
            return sinisterPrestations.stream().map(viewSinisterPrestationMapper::toDto).collect(Collectors.toSet());
        }        
        return new HashSet<>();
    }

    /**
     *  Get all the SinisterPrestation.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)    
    public Set<ViewSinisterPrestationDTO> findClosedService() {
        log.debug("Request to get all sinister prestation closed");
        Set<ViewSinisterPrestation> sinisterPrestations = viewSinisterPrestationRepository.findAllByStatusId(Constants.STATUS_CLOSED);
        
        if(CollectionUtils.isNotEmpty(sinisterPrestations)) {
            return sinisterPrestations.stream().map(viewSinisterPrestationMapper::toDto).collect(Collectors.toSet());
        }        
        return new HashSet<>();
    }

    /**
     *  Get all the SinisterPrestation.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)    
    public Set<ViewSinisterPrestationDTO> findCanceledService() {
        log.debug("Request to get all sinister prestation canceled");
        Set<ViewSinisterPrestation> sinisterPrestations = viewSinisterPrestationRepository.findAllByStatusId(Constants.STATUS_CANCELED);
        
        if(CollectionUtils.isNotEmpty(sinisterPrestations)) {
            return sinisterPrestations.stream().map(viewSinisterPrestationMapper::toDto).collect(Collectors.toSet());
        }        
        return new HashSet<>();
    }

    /**
     *  Get all the SinisterPrestation.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)    
    public Set<ViewSinisterPrestationDTO> findNotEligibleService() {
        log.debug("Request to get all sinister prestation not eligible");
        Set<ViewSinisterPrestation> sinisterPrestations = viewSinisterPrestationRepository.findAllByStatusId(Constants.STATUS_NOTELIGIBLE);
        
        if(CollectionUtils.isNotEmpty(sinisterPrestations)) {
            return sinisterPrestations.stream().map(viewSinisterPrestationMapper::toDto).collect(Collectors.toSet());
        }        
        return new HashSet<>();
    }
    
    
	@Transactional(readOnly = true)
    public Long getCountPrestationWithFiltter(String filter, Long status) {
        log.debug("Request to get all ViewPolicies");
        if (StringUtils.isNotBlank(filter)) {
            return viewSinisterPrestationRepository.countAllWithFilter(filter.toLowerCase(),status);
        } else {
            return viewSinisterPrestationRepository.countClosed(status);
        }
    }
	  
	  @Transactional(readOnly = true)
	    public Long getCountPrestation(Long status) {
	        log.debug("Request to get all ViewPolicies");
	        return viewSinisterPrestationRepository.countClosed(status);
	    }
    
    
	   @Transactional(readOnly = true)
	    public Page<ViewSinisterPrestationDTO> findAll(String filter, Pageable pageable, Long status) {
	        log.debug("Request to get a ViewPolicies page");
	        if (StringUtils.isNotBlank(filter)) {
	            return viewSinisterPrestationRepository.findAllWithFilter(filter.toLowerCase(), pageable, status).map(viewSinisterPrestationMapper::toDto);
	        } else {
	            return viewSinisterPrestationRepository.findAllPrest(pageable, status).map(viewSinisterPrestationMapper::toDto);
	        }
	    }
	   @Transactional(readOnly = true)
	    public Long getCountSinisterWithFiltter(String filter) {
	        log.debug("Request to get all sinister Page");
	        if (StringUtils.isNotBlank(filter)) {
	            return viewSinisterRepository.countAllWithFilter(filter.toLowerCase());
	        } else {
	            return viewSinisterRepository.count();
	        }
	    }

	   @Transactional(readOnly = true)
	    public Long getCountSinister() {
	        log.debug("Request to get all sinister Page");
	        return viewSinisterRepository.count();
	    }
	   
	   @Transactional(readOnly = true)
	    public Page<ViewSinisterDTO> findAllSinisterPage(String filter, Pageable pageable) {
	        log.debug("Request to get a ViewPolicies page");
	        if (StringUtils.isNotBlank(filter)) {
	            return viewSinisterRepository.findAllWithFilter(filter.toLowerCase(), pageable).map(viewSinisterMapper::toDto);
	        } else {
	            return viewSinisterRepository.findAll(pageable).map(viewSinisterMapper::toDto);
	        }
	    }
	   
	 /*  @Transactional(readOnly = true)
	    public Long getCountPrestationVrWithFiltter(String filter, Long status) {
	        log.debug("Request to get all ViewPolicies");
	        if (StringUtils.isNotBlank(filter)) {
	            return viewSinisterPrestationRepository.countAllVrWithFilter(filter.toLowerCase(),status);
	        } else {
	            return viewSinisterPrestationRepository.countVr(status);
	        }
	    }
		  
		  @Transactional(readOnly = true)
		    public Long getCountPrestationVr(Long status) {
		        log.debug("Request to get all ViewPolicies");
		        return viewSinisterPrestationRepository.countVr(status);
		    }
	    
	    
		   @Transactional(readOnly = true)
		    public Page<ViewSinisterPrestationDTO> findAllVr(String filter, Pageable pageable, Long status) {
		        log.debug("Request to get a ViewPolicies page");
		        if (StringUtils.isNotBlank(filter)) {
		            return viewSinisterPrestationRepository.findAllVrWithFilter(filter.toLowerCase(), pageable, status).map(viewSinisterPrestationMapper::toDto);
		        } else {
		            return viewSinisterPrestationRepository.findAllVrPrest(pageable, status).map(viewSinisterPrestationMapper::toDto);
		        }
		    }*/

}
