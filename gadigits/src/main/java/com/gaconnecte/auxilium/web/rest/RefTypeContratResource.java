package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefTypeContratService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefTypeContratDTO;
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
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing RefTypeContrat.
 */
@RestController
@RequestMapping("/api")
public class RefTypeContratResource {

    private final Logger log = LoggerFactory.getLogger(RefTypeContratResource.class);

    private static final String ENTITY_NAME = "refTypeContrat";

    private final RefTypeContratService refTypeContratService;

    public RefTypeContratResource(RefTypeContratService refTypeContratService) {
        this.refTypeContratService = refTypeContratService;
    }

    /**
     * POST  /ref-type-contrats : Create a new refTypeContrat.
     *
     * @param refTypeContratDTO the refTypeContratDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refTypeContratDTO, or with status 400 (Bad Request) if the refTypeContrat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-type-contrats")
    @Timed
    public ResponseEntity<RefTypeContratDTO> createRefTypeContrat(@Valid @RequestBody RefTypeContratDTO refTypeContratDTO) throws URISyntaxException {
        log.debug("REST request to save RefTypeContrat : {}", refTypeContratDTO);
        if (refTypeContratDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refTypeContrat cannot already have an ID")).body(null);
        }
        RefTypeContratDTO result = refTypeContratService.save(refTypeContratDTO);
        return ResponseEntity.created(new URI("/api/ref-type-contrats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-type-contrats : Updates an existing refTypeContrat.
     *
     * @param refTypeContratDTO the refTypeContratDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refTypeContratDTO,
     * or with status 400 (Bad Request) if the refTypeContratDTO is not valid,
     * or with status 500 (Internal Server Error) if the refTypeContratDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-type-contrats")
    @Timed
    public ResponseEntity<RefTypeContratDTO> updateRefTypeContrat(@Valid @RequestBody RefTypeContratDTO refTypeContratDTO) throws URISyntaxException {
        log.debug("REST request to update RefTypeContrat : {}", refTypeContratDTO);
        if (refTypeContratDTO.getId() == null) {
            return createRefTypeContrat(refTypeContratDTO);
        }
        RefTypeContratDTO result = refTypeContratService.save(refTypeContratDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refTypeContratDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-type-contrats : get all the refTypeContrats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refTypeContrats in body
     */
    @GetMapping("/ref-type-contrats")
    @Timed
    public ResponseEntity<List<RefTypeContratDTO>> getAllRefTypeContrats(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefTypeContrats");
        Page<RefTypeContratDTO> page = refTypeContratService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-type-contrats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ref-type-contrats/:id : get the "id" refTypeContrat.
     *
     * @param id the id of the refTypeContratDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refTypeContratDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-type-contrats/{id}")
    @Timed
    public ResponseEntity<RefTypeContratDTO> getRefTypeContrat(@PathVariable Long id) {
        log.debug("REST request to get RefTypeContrat : {}", id);
        RefTypeContratDTO refTypeContratDTO = refTypeContratService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refTypeContratDTO));
    }

    /**
     * DELETE  /ref-type-contrats/:id : delete the "id" refTypeContrat.
     *
     * @param id the id of the refTypeContratDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-type-contrats/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefTypeContrat(@PathVariable Long id) {
        log.debug("REST request to delete RefTypeContrat : {}", id);
        refTypeContratService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ref-type-contrats?query=:query : search for the refTypeContrat corresponding
     * to the query.
     *
     * @param query the query of the refTypeContrat search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-type-contrats")
    @Timed
    public ResponseEntity<List<RefTypeContratDTO>> searchRefTypeContrats(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefTypeContrats for query {}", query);
        Page<RefTypeContratDTO> page = refTypeContratService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-type-contrats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
