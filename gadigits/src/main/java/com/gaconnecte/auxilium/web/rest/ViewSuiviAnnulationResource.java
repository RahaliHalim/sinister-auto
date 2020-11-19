package com.gaconnecte.auxilium.web.rest;

import java.util.List;
import com.gaconnecte.auxilium.service.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.ViewSuiviAnnulationService;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewSuiviAnnulationDTO;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/view")
public class ViewSuiviAnnulationResource {
	private final Logger log = LoggerFactory.getLogger(ViewBonificationResource.class);
    private static final String ENTITY_NAME = "viewSuiviAnnulation";
    
    
    private final ViewSuiviAnnulationService viewSuiviAnnulationService;
    @Autowired
    private LoggerService loggerService;
    
    public	ViewSuiviAnnulationResource ( ViewSuiviAnnulationService viewSuiviAnnulationService) {
    	
    	this.viewSuiviAnnulationService = viewSuiviAnnulationService ;
    	
    }
    
    
    @GetMapping("/suiviAnnulation")
    @Timed
    public List<ViewSuiviAnnulationDTO> getAll() {
        return viewSuiviAnnulationService.findAll();
    }

    @PostMapping("/suiviAnnulation")
    @Timed
    public List<ViewSuiviAnnulationDTO> findSuiviAnnulations(@RequestBody SearchDTO searchDTO) {
        return viewSuiviAnnulationService.Search(searchDTO);

    }
}
