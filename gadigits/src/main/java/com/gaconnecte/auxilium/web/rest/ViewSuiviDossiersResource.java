package com.gaconnecte.auxilium.web.rest;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.ViewSuiviDossiersService;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewSuiviDossiersDTO;
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
public class ViewSuiviDossiersResource {
	
	private final Logger log = LoggerFactory.getLogger(ViewSuiviDossiersResource.class);
    private static final String ENTITY_NAME = "viewSuiviDossiers";
    
    
    private final ViewSuiviDossiersService viewSuiviDossiersService;
    
    
    @Autowired
    private LoggerService loggerService;
    
 public	ViewSuiviDossiersResource (ViewSuiviDossiersService viewSuiviDossiersService) {
    	
    	this.viewSuiviDossiersService = viewSuiviDossiersService ;
    	
    }

 
 @GetMapping("/suiviDossiers")
 @Timed
 public List<ViewSuiviDossiersDTO> getAll() {
     return viewSuiviDossiersService.findAll();
 }
 
 @PostMapping("/suiviDossiers")
 @Timed
 public List<ViewSuiviDossiersDTO> findDossiersServices(@RequestBody SearchDTO searchDTO) {
     return viewSuiviDossiersService.Search(searchDTO);

 }

 
 
}
