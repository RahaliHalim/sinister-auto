package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.OperationService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.OperationDTO;
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
 * REST controller for managing Operation.
 */
@RestController
@RequestMapping("/api")
public class OperationResource {

    private final Logger log = LoggerFactory.getLogger(OperationResource.class);

    private static final String ENTITY_NAME = "operation";

    private final OperationService operationService;

    public OperationResource(OperationService operationService) {
        this.operationService = operationService;
    }

    /**
     * POST  /operations : Create a new operation.
     *
     * @param operationDTO the operationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operationDTO, or with status 400 (Bad Request) if the operation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operations")
    @Timed
    public ResponseEntity<OperationDTO> createOperation(@RequestBody OperationDTO operationDTO) throws URISyntaxException {
        log.debug("REST request to save Operation : {}", operationDTO);
        if (operationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new operation cannot already have an ID")).body(null);
        }
        OperationDTO result = operationService.save(operationDTO);
        return ResponseEntity.created(new URI("/api/operations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operations : Updates an existing operation.
     *
     * @param operationDTO the operationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operationDTO,
     * or with status 400 (Bad Request) if the operationDTO is not valid,
     * or with status 500 (Internal Server Error) if the operationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operations")
    @Timed
    public ResponseEntity<OperationDTO> updateOperation(@RequestBody OperationDTO operationDTO) throws URISyntaxException {
        log.debug("REST request to update Operation : {}", operationDTO);
        if (operationDTO.getId() == null) {
            return createOperation(operationDTO);
        }
        OperationDTO result = operationService.save(operationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operations : get all the operations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of operations in body
     */
    @GetMapping("/operations")
    @Timed
    public List<OperationDTO> getAllOperations() {
        log.debug("REST request to get all Operations");
        return operationService.findAll();
    }

    @GetMapping("/operations/assistance")
    @Timed
    public List<OperationDTO> getAllAssistanceOperations() {
        log.debug("REST request to get all Operations");
        return operationService.findAllByCode("ASSISTANCE");
    }

    @GetMapping("/operations/pec")
    @Timed
    public List<OperationDTO> getAllPecOperations() {
        log.debug("REST request to get all Operations");
        return operationService.findAllByCode("PEC");
    }

    /**
     * GET  /operations/:id : get the "id" operation.
     *
     * @param id the id of the operationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/operations/{id}")
    @Timed
    public ResponseEntity<OperationDTO> getOperation(@PathVariable Long id) {
        log.debug("REST request to get Operation : {}", id);
        OperationDTO operationDTO = operationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(operationDTO));
    }

    /**
     * DELETE  /operations/:id : delete the "id" operation.
     *
     * @param id the id of the operationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operations/{id}")
    @Timed
    public ResponseEntity<Void> deleteOperation(@PathVariable Long id) {
        log.debug("REST request to delete Operation : {}", id);
        operationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/operations?query=:query : search for the operation corresponding
     * to the query.
     *
     * @param query the query of the operation search
     * @return the result of the search
     */
    @GetMapping("/_search/operations")
    @Timed
    public List<OperationDTO> searchOperations(@RequestParam String query) {
        log.debug("REST request to search Operations for query {}", query);
        return operationService.search(query);
    }

}
