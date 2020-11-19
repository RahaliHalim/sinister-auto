package com.gaconnecte.auxilium.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.RefStatusSinisterService;
import com.gaconnecte.auxilium.service.ViewContratService;
import com.gaconnecte.auxilium.service.dto.ViewContratDTO;
import com.gaconnecte.auxilium.service.referential.dto.RefStatusSinisterDTO;

@RestController
@RequestMapping("/api")
public class RefStatusSinisterResource {

	 private final Logger log = LoggerFactory.getLogger(ViewContratResource.class);
	    private static final String ENTITY_NAME = "refStatusSinister";
	    
	    
	 private final RefStatusSinisterService refStatusSinisterService ; 
	 
	 
	 
	 
	 @Autowired
	    private LoggerService loggerService;
	    
	    public	RefStatusSinisterResource (RefStatusSinisterService refStatusSinisterService) {
	    	
	    	this.refStatusSinisterService = refStatusSinisterService ;
	    	
	    }
	    @GetMapping("/refStatusSinister")
	    @Timed
	    public List<RefStatusSinisterDTO> getAllContrat() {
	        return refStatusSinisterService.findAll();
	    }
}
