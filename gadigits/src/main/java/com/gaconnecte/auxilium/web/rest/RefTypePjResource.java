package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefTypePjService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefTypePjDTO;
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
 * REST controller for managing RefTypePj.
 */
@RestController
@RequestMapping("/api")
public class RefTypePjResource {

    private final Logger log = LoggerFactory.getLogger(RefTypePjResource.class);

    private static final String ENTITY_NAME = "refTypePj";

    private final RefTypePjService refTypePjService;

    public RefTypePjResource(RefTypePjService refTypePjService) {
        this.refTypePjService = refTypePjService;
    }

    /**
     * POST  /ref-type-pjs : Create a new refTypePj.
     *
     * @param refTypePjDTO the refTypePjDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refTypePjDTO, or with status 400 (Bad Request) if the refTypePj has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-type-pjs")
    @Timed
    public ResponseEntity<RefTypePjDTO> createRefTypePj(@Valid @RequestBody RefTypePjDTO refTypePjDTO) throws URISyntaxException {
        log.debug("REST request to save RefTypePj : {}", refTypePjDTO);
        if (refTypePjDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refTypePj cannot already have an ID")).body(null);
        }
        RefTypePjDTO result = refTypePjService.save(refTypePjDTO);
        return ResponseEntity.created(new URI("/api/ref-type-pjs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-type-pjs : Updates an existing refTypePj.
     *
     * @param refTypePjDTO the refTypePjDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refTypePjDTO,
     * or with status 400 (Bad Request) if the refTypePjDTO is not valid,
     * or with status 500 (Internal Server Error) if the refTypePjDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-type-pjs")
    @Timed
    public ResponseEntity<RefTypePjDTO> updateRefTypePj(@Valid @RequestBody RefTypePjDTO refTypePjDTO) throws URISyntaxException {
        log.debug("REST request to update RefTypePj : {}", refTypePjDTO);
        if (refTypePjDTO.getId() == null) {
            return createRefTypePj(refTypePjDTO);
        }
        RefTypePjDTO result = refTypePjService.save(refTypePjDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refTypePjDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-type-pjs : get all the refTypePjs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refTypePjs in body
     */
    @GetMapping("/ref-type-pjs")
    @Timed
    public ResponseEntity<List<RefTypePjDTO>> getAllRefTypePjs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefTypePjs");
        Page<RefTypePjDTO> page = refTypePjService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-type-pjs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ref-type-pjs/:id : get the "id" refTypePj.
     *
     * @param id the id of the refTypePjDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refTypePjDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-type-pjs/{id}")
    @Timed
    public ResponseEntity<RefTypePjDTO> getRefTypePj(@PathVariable Long id) {
        log.debug("REST request to get RefTypePj : {}", id);
        RefTypePjDTO refTypePjDTO = refTypePjService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refTypePjDTO));
    }

    /**
     * DELETE  /ref-type-pjs/:id : delete the "id" refTypePj.
     *
     * @param id the id of the refTypePjDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-type-pjs/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefTypePj(@PathVariable Long id) {
        log.debug("REST request to delete RefTypePj : {}", id);
        refTypePjService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ref-type-pjs?query=:query : search for the refTypePj corresponding
     * to the query.
     *
     * @param query the query of the refTypePj search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-type-pjs")
    @Timed
    public ResponseEntity<List<RefTypePjDTO>> searchRefTypePjs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefTypePjs for query {}", query);
        Page<RefTypePjDTO> page = refTypePjService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-type-pjs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
