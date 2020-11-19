package com.gaconnecte.auxilium.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.ViewPerformanceRemorqueurService;
import com.gaconnecte.auxilium.service.ViewSuiviAnnulationService;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewPerformanceRemorqueurDTO;
import com.gaconnecte.auxilium.service.dto.ViewSuiviAnnulationDTO;

@RestController
@RequestMapping("/api/view")
public class ViewPerformanceRemorqueurResource {
	private final Logger log = LoggerFactory.getLogger(ViewPerformanceRemorqueurResource.class);
    private static final String ENTITY_NAME = "viewSuiviAnnulation";
    
    
    
    
    private final ViewPerformanceRemorqueurService viewPerformanceRemorqueurService;
    @Autowired
    private LoggerService loggerService;
    
    public	ViewPerformanceRemorqueurResource ( ViewPerformanceRemorqueurService viewPerformanceRemorqueurService) {
    	
    	this.viewPerformanceRemorqueurService = viewPerformanceRemorqueurService ;
    	
    }
    
    
    @GetMapping("/viewPerformance")
    @Timed
    public List<ViewPerformanceRemorqueurDTO> getAll() {
        return viewPerformanceRemorqueurService.findAll();
    }

    @PostMapping("/viewPerformance")
    @Timed
    public List<ViewPerformanceRemorqueurDTO> find(@RequestBody SearchDTO searchDTO) {
        return viewPerformanceRemorqueurService.findAll(searchDTO);

    }
    
    
    
    
    
}
