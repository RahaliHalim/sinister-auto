package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.StampDutyService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.StampDutyDTO;
import com.gaconnecte.auxilium.service.dto.VehicleBrandDTO;

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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing StampDuty.
 */
@RestController
@RequestMapping("/api")
public class StampDutyResource {

    private final Logger log = LoggerFactory.getLogger(StampDutyResource.class);

    private static final String ENTITY_NAME = "stampDuty";

    private final StampDutyService stampDutyService;
    
    @Autowired
    private HistoryService historyService;

    public StampDutyResource(StampDutyService stampDutyService) {
        this.stampDutyService = stampDutyService;
    }

    /**
     * POST  /stamp-duties : Create a new stampDuty.
     *
     * @param stampDutyDTO the stampDutyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stampDutyDTO, or with status 400 (Bad Request) if the stampDuty has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stamp-duties")
    @Timed
    public ResponseEntity<StampDutyDTO> createStampDuty(@RequestBody StampDutyDTO stampDutyDTO) throws URISyntaxException {
        log.debug("REST request to save StampDuty : {}", stampDutyDTO);
        if (stampDutyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new stampDuty cannot already have an ID")).body(null);
        }
        StampDutyDTO result = stampDutyService.save(stampDutyDTO);
        if(result!= null ) {
         	historyService.historysave("DroitTimbre", result.getId(),null, result,0,1, "Création");
            }
        return ResponseEntity.created(new URI("/api/stamp-duties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stamp-duties : Updates an existing stampDuty.
     *
     * @param stampDutyDTO the stampDutyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stampDutyDTO,
     * or with status 400 (Bad Request) if the stampDutyDTO is not valid,
     * or with status 500 (Internal Server Error) if the stampDutyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stamp-duties")
    @Timed
    public ResponseEntity<StampDutyDTO> updateStampDuty(@RequestBody StampDutyDTO stampDutyDTO) throws URISyntaxException {
        log.debug("REST request to update StampDuty : {}", stampDutyDTO);
        if (stampDutyDTO.getId() == null) {
            return createStampDuty(stampDutyDTO);
        }
        StampDutyDTO oldStampDuty = stampDutyService.findOne(stampDutyDTO.getId()); 
        StampDutyDTO result = stampDutyService.save(stampDutyDTO);
        historyService.historysave("DroitTimbre",result.getId(),oldStampDuty,result,0,0, "Modification");
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stampDutyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stamp-duties : get all the stampDuties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stampDuties in body
     */
    @GetMapping("/stamp-duties")
    @Timed
    public List<StampDutyDTO> getAllStampDuties() {
        log.debug("REST request to get all StampDuties");
        return stampDutyService.findAll();
    }

    /**
     * GET  /stamp-duties/:id : get the "id" stampDuty.
     *
     * @param id the id of the stampDutyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stampDutyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stamp-duties/{id}")
    @Timed
    public ResponseEntity<StampDutyDTO> getStampDuty(@PathVariable Long id) {
        log.debug("REST request to get StampDuty : {}", id);
        StampDutyDTO stampDutyDTO = stampDutyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(stampDutyDTO));
    }
    @GetMapping("/stamp-duties/active")
    @Timed
    public ResponseEntity<StampDutyDTO> findActiveStampDuty() {
        log.debug("REST request to get Active StampDuty");
        StampDutyDTO stampDutyDTO = stampDutyService.findActiveStampDuty();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(stampDutyDTO));
    }

    /**
     * DELETE  /stamp-duties/:id : delete the "id" stampDuty.
     *
     * @param id the id of the stampDutyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stamp-duties/{id}")
    @Timed
    public ResponseEntity<Void> deleteStampDuty(@PathVariable Long id) {
        log.debug("REST request to delete StampDuty : {}", id);
        stampDutyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/stamp-duties?query=:query : search for the stampDuty corresponding
     * to the query.
     *
     * @param query the query of the stampDuty search
     * @return the result of the search
     */
    @GetMapping("/_search/stamp-duties")
    @Timed
    public List<StampDutyDTO> searchStampDuties(@RequestParam String query) {
        log.debug("REST request to search StampDuties for query {}", query);
        return stampDutyService.search(query);
    }

}