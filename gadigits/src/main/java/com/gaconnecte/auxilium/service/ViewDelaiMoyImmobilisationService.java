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

import com.gaconnecte.auxilium.repository.ViewDelaiMoyImmobilisationRepository;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewDelaiMoyImmobilisationDTO;
import com.gaconnecte.auxilium.service.mapper.ViewDelaiMoyImmobilisationMapper;

@Service
@Transactional
public class ViewDelaiMoyImmobilisationService {

	

    private final Logger log = LoggerFactory.getLogger(ViewDelaiMoyImmobilisationService.class);

    private final ViewDelaiMoyImmobilisationRepository viewDelaiMoyImmobilisationRepository;
    private final ViewDelaiMoyImmobilisationMapper viewDelaiMoyImmobilisationMapper;

    public ViewDelaiMoyImmobilisationService(ViewDelaiMoyImmobilisationRepository viewDelaiMoyImmobilisationRepository, ViewDelaiMoyImmobilisationMapper viewDelaiMoyImmobilisationMapper) {
        this.viewDelaiMoyImmobilisationRepository = viewDelaiMoyImmobilisationRepository;
        this.viewDelaiMoyImmobilisationMapper = viewDelaiMoyImmobilisationMapper;       
    }

    /**
     * Get all the view sinister prestation monitoring lines.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ViewDelaiMoyImmobilisationDTO> findAll(SearchDTO searchDTO) {
        log.debug("Request to get all view sinister pec monitoring.");
        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
        
        return viewDelaiMoyImmobilisationRepository.findAllByDates(startDate, endDate).stream()
                .map(viewDelaiMoyImmobilisationMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }
  

    
    
    @Transactional(readOnly = true)
    public List<ViewDelaiMoyImmobilisationDTO> findAll( ) {
        
        return viewDelaiMoyImmobilisationRepository.findAll().stream()
                .map(viewDelaiMoyImmobilisationMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }
    
	
}
