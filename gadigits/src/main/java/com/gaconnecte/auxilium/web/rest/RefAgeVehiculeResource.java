package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefAgeVehiculeService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefAgeVehiculeDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RefTva.
 */
@RestController
@RequestMapping("/api")
public class RefAgeVehiculeResource {

    private final Logger log = LoggerFactory.getLogger(RefAgeVehiculeResource.class);

    private static final String ENTITY_NAME = "refTva";

    private final RefAgeVehiculeService refAgeVehiculeService;

    public RefAgeVehiculeResource(RefAgeVehiculeService refAgeVehiculeService) {
        this.refAgeVehiculeService = refAgeVehiculeService;
    }

   


    /**
     * GET  /RefTvas : get all the RefTvas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of RefTvas in body
     */
    @GetMapping("/refAgeVehicules")
    @Timed
    public ResponseEntity<List<RefAgeVehiculeDTO>> getAllRefAgeVehicules(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefAge Vehicules");
        Page<RefAgeVehiculeDTO> page = refAgeVehiculeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/refAgeVehicules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /RefTvas/:id : get the "id" refTva.
     *
     * @param id the id of the refTvaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refTvaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/refAgeVehicules/{id}")
    @Timed
    public ResponseEntity<RefAgeVehiculeDTO> getRefTva(@PathVariable Long id) {
        log.debug("REST request to get RefTva : {}", id);
        RefAgeVehiculeDTO refAgeVehiculeDTO = refAgeVehiculeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refAgeVehiculeDTO));
    }

  
}
