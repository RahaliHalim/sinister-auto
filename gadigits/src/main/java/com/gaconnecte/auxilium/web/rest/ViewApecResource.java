package com.gaconnecte.auxilium.web.rest;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.ViewApecService;
import com.gaconnecte.auxilium.service.dto.ApecDTO;
import com.gaconnecte.auxilium.service.dto.ViewApecDTO;

@RestController
@RequestMapping("/api/view")
public class ViewApecResource {
	
	private final Logger log = LoggerFactory.getLogger(ViewApecResource.class);
	
	private static final String ENTITY_NAME = "viewApec";
	
	private final ViewApecService viewApecService;
	
	private final LoggerService loggerService;
	
	public ViewApecResource(ViewApecService viewApecService, LoggerService loggerService) {
        this.viewApecService = viewApecService;
        this.loggerService = loggerService;
    }
	
	@GetMapping("/signature-accord/{userId}/{etat}")
	@Timed
	public ResponseEntity<Set<ViewApecDTO>> getAllApecsByStateAccord(@PathVariable Long userId,
			@PathVariable Integer etat) {
		try {
			Set<ViewApecDTO> sinistersPec = viewApecService.findByStateAccord(userId, etat);
			return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
