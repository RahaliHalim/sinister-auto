/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.repository.ViewSinisterPecMonitoringRepository;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPecMonitoringDTO;
import com.gaconnecte.auxilium.service.dto.ViewSuiviDossiersDTO;
import com.gaconnecte.auxilium.service.mapper.ViewSinisterPecMonitoringMapper;
import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hannibaal
 */
@Service
@Transactional
public class ViewSinisterPecMonitoringService {
    
    private final Logger log = LoggerFactory.getLogger(ViewSinisterPecMonitoringService.class);

    private final ViewSinisterPecMonitoringRepository viewSinisterPecMonitoringRepository;
    private final ViewSinisterPecMonitoringMapper viewSinisterPecMonitoringMapper;

    public ViewSinisterPecMonitoringService(ViewSinisterPecMonitoringRepository viewSinisterPecMonitoringRepository, ViewSinisterPecMonitoringMapper viewSinisterPecMonitoringMapper) {
        this.viewSinisterPecMonitoringRepository = viewSinisterPecMonitoringRepository;
        this.viewSinisterPecMonitoringMapper = viewSinisterPecMonitoringMapper;       
    }

    /**
     * Get all the view sinister prestation monitoring lines.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ViewSinisterPecMonitoringDTO> findAll(SearchDTO searchDTO) {
        log.debug("Request to get all view sinister pec monitoring.");
        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
        
        return viewSinisterPecMonitoringRepository.findAllByDates(startDate, endDate).stream()
                .map(viewSinisterPecMonitoringMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }
  

    
    
    @Transactional(readOnly = true)
    public List<ViewSinisterPecMonitoringDTO> findAll( ) {
        
        return viewSinisterPecMonitoringRepository.findAll().stream()
                .map(viewSinisterPecMonitoringMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }
    
}
