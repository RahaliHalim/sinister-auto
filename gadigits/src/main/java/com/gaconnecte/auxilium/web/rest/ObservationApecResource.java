package com.gaconnecte.auxilium.web.rest;

import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.ObservationApecService;
import com.gaconnecte.auxilium.service.dto.ObservationApecDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class ObservationApecResource {
	
	private final Logger log = LoggerFactory.getLogger(ObservationApecResource.class);
	
	private static final String ENTITY_NAME = "observation_apec";
	
	private final ObservationApecService observationApecService;
	
	@Autowired
	private LoggerService loggerService;
	
	public ObservationApecResource(ObservationApecService observationApecService) {
		this.observationApecService = observationApecService;
	}
	
	
	
	@PostMapping("/observation-apecs")
	@Timed
	public ResponseEntity<ObservationApecDTO> createObservationApec(@Valid @RequestBody ObservationApecDTO observationApecDTO) throws URISyntaxException {
		log.debug("REST request to save Apec : {}", observationApecDTO);
		if (observationApecDTO.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new observationApec cannot already have an ID"))
					.body(null);
		}
		ObservationApecDTO result = observationApecService.save(observationApecDTO);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
	}
	
	@PutMapping("/observation-apecs")
	@Timed
	public ResponseEntity<ObservationApecDTO> updateObservationApec(@Valid @RequestBody ObservationApecDTO observationApecDTO) throws URISyntaxException {
		log.debug("REST request to update ObservationApec : {}", observationApecDTO);
		if (observationApecDTO.getId() == null) {
			return createObservationApec(observationApecDTO);
		}
		ObservationApecDTO result = observationApecService.save(observationApecDTO);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
	}
	
	@GetMapping("/observation-apecs/{id}")
	@Timed
	public ResponseEntity<ObservationApecDTO> getObservationApec(@PathVariable Long id) {
		log.debug("REST request to get ObservationApec : {}", id);
		ObservationApecDTO observationApecDTO = observationApecService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(observationApecDTO));
	}
	
	@GetMapping("/observation-apecs-by-apec/{id}")
	@Timed
	public ResponseEntity<ObservationApecDTO> getObservationApecByApecId(@PathVariable Long id) {
		log.debug("REST request to get ObservationApec : {}", id);
		ObservationApecDTO observationApecDTO = observationApecService.findOneByApecId(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(observationApecDTO));
	}
	
	@DeleteMapping("/observation-apecs/{id}")
	@Timed
	public ResponseEntity<Void> deleteObservationApec(@PathVariable Long id) {
		log.debug("REST request to delete ObservationApec : {}", id);
		observationApecService.delete(id);
		return ResponseEntity.ok().build();
	}

}
