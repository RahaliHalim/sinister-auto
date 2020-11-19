package com.gaconnecte.auxilium.web.rest;
import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.ObservationService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import com.gaconnecte.auxilium.service.dto.ObservationDTO;
import com.gaconnecte.auxilium.service.dto.PriseEnChargeDTO;

import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gaconnecte.auxilium.domain.ResourceNotFoundException;
import com.gaconnecte.auxilium.security.SecurityUtils;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * REST controller for managing Observation.
 */
@RestController
@RequestMapping("/api")
public class ObservationResource {

    private final Logger log = LoggerFactory.getLogger(ObservationResource.class);

    private static final String ENTITY_NAME = "observation";

    private final ObservationService observationService;

    public ObservationResource(ObservationService observationService) {
        this.observationService = observationService;
    }

    /**
     * POST  /observations : Create a new observation.
     *
     * @param observationDTO the observationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new observationDTO, or with status 400 (Bad Request) if the observation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/observations")
    @Timed
    public ResponseEntity<ObservationDTO> createObservation(@Valid @RequestBody ObservationDTO observationDTO) throws URISyntaxException, ResourceNotFoundException {
        log.debug("REST request to save Observation : {}", observationDTO);
        if (observationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new observation cannot already have an ID")).body(null);
        }
        ObservationDTO result = observationService.save(observationDTO);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(observationDTO));
    }

    /**
     * PUT  /observations : Updates an existing observation.
     *
     * @param observationDTO the observationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated observationDTO,
     * or with status 400 (Bad Request) if the observationDTO is not valid,
     * or with status 500 (Internal Server Error) if the observationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/observations")
    @Timed
    public ResponseEntity<ObservationDTO> updateObservation(@Valid @RequestBody ObservationDTO observationDTO) throws URISyntaxException {
        log.debug("REST request to update Observation : {}", observationDTO);
        if (observationDTO.getId() == null) {
            return createObservation(observationDTO);
        }
        ObservationDTO result = observationService.save(observationDTO);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(observationDTO));
    }

    /**
     * GET  /observations : get all the observations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of observations in body
     */
    @GetMapping("/observations")
    @Timed
    public ResponseEntity<List<ObservationDTO>> getAllObservations() {
    	String login = SecurityUtils.getCurrentUserLogin();

        List<ObservationDTO> observationDTO = observationService.findAll(login);
        return new ResponseEntity<>(observationDTO, HttpStatus.OK);
    }

    @GetMapping("/observations/prestationsss")
    @Timed
    public ResponseEntity<List<ObservationDTO>> getAllObservationsOfPrestation() {
    	log.debug("REST request to get a set of observations");
    	String login = SecurityUtils.getCurrentUserLogin();

        List<ObservationDTO> observationDTO = observationService.findByPrestation(login);
        return new ResponseEntity<>(observationDTO, HttpStatus.OK);
    }
    
   
    
    /**
     * GET  /observations/:id : get the "id" observation.
     *
     * @param id the id of the observationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the observationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/observations/{id}")
    @Timed
    public ResponseEntity<ObservationDTO> getObservation(@PathVariable Long id) {
        log.debug("REST request to get Observation : {}", id);
        ObservationDTO observationDTO = observationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(observationDTO));
    }

    /**
     * DELETE  /observations/:id : delete the "id" observation.
     *
     * @param id the id of the observationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/observations/{id}")
    @Timed
    public ResponseEntity<Void> deleteObservation(@PathVariable Long id) {
        log.debug("REST request to delete Observation : {}", id);
        observationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

     /**
     * GET  /observations/:prestationId : get the "prestationId" prestation.
     *
     * @param prestationId the prestationId of the observationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prestationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/observations/prestation/{prestationId}")
    @Timed
    public List<ObservationDTO> getObservationByPrestation(@PathVariable Long prestationId) {
        log.debug("REST request to get Prestation : {}", prestationId);
        List<ObservationDTO> observationDTO = observationService.findObservationByPrestation(prestationId);
        if (CollectionUtils.isNotEmpty(observationDTO)) {
			return observationDTO;
		}
		return new ArrayList<>();
       
    }


     /**
     * GET  /observations/:prestationId : get the "prestationId" prestation.
     *
     * @param prestationId the prestationId of the observationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prestationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/observations/devis/{devisId}")
    @Timed
    public ResponseEntity<List<ObservationDTO>> getObservationByDevis(@ApiParam Pageable pageable, @PathVariable Long devisId) {
        log.debug("REST request to get Prestation : {}", devisId);
        Page<ObservationDTO> observationDTO = observationService.findObservationByDevis(pageable, devisId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(observationDTO, "/api/observations/prestation");
        return new ResponseEntity<>(observationDTO.getContent(), headers, HttpStatus.OK);
    }

}
