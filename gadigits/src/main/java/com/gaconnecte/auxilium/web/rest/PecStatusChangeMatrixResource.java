package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.PecStatusChangeMatrixService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.PecStatusChangeMatrixDTO;
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
 * REST controller for managing PecStatusChangeMatrix.
 */
@RestController
@RequestMapping("/api")
public class PecStatusChangeMatrixResource {

    private final Logger log = LoggerFactory.getLogger(PecStatusChangeMatrixResource.class);

    private static final String ENTITY_NAME = "pecStatusChangeMatrix";

    private final PecStatusChangeMatrixService pecStatusChangeMatrixService;

    public PecStatusChangeMatrixResource(PecStatusChangeMatrixService pecStatusChangeMatrixService) {
        this.pecStatusChangeMatrixService = pecStatusChangeMatrixService;
    }

    /**
     * POST  /pec-status-change-matrices : Create a new pecStatusChangeMatrix.
     *
     * @param pecStatusChangeMatrixDTO the pecStatusChangeMatrixDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pecStatusChangeMatrixDTO, or with status 400 (Bad Request) if the pecStatusChangeMatrix has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pec-status-change-matrices")
    @Timed
    public ResponseEntity<PecStatusChangeMatrixDTO> createPecStatusChangeMatrix(@RequestBody PecStatusChangeMatrixDTO pecStatusChangeMatrixDTO) throws URISyntaxException {
        log.debug("REST request to save PecStatusChangeMatrix : {}", pecStatusChangeMatrixDTO);
        if (pecStatusChangeMatrixDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pecStatusChangeMatrix cannot already have an ID")).body(null);
        }
        PecStatusChangeMatrixDTO result = pecStatusChangeMatrixService.save(pecStatusChangeMatrixDTO);
        return ResponseEntity.created(new URI("/api/pec-status-change-matrices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pec-status-change-matrices : Updates an existing pecStatusChangeMatrix.
     *
     * @param pecStatusChangeMatrixDTO the pecStatusChangeMatrixDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pecStatusChangeMatrixDTO,
     * or with status 400 (Bad Request) if the pecStatusChangeMatrixDTO is not valid,
     * or with status 500 (Internal Server Error) if the pecStatusChangeMatrixDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pec-status-change-matrices")
    @Timed
    public ResponseEntity<PecStatusChangeMatrixDTO> updatePecStatusChangeMatrix(@RequestBody PecStatusChangeMatrixDTO pecStatusChangeMatrixDTO) throws URISyntaxException {
        log.debug("REST request to update PecStatusChangeMatrix : {}", pecStatusChangeMatrixDTO);
        if (pecStatusChangeMatrixDTO.getId() == null) {
            return createPecStatusChangeMatrix(pecStatusChangeMatrixDTO);
        }
        PecStatusChangeMatrixDTO result = pecStatusChangeMatrixService.save(pecStatusChangeMatrixDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pecStatusChangeMatrixDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pec-status-change-matrices : get all the pecStatusChangeMatrices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pecStatusChangeMatrices in body
     */
    @GetMapping("/pec-status-change-matrices")
    @Timed
    public List<PecStatusChangeMatrixDTO> getAllPecStatusChangeMatrices() {
        log.debug("REST request to get all PecStatusChangeMatrices");
        return pecStatusChangeMatrixService.findAll();
    }

    /**
     * GET  /pec-status-change-matrices/:id : get the "id" pecStatusChangeMatrix.
     *
     * @param id the id of the pecStatusChangeMatrixDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pecStatusChangeMatrixDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pec-status-change-matrices/{id}")
    @Timed
    public ResponseEntity<PecStatusChangeMatrixDTO> getPecStatusChangeMatrix(@PathVariable Long id) {
        log.debug("REST request to get PecStatusChangeMatrix : {}", id);
        PecStatusChangeMatrixDTO pecStatusChangeMatrixDTO = pecStatusChangeMatrixService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pecStatusChangeMatrixDTO));
    }

    /**
     * DELETE  /pec-status-change-matrices/:id : delete the "id" pecStatusChangeMatrix.
     *
     * @param id the id of the pecStatusChangeMatrixDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pec-status-change-matrices/{id}")
    @Timed
    public ResponseEntity<Void> deletePecStatusChangeMatrix(@PathVariable Long id) {
        log.debug("REST request to delete PecStatusChangeMatrix : {}", id);
        pecStatusChangeMatrixService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pec-status-change-matrices?query=:query : search for the pecStatusChangeMatrix corresponding
     * to the query.
     *
     * @param query the query of the pecStatusChangeMatrix search
     * @return the result of the search
     */
    @GetMapping("/_search/pec-status-change-matrices")
    @Timed
    public List<PecStatusChangeMatrixDTO> searchPecStatusChangeMatrices(@RequestParam String query) {
        log.debug("REST request to search PecStatusChangeMatrices for query {}", query);
        return pecStatusChangeMatrixService.search(query);
    }

}
