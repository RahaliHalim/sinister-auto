package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefTypeInterventionService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefTypeInterventionDTO;
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
import java.util.Set;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing RefTypeIntervention.
 */
@RestController
@RequestMapping("/api")
public class RefTypeInterventionResource {

    private final Logger log = LoggerFactory.getLogger(RefTypeInterventionResource.class);

    private static final String ENTITY_NAME = "refTypeIntervention";

    private final RefTypeInterventionService refTypeInterventionService;

    public RefTypeInterventionResource(RefTypeInterventionService refTypeInterventionService) {
        this.refTypeInterventionService = refTypeInterventionService;
    }

    /**
     * POST  /ref-type-interventions : Create a new refTypeIntervention.
     *
     * @param refTypeInterventionDTO the refTypeInterventionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refTypeInterventionDTO, or with status 400 (Bad Request) if the refTypeIntervention has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-type-interventions")
    @Timed
    public ResponseEntity<RefTypeInterventionDTO> createRefTypeIntervention(@Valid @RequestBody RefTypeInterventionDTO refTypeInterventionDTO) throws URISyntaxException {
        log.debug("REST request to save RefTypeIntervention : {}", refTypeInterventionDTO);
        if (refTypeInterventionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refTypeIntervention cannot already have an ID")).body(null);
        }
        RefTypeInterventionDTO result = refTypeInterventionService.save(refTypeInterventionDTO);
        return ResponseEntity.created(new URI("/api/ref-type-interventions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-type-interventions : Updates an existing refTypeIntervention.
     *
     * @param refTypeInterventionDTO the refTypeInterventionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refTypeInterventionDTO,
     * or with status 400 (Bad Request) if the refTypeInterventionDTO is not valid,
     * or with status 500 (Internal Server Error) if the refTypeInterventionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-type-interventions")
    @Timed
    public ResponseEntity<RefTypeInterventionDTO> updateRefTypeIntervention(@Valid @RequestBody RefTypeInterventionDTO refTypeInterventionDTO) throws URISyntaxException {
        log.debug("REST request to update RefTypeIntervention : {}", refTypeInterventionDTO);
        if (refTypeInterventionDTO.getId() == null) {
            return createRefTypeIntervention(refTypeInterventionDTO);
        }
        RefTypeInterventionDTO result = refTypeInterventionService.save(refTypeInterventionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refTypeInterventionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-type-interventions : get all the refTypeInterventions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refTypeInterventions in body
     */
    @GetMapping("/ref-type-interventions")
    @Timed
    public ResponseEntity<List<RefTypeInterventionDTO>> getAllRefTypeInterventions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefTypeInterventions");
        Page<RefTypeInterventionDTO> page = refTypeInterventionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-type-interventions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

     @GetMapping("/ref-type-interventions/type/{type}")
    @Timed
    public Set<RefTypeInterventionDTO> getRefTypeInterventionsByType(@PathVariable Integer type) {
        log.debug("REST request to get RefTypeInterventions by Type: {}", type);
        Set<RefTypeInterventionDTO> liste = refTypeInterventionService.findByType(type);
        
        return liste;
    }


    /**
     * GET  /ref-type-interventions/:id : get the "id" refTypeIntervention.
     *
     * @param id the id of the refTypeInterventionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refTypeInterventionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-type-interventions/{id}")
    @Timed
    public ResponseEntity<RefTypeInterventionDTO> getRefTypeIntervention(@PathVariable Long id) {
        log.debug("REST request to get RefTypeIntervention : {}", id);
        RefTypeInterventionDTO refTypeInterventionDTO = refTypeInterventionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refTypeInterventionDTO));
    }

    /**
     * DELETE  /ref-type-interventions/:id : delete the "id" refTypeIntervention.
     *
     * @param id the id of the refTypeInterventionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-type-interventions/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefTypeIntervention(@PathVariable Long id) {
        log.debug("REST request to delete RefTypeIntervention : {}", id);
        refTypeInterventionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ref-type-interventions?query=:query : search for the refTypeIntervention corresponding
     * to the query.
     *
     * @param query the query of the refTypeIntervention search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-type-interventions")
    @Timed
    public ResponseEntity<List<RefTypeInterventionDTO>> searchRefTypeInterventions(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefTypeInterventions for query {}", query);
        Page<RefTypeInterventionDTO> page = refTypeInterventionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-type-interventions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
