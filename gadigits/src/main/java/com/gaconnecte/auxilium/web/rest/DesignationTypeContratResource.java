package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.DesignationTypeContratService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.DesignationTypeContratDTO;
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
 * REST controller for managing DesignationTypeContrat.
 */
@RestController
@RequestMapping("/api")
public class DesignationTypeContratResource {

    private final Logger log = LoggerFactory.getLogger(DesignationTypeContratResource.class);

    private static final String ENTITY_NAME = "designationTypeContrat";

    private final DesignationTypeContratService designationTypeContratService;

    public DesignationTypeContratResource(DesignationTypeContratService designationTypeContratService) {
        this.designationTypeContratService = designationTypeContratService;
    }

    /**
     * POST  /designation-type-contrats : Create a new designationTypeContrat.
     *
     * @param designationTypeContratDTO the designationTypeContratDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new designationTypeContratDTO, or with status 400 (Bad Request) if the designationTypeContrat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/designation-type-contrats")
    @Timed
    public ResponseEntity<DesignationTypeContratDTO> createDesignationTypeContrat(@Valid @RequestBody DesignationTypeContratDTO designationTypeContratDTO) throws URISyntaxException {
        log.debug("REST request to save DesignationTypeContrat : {}", designationTypeContratDTO);
        if (designationTypeContratDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new designationTypeContrat cannot already have an ID")).body(null);
        }
        DesignationTypeContratDTO result = designationTypeContratService.save(designationTypeContratDTO);
        return ResponseEntity.created(new URI("/api/designation-type-contrats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /designation-type-contrats : Updates an existing designationTypeContrat.
     *
     * @param designationTypeContratDTO the designationTypeContratDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated designationTypeContratDTO,
     * or with status 400 (Bad Request) if the designationTypeContratDTO is not valid,
     * or with status 500 (Internal Server Error) if the designationTypeContratDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/designation-type-contrats")
    @Timed
    public ResponseEntity<DesignationTypeContratDTO> updateDesignationTypeContrat(@Valid @RequestBody DesignationTypeContratDTO designationTypeContratDTO) throws URISyntaxException {
        log.debug("REST request to update DesignationTypeContrat : {}", designationTypeContratDTO);
        if (designationTypeContratDTO.getId() == null) {
            return createDesignationTypeContrat(designationTypeContratDTO);
        }
        DesignationTypeContratDTO result = designationTypeContratService.save(designationTypeContratDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, designationTypeContratDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /designation-type-contrats : get all the designationTypeContrats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of designationTypeContrats in body
     */
    @GetMapping("/designation-type-contrats")
    @Timed
    public ResponseEntity<List<DesignationTypeContratDTO>> getAllDesignationTypeContrats(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DesignationTypeContrats");
        Page<DesignationTypeContratDTO> page = designationTypeContratService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/designation-type-contrats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /designation-type-contrats/:id : get the "id" designationTypeContrat.
     *
     * @param id the id of the designationTypeContratDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the designationTypeContratDTO, or with status 404 (Not Found)
     */
    @GetMapping("/designation-type-contrats/{id}")
    @Timed
    public ResponseEntity<DesignationTypeContratDTO> getDesignationTypeContrat(@PathVariable Long id) {
        log.debug("REST request to get DesignationTypeContrat : {}", id);
        DesignationTypeContratDTO designationTypeContratDTO = designationTypeContratService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(designationTypeContratDTO));
    }

    /**
     * DELETE  /designation-type-contrats/:id : delete the "id" designationTypeContrat.
     *
     * @param id the id of the designationTypeContratDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/designation-type-contrats/{id}")
    @Timed
    public ResponseEntity<Void> deleteDesignationTypeContrat(@PathVariable Long id) {
        log.debug("REST request to delete DesignationTypeContrat : {}", id);
        designationTypeContratService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/designation-type-contrats?query=:query : search for the designationTypeContrat corresponding
     * to the query.
     *
     * @param query the query of the designationTypeContrat search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/designation-type-contrats")
    @Timed
    public ResponseEntity<List<DesignationTypeContratDTO>> searchDesignationTypeContrats(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of DesignationTypeContrats for query {}", query);
        Page<DesignationTypeContratDTO> page = designationTypeContratService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/designation-type-contrats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
