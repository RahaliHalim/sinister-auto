package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.DesignationNatureContratService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.DesignationNatureContratDTO;
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
 * REST controller for managing DesignationNatureContrat.
 */
@RestController
@RequestMapping("/api")
public class DesignationNatureContratResource {

    private final Logger log = LoggerFactory.getLogger(DesignationNatureContratResource.class);

    private static final String ENTITY_NAME = "designationNatureContrat";

    private final DesignationNatureContratService designationNatureContratService;

    public DesignationNatureContratResource(DesignationNatureContratService designationNatureContratService) {
        this.designationNatureContratService = designationNatureContratService;
    }

    /**
     * POST  /designation-nature-contrats : Create a new designationNatureContrat.
     *
     * @param designationNatureContratDTO the designationNatureContratDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new designationNatureContratDTO, or with status 400 (Bad Request) if the designationNatureContrat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/designation-nature-contrats")
    @Timed
    public ResponseEntity<DesignationNatureContratDTO> createDesignationNatureContrat(@Valid @RequestBody DesignationNatureContratDTO designationNatureContratDTO) throws URISyntaxException {
        log.debug("REST request to save DesignationNatureContrat : {}", designationNatureContratDTO);
        if (designationNatureContratDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new designationNatureContrat cannot already have an ID")).body(null);
        }
        DesignationNatureContratDTO result = designationNatureContratService.save(designationNatureContratDTO);
        return ResponseEntity.created(new URI("/api/designation-nature-contrats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /designation-nature-contrats : Updates an existing designationNatureContrat.
     *
     * @param designationNatureContratDTO the designationNatureContratDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated designationNatureContratDTO,
     * or with status 400 (Bad Request) if the designationNatureContratDTO is not valid,
     * or with status 500 (Internal Server Error) if the designationNatureContratDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/designation-nature-contrats")
    @Timed
    public ResponseEntity<DesignationNatureContratDTO> updateDesignationNatureContrat(@Valid @RequestBody DesignationNatureContratDTO designationNatureContratDTO) throws URISyntaxException {
        log.debug("REST request to update DesignationNatureContrat : {}", designationNatureContratDTO);
        if (designationNatureContratDTO.getId() == null) {
            return createDesignationNatureContrat(designationNatureContratDTO);
        }
        DesignationNatureContratDTO result = designationNatureContratService.save(designationNatureContratDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, designationNatureContratDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /designation-nature-contrats : get all the designationNatureContrats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of designationNatureContrats in body
     */
    @GetMapping("/designation-nature-contrats")
    @Timed
    public ResponseEntity<List<DesignationNatureContratDTO>> getAllDesignationNatureContrats(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DesignationNatureContrats");
        Page<DesignationNatureContratDTO> page = designationNatureContratService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/designation-nature-contrats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /designation-nature-contrats/:id : get the "id" designationNatureContrat.
     *
     * @param id the id of the designationNatureContratDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the designationNatureContratDTO, or with status 404 (Not Found)
     */
    @GetMapping("/designation-nature-contrats/{id}")
    @Timed
    public ResponseEntity<DesignationNatureContratDTO> getDesignationNatureContrat(@PathVariable Long id) {
        log.debug("REST request to get DesignationNatureContrat : {}", id);
        DesignationNatureContratDTO designationNatureContratDTO = designationNatureContratService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(designationNatureContratDTO));
    }

    /**
     * DELETE  /designation-nature-contrats/:id : delete the "id" designationNatureContrat.
     *
     * @param id the id of the designationNatureContratDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/designation-nature-contrats/{id}")
    @Timed
    public ResponseEntity<Void> deleteDesignationNatureContrat(@PathVariable Long id) {
        log.debug("REST request to delete DesignationNatureContrat : {}", id);
        designationNatureContratService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/designation-nature-contrats?query=:query : search for the designationNatureContrat corresponding
     * to the query.
     *
     * @param query the query of the designationNatureContrat search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/designation-nature-contrats")
    @Timed
    public ResponseEntity<List<DesignationNatureContratDTO>> searchDesignationNatureContrats(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of DesignationNatureContrats for query {}", query);
        Page<DesignationNatureContratDTO> page = designationNatureContratService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/designation-nature-contrats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
