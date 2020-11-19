package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.ReasonService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.ReasonDTO;
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
 * REST controller for managing Reason.
 */
@RestController
@RequestMapping("/api")
public class ReasonResource {

    private final Logger log = LoggerFactory.getLogger(ReasonResource.class);

    private static final String ENTITY_NAME = "reason";

    private final ReasonService reasonService;

    public ReasonResource(ReasonService reasonService) {
        this.reasonService = reasonService;
    }

    /**
     * POST  /reasons : Create a new reason.
     *
     * @param reasonDTO the reasonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reasonDTO, or with status 400 (Bad Request) if the reason has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reasons")
    @Timed
    public ResponseEntity<ReasonDTO> createReason(@RequestBody ReasonDTO reasonDTO) throws URISyntaxException {
        log.debug("REST request to save Reason : {}", reasonDTO);
        if (reasonDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new reason cannot already have an ID")).body(null);
        }
        ReasonDTO result = reasonService.save(reasonDTO);
        return ResponseEntity.created(new URI("/api/reasons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reasons : Updates an existing reason.
     *
     * @param reasonDTO the reasonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reasonDTO,
     * or with status 400 (Bad Request) if the reasonDTO is not valid,
     * or with status 500 (Internal Server Error) if the reasonDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reasons")
    @Timed
    public ResponseEntity<ReasonDTO> updateReason(@RequestBody ReasonDTO reasonDTO) throws URISyntaxException {
        log.debug("REST request to update Reason : {}", reasonDTO);
        if (reasonDTO.getId() == null) {
            return createReason(reasonDTO);
        }
        ReasonDTO result = reasonService.save(reasonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reasonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reasons : get all the reasons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reasons in body
     */
    @GetMapping("/reasons")
    @Timed
    public List<ReasonDTO> getAllReasons() {
        log.debug("REST request to get all Reasons");
        log.debug("mission done");
        return reasonService.findAll();
    }
    
    /**
     * GET  /reasons : get all Motifs Reopened.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reasons in body
     */
    @GetMapping("/reasons/motifs-reopened")
    @Timed
    public List<ReasonDTO> getAllMotifReopened() {
        log.debug("REST request to get all Reasons Reopened");
        return reasonService.findAllMotifsReopened();
    }
    
    @GetMapping("/reasons/motifs-refused")
    @Timed
    public List<ReasonDTO> getAllMotifRefused() {
        log.debug("REST request to get all Reasons Refused");
        return reasonService.findAllMotifsRefused();
    }
    
    @GetMapping("/reasons/motifs-canceled")
    @Timed
    public List<ReasonDTO> getAllMotifCanceled() {
        log.debug("REST request to get all Reasons Canceled");
        return reasonService.findAllMotifsCanceled();
    }
    
    @GetMapping("/reasons/motifs/{stepId}")
    @Timed
    public List<ReasonDTO> getMotifsByStepId(@PathVariable Long stepId) {
    	log.debug("Rest Request to get Reasons By step : {}", stepId);
        return reasonService.findMotifsByStepId(stepId);
    }
    
  

    

    /**
     * GET  /reasons/:id : get the "id" reason.
     *
     * @param id the id of the reasonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reasonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reasons/{id}")
    @Timed
    public ResponseEntity<ReasonDTO> getReason(@PathVariable Long id) {
        log.debug("REST request to get Reason : {}", id);
        ReasonDTO reasonDTO = reasonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reasonDTO));
    }

    /**
     * DELETE  /reasons/:id : delete the "id" reason.
     *
     * @param id the id of the reasonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reasons/{id}")
    @Timed
    public ResponseEntity<Void> deleteReason(@PathVariable Long id) {
        log.debug("REST request to delete Reason : {}", id);
        reasonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/reasons?query=:query : search for the reason corresponding
     * to the query.
     *
     * @param query the query of the reason search
     * @return the result of the search
     */
    @GetMapping("/_search/reasons")
    @Timed
    public List<ReasonDTO> searchReasons(@RequestParam String query) {
        log.debug("REST request to search Reasons for query {}", query);
        return reasonService.search(query);
    }

}
