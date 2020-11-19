package com.gaconnecte.auxilium.web.rest;

import java.util.List;

import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.ViewBonificationService;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewBonificationDTO;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPecMonitoringDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codahale.metrics.annotation.Timed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/view")
public class ViewBonificationResource {

	private final Logger log = LoggerFactory.getLogger(ViewBonificationResource.class);
    private static final String ENTITY_NAME = "viewBonification";
    
    
    private final ViewBonificationService viewBonificationService;

    @Autowired
    private LoggerService loggerService;
    
    public	ViewBonificationResource (ViewBonificationService viewBonificationService) {
    	
    	this.viewBonificationService = viewBonificationService ;
    	
    }
    
    @GetMapping("/bonification")
    @Timed
    public List<ViewBonificationDTO> getAllBonification() {
        return viewBonificationService.findAll();
    }
    
    @PostMapping("/bonification")
    @Timed
    public List<ViewBonificationDTO> getAll(@RequestBody SearchDTO searchDTO) {
        return viewBonificationService.Search(searchDTO);
    }

}
