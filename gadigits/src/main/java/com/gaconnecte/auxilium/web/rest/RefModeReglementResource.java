package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefModeReglementService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefModeReglementDTO;
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
 * REST controller for managing RefModeReglement.
 */
@RestController
@RequestMapping("/api")
public class RefModeReglementResource {

    private final Logger log = LoggerFactory.getLogger(RefModeReglementResource.class);

    private static final String ENTITY_NAME = "refModeReglement";

    private final RefModeReglementService refModeReglementService;

    public RefModeReglementResource(RefModeReglementService refModeReglementService) {
        this.refModeReglementService = refModeReglementService;
    }

    /**
     * POST  /ref-mode-reglements : Create a new refModeReglement.
     *
     * @param refModeReglementDTO the refModeReglementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refModeReglementDTO, or with status 400 (Bad Request) if the refModeReglement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-mode-reglements")
    @Timed
    public ResponseEntity<RefModeReglementDTO> createRefModeReglement(@Valid @RequestBody RefModeReglementDTO refModeReglementDTO) throws URISyntaxException {
        log.debug("REST request to save RefModeReglement : {}", refModeReglementDTO);
        if (refModeReglementDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refModeReglement cannot already have an ID")).body(null);
        }
        RefModeReglementDTO result = refModeReglementService.save(refModeReglementDTO);
        return ResponseEntity.created(new URI("/api/ref-mode-reglements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-mode-reglements : Updates an existing refModeReglement.
     *
     * @param refModeReglementDTO the refModeReglementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refModeReglementDTO,
     * or with status 400 (Bad Request) if the refModeReglementDTO is not valid,
     * or with status 500 (Internal Server Error) if the refModeReglementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-mode-reglements")
    @Timed
    public ResponseEntity<RefModeReglementDTO> updateRefModeReglement(@Valid @RequestBody RefModeReglementDTO refModeReglementDTO) throws URISyntaxException {
        log.debug("REST request to update RefModeReglement : {}", refModeReglementDTO);
        if (refModeReglementDTO.getId() == null) {
            return createRefModeReglement(refModeReglementDTO);
        }
        RefModeReglementDTO result = refModeReglementService.save(refModeReglementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refModeReglementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-mode-reglements : get all the refModeReglements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refModeReglements in body
     */
    @GetMapping("/ref-mode-reglements")
    @Timed
    public ResponseEntity<List<RefModeReglementDTO>> getAllRefModeReglements(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefModeReglements");
        Page<RefModeReglementDTO> page = refModeReglementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-mode-reglements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ref-mode-reglements/:id : get the "id" refModeReglement.
     *
     * @param id the id of the refModeReglementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refModeReglementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-mode-reglements/{id}")
    @Timed
    public ResponseEntity<RefModeReglementDTO> getRefModeReglement(@PathVariable Long id) {
        log.debug("REST request to get RefModeReglement : {}", id);
        RefModeReglementDTO refModeReglementDTO = refModeReglementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refModeReglementDTO));
    }

    /**
     * DELETE  /ref-mode-reglements/:id : delete the "id" refModeReglement.
     *
     * @param id the id of the refModeReglementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-mode-reglements/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefModeReglement(@PathVariable Long id) {
        log.debug("REST request to delete RefModeReglement : {}", id);
        refModeReglementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ref-mode-reglements?query=:query : search for the refModeReglement corresponding
     * to the query.
     *
     * @param query the query of the refModeReglement search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-mode-reglements")
    @Timed
    public ResponseEntity<List<RefModeReglementDTO>> searchRefModeReglements(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefModeReglements for query {}", query);
        Page<RefModeReglementDTO> page = refModeReglementService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-mode-reglements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
