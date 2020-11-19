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
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewDelaiMoyImmobilisationDTO;

@RestController
@RequestMapping("/api/view")
public class ViewDelaiMoyImmobilisationResource {
	private final Logger log = LoggerFactory.getLogger(ViewDelaiMoyImmobilisationResource.class);
    private static final String ENTITY_NAME = "viewDelaiMoyImmobilisation";
    
    
    private final ViewDelaiMoyImmobilisationService viewDelaiMoyImmobilisationService;
    @Autowired
    private LoggerService loggerService;
    
    public	ViewDelaiMoyImmobilisationResource ( ViewDelaiMoyImmobilisationService viewDelaiMoyImmobilisationService) {
    	
    	this.viewDelaiMoyImmobilisationService = viewDelaiMoyImmobilisationService ;
    	
    }
    
    
    @GetMapping("/viewDelaiMoy")
    @Timed
    public List<ViewDelaiMoyImmobilisationDTO> getAll() {
        return viewDelaiMoyImmobilisationService.findAll();
    }

    @PostMapping("/viewDelaiMoy")
    @Timed
    public List<ViewDelaiMoyImmobilisationDTO> findSuiviAnnulations(@RequestBody SearchDTO searchDTO) {
        return viewDelaiMoyImmobilisationService.findAll(searchDTO);

    }
}
