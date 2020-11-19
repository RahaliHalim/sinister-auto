package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.DesignationUsageContratService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.DesignationUsageContratDTO;
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
 * REST controller for managing DesignationUsageContrat.
 */
@RestController
@RequestMapping("/api")
public class DesignationUsageContratResource {

    private final Logger log = LoggerFactory.getLogger(DesignationUsageContratResource.class);

    private static final String ENTITY_NAME = "designationUsageContrat";

    private final DesignationUsageContratService designationUsageContratService;

    public DesignationUsageContratResource(DesignationUsageContratService designationUsageContratService) {
        this.designationUsageContratService = designationUsageContratService;
    }

    /**
     * POST  /designation-usage-contrats : Create a new designationUsageContrat.
     *
     * @param designationUsageContratDTO the designationUsageContratDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new designationUsageContratDTO, or with status 400 (Bad Request) if the designationUsageContrat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/designation-usage-contrats")
    @Timed
    public ResponseEntity<DesignationUsageContratDTO> createDesignationUsageContrat(@Valid @RequestBody DesignationUsageContratDTO designationUsageContratDTO) throws URISyntaxException {
        log.debug("REST request to save DesignationUsageContrat : {}", designationUsageContratDTO);
        if (designationUsageContratDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new designationUsageContrat cannot already have an ID")).body(null);
        }
        DesignationUsageContratDTO result = designationUsageContratService.save(designationUsageContratDTO);
        return ResponseEntity.created(new URI("/api/designation-usage-contrats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /designation-usage-contrats : Updates an existing designationUsageContrat.
     *
     * @param designationUsageContratDTO the designationUsageContratDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated designationUsageContratDTO,
     * or with status 400 (Bad Request) if the designationUsageContratDTO is not valid,
     * or with status 500 (Internal Server Error) if the designationUsageContratDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/designation-usage-contrats")
    @Timed
    public ResponseEntity<DesignationUsageContratDTO> updateDesignationUsageContrat(@Valid @RequestBody DesignationUsageContratDTO designationUsageContratDTO) throws URISyntaxException {
        log.debug("REST request to update DesignationUsageContrat : {}", designationUsageContratDTO);
        if (designationUsageContratDTO.getId() == null) {
            return createDesignationUsageContrat(designationUsageContratDTO);
        }
        DesignationUsageContratDTO result = designationUsageContratService.save(designationUsageContratDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, designationUsageContratDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /designation-usage-contrats : get all the designationUsageContrats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of designationUsageContrats in body
     */
    @GetMapping("/designation-usage-contrats")
    @Timed
    public ResponseEntity<List<DesignationUsageContratDTO>> getAllDesignationUsageContrats(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DesignationUsageContrats");
        Page<DesignationUsageContratDTO> page = designationUsageContratService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/designation-usage-contrats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /designation-usage-contrats/:id : get the "id" designationUsageContrat.
     *
     * @param id the id of the designationUsageContratDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the designationUsageContratDTO, or with status 404 (Not Found)
     */
    @GetMapping("/designation-usage-contrats/{id}")
    @Timed
    public ResponseEntity<DesignationUsageContratDTO> getDesignationUsageContrat(@PathVariable Long id) {
        log.debug("REST request to get DesignationUsageContrat : {}", id);
        DesignationUsageContratDTO designationUsageContratDTO = designationUsageContratService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(designationUsageContratDTO));
    }

    /**
     * DELETE  /designation-usage-contrats/:id : delete the "id" designationUsageContrat.
     *
     * @param id the id of the designationUsageContratDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/designation-usage-contrats/{id}")
    @Timed
    public ResponseEntity<Void> deleteDesignationUsageContrat(@PathVariable Long id) {
        log.debug("REST request to delete DesignationUsageContrat : {}", id);
        designationUsageContratService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/designation-usage-contrats?query=:query : search for the designationUsageContrat corresponding
     * to the query.
     *
     * @param query the query of the designationUsageContrat search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/designation-usage-contrats")
    @Timed
    public ResponseEntity<List<DesignationUsageContratDTO>> searchDesignationUsageContrats(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of DesignationUsageContrats for query {}", query);
        Page<DesignationUsageContratDTO> page = designationUsageContratService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/designation-usage-contrats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
