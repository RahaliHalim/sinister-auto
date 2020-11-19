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

import com.gaconnecte.auxilium.repository.ViewPerformanceRemorqueurRepository;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewPerformanceRemorqueurDTO;
import com.gaconnecte.auxilium.service.mapper.ViewPerformanceREmorqueurMapper;


@Service
@Transactional
public class ViewPerformanceRemorqueurService {

	 private final Logger log = LoggerFactory.getLogger(ViewPerformanceRemorqueurService.class);

	    private final ViewPerformanceRemorqueurRepository viewPerformanceRemorqueurRepository;
	    private final ViewPerformanceREmorqueurMapper viewPerformanceREmorqueurMapper;

	    public ViewPerformanceRemorqueurService(ViewPerformanceRemorqueurRepository viewPerformanceRemorqueurRepository, ViewPerformanceREmorqueurMapper viewPerformanceREmorqueurMapper) {
	        this.viewPerformanceRemorqueurRepository = viewPerformanceRemorqueurRepository;
	        this.viewPerformanceREmorqueurMapper = viewPerformanceREmorqueurMapper;       
	    }
	    @Transactional(readOnly = true)
	    public List<ViewPerformanceRemorqueurDTO> findAll(SearchDTO searchDTO) {
	        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
	        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
	        
	        return viewPerformanceRemorqueurRepository.findAllByDates(startDate, endDate).stream()
	                .map(viewPerformanceREmorqueurMapper::toDto)
	                .collect(Collectors.toCollection(LinkedList::new));
	    }
	  

	    
	    
	    @Transactional(readOnly = true)
	    public List<ViewPerformanceRemorqueurDTO> findAll( ) {
	        
	        return viewPerformanceRemorqueurRepository.findAll().stream()
	                .map(viewPerformanceREmorqueurMapper::toDto)
	                .collect(Collectors.toCollection(LinkedList::new));
	    }
	
	
}
