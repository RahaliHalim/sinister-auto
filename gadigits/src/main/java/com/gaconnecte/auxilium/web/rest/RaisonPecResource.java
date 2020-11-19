package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.RaisonPecService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.RaisonPecDTO;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RaisonPec.
 */
@RestController
@RequestMapping("/api")
public class RaisonPecResource {

    private final Logger log = LoggerFactory.getLogger(RaisonPecResource.class);

    private static final String ENTITY_NAME = "raisonPec";

    private final RaisonPecService raisonPecService;
    
    @Autowired
    private HistoryService historyService;

    public RaisonPecResource(RaisonPecService raisonPecService) {
        this.raisonPecService = raisonPecService;
    }

    /**
     * POST  /raison-pecs : Create a new raisonPec.
     *
     * @param raisonPecDTO the raisonPecDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new raisonPecDTO, or with status 400 (Bad Request) if the raisonPec has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/raison-pecs")
    @Timed
    public ResponseEntity<RaisonPecDTO> createRaisonPec(@RequestBody RaisonPecDTO raisonPecDTO) throws URISyntaxException {
        log.debug("REST request to save RaisonPec : {}", raisonPecDTO);
        if (raisonPecDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new raisonPec cannot already have an ID")).body(null);
        }
        RaisonPecDTO result = raisonPecService.save(raisonPecDTO);
        if(result!= null ) {
         	historyService.historysave("RaisonPec", result.getId(),null, result,0,1, "Création");
            }
        return ResponseEntity.created(new URI("/api/raison-pecs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /raison-pecs : Updates an existing raisonPec.
     *
     * @param raisonPecDTO the raisonPecDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated raisonPecDTO,
     * or with status 400 (Bad Request) if the raisonPecDTO is not valid,
     * or with status 500 (Internal Server Error) if the raisonPecDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/raison-pecs")
    @Timed
    public ResponseEntity<RaisonPecDTO> updateRaisonPec(@RequestBody RaisonPecDTO raisonPecDTO) throws URISyntaxException {
        log.debug("REST request to update RaisonPec : {}", raisonPecDTO);
        if (raisonPecDTO.getId() == null) {
            return createRaisonPec(raisonPecDTO);
        }
        RaisonPecDTO oldRaisonPec = raisonPecService.findOne(raisonPecDTO.getId()); 
        RaisonPecDTO result = raisonPecService.save(raisonPecDTO);
        historyService.historysave("RaisonPec",result.getId(),oldRaisonPec,result,0,0, "Modification");
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, raisonPecDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /raison-pecs : get all the raisonPecs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of raisonPecs in body
     */
    @GetMapping("/raison-pecs")
    @Timed
    public List<RaisonPecDTO> getAllRaisonPecs() {
        log.debug("REST request to get all RaisonPecs");
        return raisonPecService.findAll();
    }

    /**
     * GET  /raison-pecs/:id : get the "id" raisonPec.
     *
     * @param id the id of the raisonPecDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the raisonPecDTO, or with status 404 (Not Found)
     */
    @GetMapping("/raison-pecs/{id}")
    @Timed
    public ResponseEntity<RaisonPecDTO> getRaisonPec(@PathVariable Long id) {
        log.debug("REST request to get RaisonPec : {}", id);
        RaisonPecDTO raisonPecDTO = raisonPecService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(raisonPecDTO));
    }

    /**
     * DELETE  /raison-pecs/:id : delete the "id" raisonPec.
     *
     * @param id the id of the raisonPecDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/raison-pecs/{id}")
    @Timed
    public ResponseEntity<Void> deleteRaisonPec(@PathVariable Long id) {
        log.debug("REST request to delete RaisonPec : {}", id);
        raisonPecService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    @GetMapping("/raison-pecs/motifs-reopened")
    @Timed
    public List<RaisonPecDTO> getAllMotifReopened() {
        log.debug("REST request to get all Reasons Reopened");
        return raisonPecService.findAllMotifsReopened();
    }
    
    @GetMapping("/raison-pecs/motifs-refused")
    @Timed
    public List<RaisonPecDTO> getAllMotifRefused() {
        log.debug("REST request to get all Reasons Refused");
        return raisonPecService.findAllMotifsRefused();
    }
    
    @GetMapping("/raison-pecs/motifs-canceled")
    @Timed
    public List<RaisonPecDTO> getAllMotifCanceled() {
        log.debug("REST request to get all Reasons Canceled");
        return raisonPecService.findAllMotifsCanceled();
    }
    
    @GetMapping("/raison-pecs/motifs/{stepId}")
    @Timed
    public List<RaisonPecDTO> getMotifsByStepId(@PathVariable Long stepId) {
    	log.debug("Rest Request to get Reasons By step : {}", stepId);
        return raisonPecService.findMotifsByStepId(stepId);
    }
    @GetMapping("raison-pecs/operations/{operationId}")
    @Timed
    public List<RaisonPecDTO> getMotifsByOperations(@PathVariable Long operationId) {
    	log.debug("Rest Request to get Reasons By  operation: {}", operationId);
        return raisonPecService.findMotifsByOperationId(operationId);
    }
    
    @GetMapping("/raison-pecs/motifs/changeMatrixId/{changeMatrixId}")
    @Timed
    public List<RaisonPecDTO> getMotifsByStatusPecStatusChangeMatrix(@PathVariable Long changeMatrixId) {
    	log.debug("Rest Request to get Reasons By step : {}", changeMatrixId);
        return raisonPecService.findMotifsByStatusPecStatusChangeMatrix(changeMatrixId);
    }

    /**
     * SEARCH  /_search/raison-pecs?query=:query : search for the raisonPec corresponding
     * to the query.
     *
     * @param query the query of the raisonPec search
     * @return the result of the search
     */
    @GetMapping("/_search/raison-pecs")
    @Timed
    public List<RaisonPecDTO> searchRaisonPecs(@RequestParam String query) {
        log.debug("REST request to search RaisonPecs for query {}", query);
        return raisonPecService.search(query);
    }

}
