package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.StatusPecService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.StatusPecDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StatusPec.
 */
@RestController
@RequestMapping("/api")
public class StatusPecResource {

    private final Logger log = LoggerFactory.getLogger(StatusPecResource.class);

    private static final String ENTITY_NAME = "statusPec";

    private final StatusPecService statusPecService;

    public StatusPecResource(StatusPecService statusPecService) {
        this.statusPecService = statusPecService;
    }

    /**
     * POST  /status-pecs : Create a new statusPec.
     *
     * @param statusPecDTO the statusPecDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new statusPecDTO, or with status 400 (Bad Request) if the statusPec has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/status-pecs")
    @Timed
    public ResponseEntity<StatusPecDTO> createStatusPec(@RequestBody StatusPecDTO statusPecDTO) throws URISyntaxException {
        log.debug("REST request to save StatusPec : {}", statusPecDTO);
        if (statusPecDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new statusPec cannot already have an ID")).body(null);
        }
        StatusPecDTO result = statusPecService.save(statusPecDTO);
        return ResponseEntity.created(new URI("/api/status-pecs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /status-pecs : Updates an existing statusPec.
     *
     * @param statusPecDTO the statusPecDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated statusPecDTO,
     * or with status 400 (Bad Request) if the statusPecDTO is not valid,
     * or with status 500 (Internal Server Error) if the statusPecDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/status-pecs")
    @Timed
    public ResponseEntity<StatusPecDTO> updateStatusPec(@RequestBody StatusPecDTO statusPecDTO) throws URISyntaxException {
        log.debug("REST request to update StatusPec : {}", statusPecDTO);
        if (statusPecDTO.getId() == null) {
            return createStatusPec(statusPecDTO);
        }
        StatusPecDTO result = statusPecService.save(statusPecDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, statusPecDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /status-pecs : get all the statusPecs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of statusPecs in body
     */
    @GetMapping("/status-pecs")
    @Timed
    public List<StatusPecDTO> getAllStatusPecs() {
        log.debug("REST request to get all StatusPecs");
        return statusPecService.findAll();
    }

    /**
     * GET  /status-pecs/code/:code : get all the statusPecs.
     * @param code
     * @return the ResponseEntity with status 200 (OK) and the list of statusPecs in body
     */
    @GetMapping("/status-pecs/code/{code}")
    @Timed
    public List<StatusPecDTO> getAllStatusPecsByCode(@PathVariable String code) {
        log.debug("REST request to get all StatusPecs");
        return statusPecService.findAllByCode(code);
    }

    /**
     * GET  /status-pecs/reason : get all the statusPecs.
     * @return the ResponseEntity with status 200 (OK) and the list of statusPecs in body
     */
    @GetMapping("/status-pecs/reason")
    @Timed
    public List<StatusPecDTO> getAllStatusWitchHasReason() {
        log.debug("REST request to get all StatusPecs");
        return statusPecService.findAllWitchHasReason();
    }

    /**
     * GET  /status-pecs/:id : get the "id" statusPec.
     *
     * @param id the id of the statusPecDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the statusPecDTO, or with status 404 (Not Found)
     */
    @GetMapping("/status-pecs/{id}")
    @Timed
    public ResponseEntity<StatusPecDTO> getStatusPec(@PathVariable Long id) {
        log.debug("REST request to get StatusPec : {}", id);
        StatusPecDTO statusPecDTO = statusPecService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(statusPecDTO));
    }

    /**
     * DELETE  /status-pecs/:id : delete the "id" statusPec.
     *
     * @param id the id of the statusPecDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/status-pecs/{id}")
    @Timed
    public ResponseEntity<Void> deleteStatusPec(@PathVariable Long id) {
        log.debug("REST request to delete StatusPec : {}", id);
        statusPecService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/status-pecs?query=:query : search for the statusPec corresponding
     * to the query.
     *
     * @param query the query of the statusPec search
     * @return the result of the search
     */
    @GetMapping("/_search/status-pecs")
    @Timed
    public List<StatusPecDTO> searchStatusPecs(@RequestParam String query) {
        log.debug("REST request to search StatusPecs for query {}", query);
        return statusPecService.search(query);
    }

}
