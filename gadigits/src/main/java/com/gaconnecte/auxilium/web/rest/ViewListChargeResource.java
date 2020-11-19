package com.gaconnecte.auxilium.web.rest;
import java.util.List;
import java.util.Optional;


import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.ViewListChargeService;
import com.gaconnecte.auxilium.service.dto.ViewListChargeDTO;


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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/view")
public class ViewListChargeResource {
	
	private final Logger log = LoggerFactory.getLogger(ViewListChargeResource.class);
    private static final String ENTITY_NAME = "viewListCharge";
    
    private final ViewListChargeService viewListChargeService ;
    
    @Autowired
    private LoggerService loggerService;
    
    public	ViewListChargeResource (ViewListChargeService viewListChargeService) {
    	
    	this.viewListChargeService = viewListChargeService ;
    	
    }
    
    @GetMapping("/listCharge")
    @Timed
    public List<ViewListChargeDTO> getAll() {
        return viewListChargeService.findAll();
    }
    
    
}
