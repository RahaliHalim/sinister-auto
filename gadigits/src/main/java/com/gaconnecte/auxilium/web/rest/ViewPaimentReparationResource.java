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
import com.gaconnecte.auxilium.service.ViewDelaiMoyImmobilisationService;
import com.gaconnecte.auxilium.service.ViewPaimentReparationService;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewDelaiMoyImmobilisationDTO;
import com.gaconnecte.auxilium.service.dto.ViewPaimentReparationDTO;

@RestController
@RequestMapping("/api/view")
public class ViewPaimentReparationResource {
	private final Logger log = LoggerFactory.getLogger(ViewPaimentReparationResource.class);
    private static final String ENTITY_NAME = "viewPaimentReparationResource";
    
    
    private final ViewPaimentReparationService viewPaimentReparationService;
    @Autowired
    private LoggerService loggerService;
    
    public	ViewPaimentReparationResource ( ViewPaimentReparationService viewPaimentReparationService) {
    	
    	this.viewPaimentReparationService = viewPaimentReparationService ;
    	
    }
    
    
    @GetMapping("/viewPaiment")
    @Timed
    public List<ViewPaimentReparationDTO> getAll() {
        return viewPaimentReparationService.findAll();
    }

    @PostMapping("/viewPaiment")
    @Timed
    public List<ViewPaimentReparationDTO> findSuiviAnnulations(@RequestBody SearchDTO searchDTO) {
        return viewPaimentReparationService.findAll(searchDTO);

    }
}
