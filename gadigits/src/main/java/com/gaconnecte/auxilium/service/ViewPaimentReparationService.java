package com.gaconnecte.auxilium.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.repository.ViewPaimentReparationRepository;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewPaimentReparationDTO;
import com.gaconnecte.auxilium.service.mapper.ViewPaimentReparationMapper;

@Service
@Transactional
public class ViewPaimentReparationService {

	
	
	 private final Logger log = LoggerFactory.getLogger(ViewPaimentReparationService.class);

	    private final ViewPaimentReparationRepository viewPaimentReparationRepository;
	    private final ViewPaimentReparationMapper viewPaimentReparationMapper;

	    public ViewPaimentReparationService(ViewPaimentReparationRepository viewPaimentReparationRepository, ViewPaimentReparationMapper viewPaimentReparationMapper) {
	        this.viewPaimentReparationRepository = viewPaimentReparationRepository;
	        this.viewPaimentReparationMapper = viewPaimentReparationMapper;       
	    }

	    /**
	     * Get all the view sinister prestation monitoring lines.
	     *
	     * @return the list of entities
	     */
	    @Transactional(readOnly = true)
	    public List<ViewPaimentReparationDTO> findAll(SearchDTO searchDTO) {
	        log.debug("Request to get all view sinister pec monitoring.");
	        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
	        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
	        log.debug("Request to get all view sinister pec monitoring." , viewPaimentReparationRepository.findAllByDates(startDate, endDate).size());
	        return viewPaimentReparationRepository.findAllByDates(startDate, endDate).stream()
	                .map(viewPaimentReparationMapper::toDto)
	                .collect(Collectors.toCollection(LinkedList::new));
	    }
	  

	    
	    
	    @Transactional(readOnly = true)
	    public List<ViewPaimentReparationDTO> findAll( ) {
	        
	        return viewPaimentReparationRepository.findAll().stream()
	                .map(viewPaimentReparationMapper::toDto)
	                .collect(Collectors.toCollection(LinkedList::new));
	    }
	    
		
	
	
}
