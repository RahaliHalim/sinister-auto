package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.PeriodicityService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.PeriodicityDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Periodicity.
 */
@RestController
@RequestMapping("/api")
public class PeriodicityResource {

    private final Logger log = LoggerFactory.getLogger(PeriodicityResource.class);

    private static final String ENTITY_NAME = "periodicity";

    private final PeriodicityService periodicityService;

    public PeriodicityResource(PeriodicityService periodicityService) {
        this.periodicityService = periodicityService;
    }

    /**
     * POST  /periodicities : Create a new periodicity.
     *
     * @param periodicityDTO the periodicityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new periodicityDTO, or with status 400 (Bad Request) if the periodicity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/periodicities")
    @Timed
    public ResponseEntity<PeriodicityDTO> createPeriodicity(@RequestBody PeriodicityDTO periodicityDTO) throws URISyntaxException {
        log.debug("REST request to save Periodicity : {}", periodicityDTO);
        if (periodicityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new periodicity cannot already have an ID")).body(null);
        }
        PeriodicityDTO result = periodicityService.save(periodicityDTO);
        return ResponseEntity.created(new URI("/api/periodicities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /periodicities : Updates an existing periodicity.
     *
     * @param periodicityDTO the periodicityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated periodicityDTO,
     * or with status 400 (Bad Request) if the periodicityDTO is not valid,
     * or with status 500 (Internal Server Error) if the periodicityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/periodicities")
    @Timed
    public ResponseEntity<PeriodicityDTO> updatePeriodicity(@RequestBody PeriodicityDTO periodicityDTO) throws URISyntaxException {
        log.debug("REST request to update Periodicity : {}", periodicityDTO);
        if (periodicityDTO.getId() == null) {
            return createPeriodicity(periodicityDTO);
        }
        PeriodicityDTO result = periodicityService.save(periodicityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, periodicityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /periodicities : get all the periodicities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of periodicities in body
     */
    @GetMapping("/periodicities")
    @Timed
    public List<PeriodicityDTO> getAllPeriodicities() {
        log.debug("REST request to get all Periodicities");
        return periodicityService.findAll();
    }

    /**
     * GET  /periodicities/:id : get the "id" periodicity.
     *
     * @param id the id of the periodicityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the periodicityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/periodicities/{id}")
    @Timed
    public ResponseEntity<PeriodicityDTO> getPeriodicity(@PathVariable Long id) {
        log.debug("REST request to get Periodicity : {}", id);
        PeriodicityDTO periodicityDTO = periodicityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(periodicityDTO));
    }

    /**
     * DELETE  /periodicities/:id : delete the "id" periodicity.
     *
     * @param id the id of the periodicityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/periodicities/{id}")
    @Timed
    public ResponseEntity<Void> deletePeriodicity(@PathVariable Long id) {
        log.debug("REST request to delete Periodicity : {}", id);
        periodicityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/periodicities?query=:query : search for the periodicity corresponding
     * to the query.
     *
     * @param query the query of the periodicity search
     * @return the result of the search
     */
    @GetMapping("/_search/periodicities")
    @Timed
    public List<PeriodicityDTO> searchPeriodicities(@RequestParam String query) {
        log.debug("REST request to search Periodicities for query {}", query);
        return periodicityService.search(query);
    }

}
