package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.FournisseurService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.FournisseurDTO;
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
 * REST controller for managing Fournisseur.
 */
@RestController
@RequestMapping("/api")
public class FournisseurResource {

    private final Logger log = LoggerFactory.getLogger(FournisseurResource.class);

    private static final String ENTITY_NAME = "fournisseur";

    private final FournisseurService fournisseurService;

    public FournisseurResource(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    /**
     * POST  /fournisseurs : Create a new fournisseur.
     *
     * @param fournisseurDTO the fournisseurDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fournisseurDTO, or with status 400 (Bad Request) if the fournisseur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fournisseurs")
    @Timed
    public ResponseEntity<FournisseurDTO> createFournisseur(@Valid @RequestBody FournisseurDTO fournisseurDTO) throws URISyntaxException {
        log.debug("REST request to save Fournisseur : {}", fournisseurDTO);
        if (fournisseurDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fournisseur cannot already have an ID")).body(null);
        }
        FournisseurDTO result = fournisseurService.save(fournisseurDTO);
        return ResponseEntity.created(new URI("/api/fournisseurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fournisseurs : Updates an existing fournisseur.
     *
     * @param fournisseurDTO the fournisseurDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fournisseurDTO,
     * or with status 400 (Bad Request) if the fournisseurDTO is not valid,
     * or with status 500 (Internal Server Error) if the fournisseurDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fournisseurs")
    @Timed
    public ResponseEntity<FournisseurDTO> updateFournisseur(@Valid @RequestBody FournisseurDTO fournisseurDTO) throws URISyntaxException {
        log.debug("REST request to update Fournisseur : {}", fournisseurDTO);
        if (fournisseurDTO.getId() == null) {
            return createFournisseur(fournisseurDTO);
        }
        FournisseurDTO result = fournisseurService.save(fournisseurDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fournisseurDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fournisseurs : get all the fournisseurs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fournisseurs in body
     */
    @GetMapping("/fournisseurs")
    @Timed
    public ResponseEntity<List<FournisseurDTO>> getAllFournisseurs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Fournisseurs");
        Page<FournisseurDTO> page = fournisseurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fournisseurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fournisseurs/:id : get the "id" fournisseur.
     *
     * @param id the id of the fournisseurDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fournisseurDTO, or with status 404 (Not Found)
     */
    @GetMapping("/fournisseurs/{id}")
    @Timed
    public ResponseEntity<FournisseurDTO> getFournisseur(@PathVariable Long id) {
        log.debug("REST request to get Fournisseur : {}", id);
        FournisseurDTO fournisseurDTO = fournisseurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fournisseurDTO));
    }

    /**
     * DELETE  /fournisseurs/:id : delete the "id" fournisseur.
     *
     * @param id the id of the fournisseurDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fournisseurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteFournisseur(@PathVariable Long id) {
        log.debug("REST request to delete Fournisseur : {}", id);
        fournisseurService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/fournisseurs?query=:query : search for the fournisseur corresponding
     * to the query.
     *
     * @param query the query of the fournisseur search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/fournisseurs")
    @Timed
    public ResponseEntity<List<FournisseurDTO>> searchFournisseurs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Fournisseurs for query {}", query);
        Page<FournisseurDTO> page = fournisseurService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/fournisseurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
