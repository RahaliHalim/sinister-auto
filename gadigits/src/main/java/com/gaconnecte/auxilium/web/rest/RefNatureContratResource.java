package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefNatureContratService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefNatureContratDTO;
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
 * REST controller for managing RefNatureContrat.
 */
@RestController
@RequestMapping("/api")
public class RefNatureContratResource {

    private final Logger log = LoggerFactory.getLogger(RefNatureContratResource.class);

    private static final String ENTITY_NAME = "refNatureContrat";

    private final RefNatureContratService refNatureContratService;

    public RefNatureContratResource(RefNatureContratService refNatureContratService) {
        this.refNatureContratService = refNatureContratService;
    }

    /**
     * POST  /ref-nature-contrats : Create a new refNatureContrat.
     *
     * @param refNatureContratDTO the refNatureContratDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refNatureContratDTO, or with status 400 (Bad Request) if the refNatureContrat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-nature-contrats")
    @Timed
    public ResponseEntity<RefNatureContratDTO> createRefNatureContrat(@Valid @RequestBody RefNatureContratDTO refNatureContratDTO) throws URISyntaxException {
        log.debug("REST request to save RefNatureContrat : {}", refNatureContratDTO);
        if (refNatureContratDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refNatureContrat cannot already have an ID")).body(null);
        }
        RefNatureContratDTO result = refNatureContratService.save(refNatureContratDTO);
        return ResponseEntity.created(new URI("/api/ref-nature-contrats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-nature-contrats : Updates an existing refNatureContrat.
     *
     * @param refNatureContratDTO the refNatureContratDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refNatureContratDTO,
     * or with status 400 (Bad Request) if the refNatureContratDTO is not valid,
     * or with status 500 (Internal Server Error) if the refNatureContratDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-nature-contrats")
    @Timed
    public ResponseEntity<RefNatureContratDTO> updateRefNatureContrat(@Valid @RequestBody RefNatureContratDTO refNatureContratDTO) throws URISyntaxException {
        log.debug("REST request to update RefNatureContrat : {}", refNatureContratDTO);
        if (refNatureContratDTO.getId() == null) {
            return createRefNatureContrat(refNatureContratDTO);
        }
        RefNatureContratDTO result = refNatureContratService.save(refNatureContratDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refNatureContratDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-nature-contrats : get all the refNatureContrats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refNatureContrats in body
     */
    @GetMapping("/ref-nature-contrats")
    @Timed
    public ResponseEntity<List<RefNatureContratDTO>> getAllRefNatureContrats(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefNatureContrats");
        Page<RefNatureContratDTO> page = refNatureContratService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-nature-contrats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ref-nature-contrats/:id : get the "id" refNatureContrat.
     *
     * @param id the id of the refNatureContratDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refNatureContratDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-nature-contrats/{id}")
    @Timed
    public ResponseEntity<RefNatureContratDTO> getRefNatureContrat(@PathVariable Long id) {
        log.debug("REST request to get RefNatureContrat : {}", id);
        RefNatureContratDTO refNatureContratDTO = refNatureContratService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refNatureContratDTO));
    }

    /**
     * DELETE  /ref-nature-contrats/:id : delete the "id" refNatureContrat.
     *
     * @param id the id of the refNatureContratDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-nature-contrats/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefNatureContrat(@PathVariable Long id) {
        log.debug("REST request to delete RefNatureContrat : {}", id);
        refNatureContratService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ref-nature-contrats?query=:query : search for the refNatureContrat corresponding
     * to the query.
     *
     * @param query the query of the refNatureContrat search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-nature-contrats")
    @Timed
    public ResponseEntity<List<RefNatureContratDTO>> searchRefNatureContrats(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefNatureContrats for query {}", query);
        Page<RefNatureContratDTO> page = refNatureContratService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-nature-contrats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
