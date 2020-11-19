package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefEtatDossierService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefEtatDossierDTO;
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
 * REST controller for managing RefEtatDossier.
 */
@RestController
@RequestMapping("/api")
public class RefEtatDossierResource {

    private final Logger log = LoggerFactory.getLogger(RefEtatDossierResource.class);

    private static final String ENTITY_NAME = "refEtatDossier";

    private final RefEtatDossierService refEtatDossierService;

    public RefEtatDossierResource(RefEtatDossierService refEtatDossierService) {
        this.refEtatDossierService = refEtatDossierService;
    }

    /**
     * POST  /ref-etat-dossiers : Create a new refEtatDossier.
     *
     * @param refEtatDossierDTO the refEtatDossierDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refEtatDossierDTO, or with status 400 (Bad Request) if the refEtatDossier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-etat-dossiers")
    @Timed
    public ResponseEntity<RefEtatDossierDTO> createRefEtatDossier(@Valid @RequestBody RefEtatDossierDTO refEtatDossierDTO) throws URISyntaxException {
        log.debug("REST request to save RefEtatDossier : {}", refEtatDossierDTO);
        if (refEtatDossierDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refEtatDossier cannot already have an ID")).body(null);
        }
        RefEtatDossierDTO result = refEtatDossierService.save(refEtatDossierDTO);
        return ResponseEntity.created(new URI("/api/ref-etat-dossiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-etat-dossiers : Updates an existing refEtatDossier.
     *
     * @param refEtatDossierDTO the refEtatDossierDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refEtatDossierDTO,
     * or with status 400 (Bad Request) if the refEtatDossierDTO is not valid,
     * or with status 500 (Internal Server Error) if the refEtatDossierDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-etat-dossiers")
    @Timed
    public ResponseEntity<RefEtatDossierDTO> updateRefEtatDossier(@Valid @RequestBody RefEtatDossierDTO refEtatDossierDTO) throws URISyntaxException {
        log.debug("REST request to update RefEtatDossier : {}", refEtatDossierDTO);
        if (refEtatDossierDTO.getId() == null) {
            return createRefEtatDossier(refEtatDossierDTO);
        }
        RefEtatDossierDTO result = refEtatDossierService.save(refEtatDossierDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refEtatDossierDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-etat-dossiers : get all the refEtatDossiers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refEtatDossiers in body
     */
    @GetMapping("/ref-etat-dossiers")
    @Timed
    public ResponseEntity<List<RefEtatDossierDTO>> getAllRefEtatDossiers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefEtatDossiers");
        Page<RefEtatDossierDTO> page = refEtatDossierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-etat-dossiers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ref-etat-dossiers/:id : get the "id" refEtatDossier.
     *
     * @param id the id of the refEtatDossierDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refEtatDossierDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-etat-dossiers/{id}")
    @Timed
    public ResponseEntity<RefEtatDossierDTO> getRefEtatDossier(@PathVariable Long id) {
        log.debug("REST request to get RefEtatDossier : {}", id);
        RefEtatDossierDTO refEtatDossierDTO = refEtatDossierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refEtatDossierDTO));
    }

    /**
     * DELETE  /ref-etat-dossiers/:id : delete the "id" refEtatDossier.
     *
     * @param id the id of the refEtatDossierDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-etat-dossiers/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefEtatDossier(@PathVariable Long id) {
        log.debug("REST request to delete RefEtatDossier : {}", id);
        refEtatDossierService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ref-etat-dossiers?query=:query : search for the refEtatDossier corresponding
     * to the query.
     *
     * @param query the query of the refEtatDossier search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-etat-dossiers")
    @Timed
    public ResponseEntity<List<RefEtatDossierDTO>> searchRefEtatDossiers(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefEtatDossiers for query {}", query);
        Page<RefEtatDossierDTO> page = refEtatDossierService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-etat-dossiers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
