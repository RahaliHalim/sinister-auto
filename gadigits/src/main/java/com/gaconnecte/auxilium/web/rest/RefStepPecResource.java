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
import com.gaconnecte.auxilium.service.RefStepPecService;
import com.gaconnecte.auxilium.service.referential.dto.RefStepPecDTO;

@RestController
@RequestMapping("/api")
public class RefStepPecResource {
	private final Logger log = LoggerFactory.getLogger(ViewListChargeResource.class);
    private static final String ENTITY_NAME = "refStepPec";
 
    
    

    private final RefStepPecService refStepPecService ;
    
    @Autowired
    private LoggerService loggerService;
    
    public	RefStepPecResource (RefStepPecService refStepPecService) {
    	
    	this.refStepPecService = refStepPecService ;
    	
    }
    @GetMapping("/listPec")
    @Timed
    public List<RefStepPecDTO> getAll() {
        return refStepPecService.findAll();
    }
    
    
    @GetMapping("/listPecByNumber")
    @Timed
    public List<RefStepPecDTO> getAllByNumber() {
        return refStepPecService.findAllByCode();
    }
    
}
