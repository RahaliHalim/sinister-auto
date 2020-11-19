package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefModeGestionService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefModeGestionDTO;
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
import java.util.Set;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing RefModeGestion.
 */
@RestController
@RequestMapping("/api")
public class RefModeGestionResource {

    private final Logger log = LoggerFactory.getLogger(RefModeGestionResource.class);

    private static final String ENTITY_NAME = "refModeGestion";

    private final RefModeGestionService refModeGestionService;

    public RefModeGestionResource(RefModeGestionService refModeGestionService) {
        this.refModeGestionService = refModeGestionService;
    }

    /**
     * POST  /ref-mode-gestions : Create a new refModeGestion.
     *
     * @param refModeGestionDTO the refModeGestionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refModeGestionDTO, or with status 400 (Bad Request) if the refModeGestion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-mode-gestions")
    @Timed
    public ResponseEntity<RefModeGestionDTO> createRefModeGestion(@Valid @RequestBody RefModeGestionDTO refModeGestionDTO) throws URISyntaxException {
        log.debug("REST request to save RefModeGestion : {}", refModeGestionDTO);
        if (refModeGestionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refModeGestion cannot already have an ID")).body(null);
        }
        RefModeGestionDTO result = refModeGestionService.save(refModeGestionDTO);
        return ResponseEntity.created(new URI("/api/ref-mode-gestions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-mode-gestions : Updates an existing refModeGestion.
     *
     * @param refModeGestionDTO the refModeGestionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refModeGestionDTO,
     * or with status 400 (Bad Request) if the refModeGestionDTO is not valid,
     * or with status 500 (Internal Server Error) if the refModeGestionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-mode-gestions")
    @Timed
    public ResponseEntity<RefModeGestionDTO> updateRefModeGestion(@Valid @RequestBody RefModeGestionDTO refModeGestionDTO) throws URISyntaxException {
        log.debug("REST request to update RefModeGestion : {}", refModeGestionDTO);
        if (refModeGestionDTO.getId() == null) {
            return createRefModeGestion(refModeGestionDTO);
        }
        RefModeGestionDTO result = refModeGestionService.save(refModeGestionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refModeGestionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-mode-gestions : get all the refModeGestions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refModeGestions in body
     */
    @GetMapping("/ref-mode-gestions")
    @Timed
    public ResponseEntity<List<RefModeGestionDTO>> getAllRefModeGestions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefModeGestions");
        Page<RefModeGestionDTO> page = refModeGestionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-mode-gestions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ref-mode-gestions/:id : get the "id" refModeGestion.
     *
     * @param id the id of the refModeGestionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refModeGestionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-mode-gestions/{id}")
    @Timed
    public ResponseEntity<RefModeGestionDTO> getRefModeGestion(@PathVariable Long id) {
        log.debug("REST request to get RefModeGestion : {}", id);
        RefModeGestionDTO refModeGestionDTO = refModeGestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refModeGestionDTO));
    }

    /**
     * DELETE  /ref-mode-gestions/:id : delete the "id" refModeGestion.
     *
     * @param id the id of the refModeGestionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-mode-gestions/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefModeGestion(@PathVariable Long id) {
        log.debug("REST request to delete RefModeGestion : {}", id);
        refModeGestionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ref-mode-gestions?query=:query : search for the refModeGestion corresponding
     * to the query.
     *
     * @param query the query of the refModeGestion search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-mode-gestions")
    @Timed
    public ResponseEntity<List<RefModeGestionDTO>> searchRefModeGestions(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefModeGestions for query {}", query);
        Page<RefModeGestionDTO> page = refModeGestionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-mode-gestions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * Get mode gestion list from client.
     *
     * @param clientId the client id
     * @return the result of the search
     */
    @GetMapping("/ref-mode-gestions/client/{clientId}")
    @Timed
    public ResponseEntity<Set<RefModeGestionDTO>> searchRefModeGestionByGaranties(@PathVariable Long clientId) {
        log.debug("REST request to search for a set of Refmodegestion for client {}", clientId);
        Set<RefModeGestionDTO> page = refModeGestionService.findModeGestionListByClient(clientId);
        return new ResponseEntity<Set<RefModeGestionDTO>>(page, HttpStatus.OK);
    }

}
