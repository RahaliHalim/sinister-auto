package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.RaisonAssistanceService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.RaisonAssistanceDTO;
import com.gaconnecte.auxilium.service.dto.RaisonPecDTO;
import com.gaconnecte.auxilium.service.referential.dto.RefGroundsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing RaisonAssistance.
 */
@RestController
@RequestMapping("/api")
public class RaisonAssistanceResource {

    private final Logger log = LoggerFactory.getLogger(RaisonAssistanceResource.class);

    private static final String ENTITY_NAME = "raisonAssistance";

    private final RaisonAssistanceService raisonAssistanceService;
    
    @Autowired
    private HistoryService historyService;

    public RaisonAssistanceResource(RaisonAssistanceService raisonAssistanceService) {
        this.raisonAssistanceService = raisonAssistanceService;
    }

    /**
     * POST  /raison-assistances : Create a new raisonAssistance.
     *
     * @param raisonAssistanceDTO the raisonAssistanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new raisonAssistanceDTO, or with status 400 (Bad Request) if the raisonAssistance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/raison-assistances")
    @Timed
    public ResponseEntity<RaisonAssistanceDTO> createRaisonAssistance(@RequestBody RaisonAssistanceDTO raisonAssistanceDTO) throws URISyntaxException {
        log.debug("REST request to save RaisonAssistance : {}", raisonAssistanceDTO);
        if (raisonAssistanceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new raisonAssistance cannot already have an ID")).body(null);
        }
        RaisonAssistanceDTO result = raisonAssistanceService.save(raisonAssistanceDTO);
        if(result!= null ) {
         	historyService.historysave("RaisonAssistance", result.getId(),null, result,0,1, "Création");
            }
        return ResponseEntity.created(new URI("/api/raison-assistances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /raison-assistances : Updates an existing raisonAssistance.
     *
     * @param raisonAssistanceDTO the raisonAssistanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated raisonAssistanceDTO,
     * or with status 400 (Bad Request) if the raisonAssistanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the raisonAssistanceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/raison-assistances")
    @Timed
    public ResponseEntity<RaisonAssistanceDTO> updateRaisonAssistance(@RequestBody RaisonAssistanceDTO raisonAssistanceDTO) throws URISyntaxException {
        log.debug("REST request to update RaisonAssistance : {}", raisonAssistanceDTO);
        if (raisonAssistanceDTO.getId() == null) {
            return createRaisonAssistance(raisonAssistanceDTO);
        }
        RaisonAssistanceDTO oldRaisonAssistance = raisonAssistanceService.findOne(raisonAssistanceDTO.getId()); 
        RaisonAssistanceDTO result = raisonAssistanceService.save(raisonAssistanceDTO);
        historyService.historysave("RaisonAssistance",result.getId(),oldRaisonAssistance,result,0,0, "Modification");
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, raisonAssistanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /raison-assistances : get all the raisonAssistances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of raisonAssistances in body
     */
    @GetMapping("/raison-assistances")
    @Timed
    public List<RaisonAssistanceDTO> getAllRaisonAssistances() {
        log.debug("REST request to get all RaisonAssistances");
        return raisonAssistanceService.findAll();
    }
    
    /**
     * GET  /raison-assistances : get all the raisonAssistances.
     *
     * @param id
     * @return the ResponseEntity with status 200 (OK) and the list of raisonAssistances in body
     */
    @GetMapping("/raison-assistances/status/{id}")
    @Timed
    public ResponseEntity<List<RaisonAssistanceDTO>> getAllRaisonAssistancesByStatus(@PathVariable Long id) {
        log.debug("REST request to get a page of raisonAssistances");
        List<RaisonAssistanceDTO> grounds = raisonAssistanceService.findAllByStatus(id);
        return new ResponseEntity<>(grounds, HttpStatus.OK);
    }
    
    @GetMapping("/reasons/motifs/operation/{id}")
    @Timed
    public ResponseEntity<List<RaisonAssistanceDTO>> getAllRaisonAssistancesByOperation(@PathVariable Long id) {
        log.debug("REST request to get a page of raisonAssistances by operation");
        List<RaisonAssistanceDTO> grounds = raisonAssistanceService.findAllByOperation(id);
        return new ResponseEntity<>(grounds, HttpStatus.OK);
    }
    
    

    /**
     * GET  /raison-assistances/:id : get the "id" raisonAssistance.
     *
     * @param id the id of the raisonAssistanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the raisonAssistanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/raison-assistances/{id}")
    @Timed
    public ResponseEntity<RaisonAssistanceDTO> getRaisonAssistance(@PathVariable Long id) {
        log.debug("REST request to get RaisonAssistance : {}", id);
        RaisonAssistanceDTO raisonAssistanceDTO = raisonAssistanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(raisonAssistanceDTO));
    }

    /**
     * DELETE  /raison-assistances/:id : delete the "id" raisonAssistance.
     *
     * @param id the id of the raisonAssistanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/raison-assistances/{id}")
    @Timed
    public ResponseEntity<Void> deleteRaisonAssistance(@PathVariable Long id) {
        log.debug("REST request to delete RaisonAssistance : {}", id);
        raisonAssistanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/raison-assistances?query=:query : search for the raisonAssistance corresponding
     * to the query.
     *
     * @param query the query of the raisonAssistance search
     * @return the result of the search
     */
    @GetMapping("/_search/raison-assistances")
    @Timed
    public List<RaisonAssistanceDTO> searchRaisonAssistances(@RequestParam String query) {
        log.debug("REST request to search RaisonAssistances for query {}", query);
        return raisonAssistanceService.search(query);
    }

}
