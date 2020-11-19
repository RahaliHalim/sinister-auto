package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.DesignationFractionnementService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.DesignationFractionnementDTO;
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
 * REST controller for managing DesignationFractionnement.
 */
@RestController
@RequestMapping("/api")
public class DesignationFractionnementResource {

    private final Logger log = LoggerFactory.getLogger(DesignationFractionnementResource.class);

    private static final String ENTITY_NAME = "designationFractionnement";

    private final DesignationFractionnementService designationFractionnementService;

    public DesignationFractionnementResource(DesignationFractionnementService designationFractionnementService) {
        this.designationFractionnementService = designationFractionnementService;
    }

    /**
     * POST  /designation-fractionnements : Create a new designationFractionnement.
     *
     * @param designationFractionnementDTO the designationFractionnementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new designationFractionnementDTO, or with status 400 (Bad Request) if the designationFractionnement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/designation-fractionnements")
    @Timed
    public ResponseEntity<DesignationFractionnementDTO> createDesignationFractionnement(@Valid @RequestBody DesignationFractionnementDTO designationFractionnementDTO) throws URISyntaxException {
        log.debug("REST request to save DesignationFractionnement : {}", designationFractionnementDTO);
        if (designationFractionnementDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new designationFractionnement cannot already have an ID")).body(null);
        }
        DesignationFractionnementDTO result = designationFractionnementService.save(designationFractionnementDTO);
        return ResponseEntity.created(new URI("/api/designation-fractionnements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /designation-fractionnements : Updates an existing designationFractionnement.
     *
     * @param designationFractionnementDTO the designationFractionnementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated designationFractionnementDTO,
     * or with status 400 (Bad Request) if the designationFractionnementDTO is not valid,
     * or with status 500 (Internal Server Error) if the designationFractionnementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/designation-fractionnements")
    @Timed
    public ResponseEntity<DesignationFractionnementDTO> updateDesignationFractionnement(@Valid @RequestBody DesignationFractionnementDTO designationFractionnementDTO) throws URISyntaxException {
        log.debug("REST request to update DesignationFractionnement : {}", designationFractionnementDTO);
        if (designationFractionnementDTO.getId() == null) {
            return createDesignationFractionnement(designationFractionnementDTO);
        }
        DesignationFractionnementDTO result = designationFractionnementService.save(designationFractionnementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, designationFractionnementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /designation-fractionnements : get all the designationFractionnements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of designationFractionnements in body
     */
    @GetMapping("/designation-fractionnements")
    @Timed
    public ResponseEntity<List<DesignationFractionnementDTO>> getAllDesignationFractionnements(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DesignationFractionnements");
        Page<DesignationFractionnementDTO> page = designationFractionnementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/designation-fractionnements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /designation-fractionnements/:id : get the "id" designationFractionnement.
     *
     * @param id the id of the designationFractionnementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the designationFractionnementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/designation-fractionnements/{id}")
    @Timed
    public ResponseEntity<DesignationFractionnementDTO> getDesignationFractionnement(@PathVariable Long id) {
        log.debug("REST request to get DesignationFractionnement : {}", id);
        DesignationFractionnementDTO designationFractionnementDTO = designationFractionnementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(designationFractionnementDTO));
    }

    /**
     * DELETE  /designation-fractionnements/:id : delete the "id" designationFractionnement.
     *
     * @param id the id of the designationFractionnementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/designation-fractionnements/{id}")
    @Timed
    public ResponseEntity<Void> deleteDesignationFractionnement(@PathVariable Long id) {
        log.debug("REST request to delete DesignationFractionnement : {}", id);
        designationFractionnementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/designation-fractionnements?query=:query : search for the designationFractionnement corresponding
     * to the query.
     *
     * @param query the query of the designationFractionnement search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/designation-fractionnements")
    @Timed
    public ResponseEntity<List<DesignationFractionnementDTO>> searchDesignationFractionnements(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of DesignationFractionnements for query {}", query);
        Page<DesignationFractionnementDTO> page = designationFractionnementService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/designation-fractionnements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
