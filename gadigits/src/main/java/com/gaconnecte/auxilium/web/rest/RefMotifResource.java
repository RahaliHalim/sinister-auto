package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefMotifService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefMotifDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import com.gaconnecte.auxilium.service.LoggerService;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing RefMotif.
 */
@RestController
@RequestMapping("/api")
public class RefMotifResource {

    private final Logger log = LoggerFactory.getLogger(RefMotifResource.class);

    private static final String ENTITY_NAME = "refMotif";

    private final RefMotifService refMotifService;

    @Autowired
	private LoggerService loggerService;

    public RefMotifResource(RefMotifService refMotifService) {
        this.refMotifService = refMotifService;
    }

    /**
     * POST  /ref-motifs : Create a new refMotif.
     *
     * @param refMotifDTO the refMotifDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refMotifDTO, or with status 400 (Bad Request) if the refMotif has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-motifs")
    @Timed
    public ResponseEntity<RefMotifDTO> createRefMotif(@RequestBody RefMotifDTO refMotifDTO) throws URISyntaxException {
        log.debug("REST request to save RefMotif : {}", refMotifDTO);
        if (refMotifDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refMotif cannot already have an ID")).body(null);
        }
        RefMotifDTO result = refMotifService.save(refMotifDTO);
        return ResponseEntity.created(new URI("/api/ref-motifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-motifs : Updates an existing refMotif.
     *
     * @param refMotifDTO the refMotifDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refMotifDTO,
     * or with status 400 (Bad Request) if the refMotifDTO is not valid,
     * or with status 500 (Internal Server Error) if the refMotifDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-motifs")
    @Timed
    public ResponseEntity<RefMotifDTO> updateRefMotif(@RequestBody RefMotifDTO refMotifDTO) throws URISyntaxException {
        log.debug("REST request to update RefMotif : {}", refMotifDTO);
        if (refMotifDTO.getId() == null) {
            return createRefMotif(refMotifDTO);
        }
        RefMotifDTO result = refMotifService.save(refMotifDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refMotifDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-motifs : get all the refMotifs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refMotifs in body
     */
    @GetMapping("/ref-motifs")
    @Timed
    public ResponseEntity<List<RefMotifDTO>> getAllRefMotifs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefMotifs");
        Page<RefMotifDTO> page = refMotifService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-motifs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ref-motifs/:id : get the "id" refMotif.
     *
     * @param id the id of the refMotifDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refMotifDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-motifs/{id}")
    @Timed
    public ResponseEntity<RefMotifDTO> getRefMotif(@PathVariable Long id) {
        log.debug("REST request to get RefMotif : {}", id);
        RefMotifDTO refMotifDTO = refMotifService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refMotifDTO));
    }

    /**
     * DELETE  /ref-motifs/:id : delete the "id" refMotif.
     *
     * @param id the id of the refMotifDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-motifs/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefMotif(@PathVariable Long id) {
        log.debug("REST request to delete RefMotif : {}", id);
        refMotifService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ref-motifs?query=:query : search for the refMotif corresponding
     * to the query.
     *
     * @param query the query of the refMotif search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-motifs")
    @Timed
    public ResponseEntity<List<RefMotifDTO>> searchRefMotifs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefMotifs for query {}", query);
        Page<RefMotifDTO> page = refMotifService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-motifs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/ref-motifs/{type}/{etat}")
    @Timed
    public ResponseEntity<Set<RefMotifDTO>> findAllMotifsByTypAndEtat(@PathVariable String type, @PathVariable String etat){
        try {
            Set<RefMotifDTO> motifs = refMotifService.findAllMotifsByTypeAndEtat(type, etat);
            return new ResponseEntity<>(motifs, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
