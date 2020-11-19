package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.ReinsurerService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.ReinsurerDTO;
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
 * REST controller for managing Reinsurer.
 */
@RestController
@RequestMapping("/api")
public class ReinsurerResource {

    private final Logger log = LoggerFactory.getLogger(ReinsurerResource.class);

    private static final String ENTITY_NAME = "reinsurer";

    private final ReinsurerService reinsurerService;

    public ReinsurerResource(ReinsurerService reinsurerService) {
        this.reinsurerService = reinsurerService;
    }

    /**
     * POST  /reinsurers : Create a new reinsurer.
     *
     * @param reinsurerDTO the reinsurerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reinsurerDTO, or with status 400 (Bad Request) if the reinsurer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reinsurers")
    @Timed
    public ResponseEntity<ReinsurerDTO> createReinsurer(@RequestBody ReinsurerDTO reinsurerDTO) throws URISyntaxException {
        log.debug("REST request to save Reinsurer : {}", reinsurerDTO);
        if (reinsurerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new reinsurer cannot already have an ID")).body(null);
        }
        ReinsurerDTO result = reinsurerService.save(reinsurerDTO);
        return ResponseEntity.created(new URI("/api/reinsurers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reinsurers : Updates an existing reinsurer.
     *
     * @param reinsurerDTO the reinsurerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reinsurerDTO,
     * or with status 400 (Bad Request) if the reinsurerDTO is not valid,
     * or with status 500 (Internal Server Error) if the reinsurerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reinsurers")
    @Timed
    public ResponseEntity<ReinsurerDTO> updateReinsurer(@RequestBody ReinsurerDTO reinsurerDTO) throws URISyntaxException {
        log.debug("REST request to update Reinsurer : {}", reinsurerDTO);
        if (reinsurerDTO.getId() == null) {
            return createReinsurer(reinsurerDTO);
        }
        ReinsurerDTO result = reinsurerService.save(reinsurerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reinsurerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reinsurers : get all the reinsurers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reinsurers in body
     */
    @GetMapping("/reinsurers")
    @Timed
    public List<ReinsurerDTO> getAllReinsurers() {
        log.debug("REST request to get all Reinsurers");
        return reinsurerService.findAll();
    }

    /**
     * GET  /reinsurers/:id : get the "id" reinsurer.
     *
     * @param id the id of the reinsurerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reinsurerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reinsurers/{id}")
    @Timed
    public ResponseEntity<ReinsurerDTO> getReinsurer(@PathVariable Long id) {
        log.debug("REST request to get Reinsurer : {}", id);
        ReinsurerDTO reinsurerDTO = reinsurerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reinsurerDTO));
    }

    /**
     * DELETE  /reinsurers/:id : delete the "id" reinsurer.
     *
     * @param id the id of the reinsurerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reinsurers/{id}")
    @Timed
    public ResponseEntity<Void> deleteReinsurer(@PathVariable Long id) {
        log.debug("REST request to delete Reinsurer : {}", id);
        reinsurerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/reinsurers?query=:query : search for the reinsurer corresponding
     * to the query.
     *
     * @param query the query of the reinsurer search
     * @return the result of the search
     */
    @GetMapping("/_search/reinsurers")
    @Timed
    public List<ReinsurerDTO> searchReinsurers(@RequestParam String query) {
        log.debug("REST request to search Reinsurers for query {}", query);
        return reinsurerService.search(query);
    }

}
