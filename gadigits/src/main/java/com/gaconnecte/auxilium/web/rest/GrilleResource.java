package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.GrilleService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.GrilleDTO;
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
 * REST controller for managing Grille.
 */
@RestController
@RequestMapping("/api")
public class GrilleResource {

    private final Logger log = LoggerFactory.getLogger(GrilleResource.class);

    private static final String ENTITY_NAME = "grille";

    private final GrilleService grilleService;

    public GrilleResource(GrilleService grilleService) {
        this.grilleService = grilleService;
    }

    /**
     * POST  /grilles : Create a new grille.
     *
     * @param grilleDTO the grilleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new grilleDTO, or with status 400 (Bad Request) if the grille has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/grilles")
    @Timed
    public ResponseEntity<GrilleDTO> createGrille(@Valid @RequestBody GrilleDTO grilleDTO) throws URISyntaxException {
        log.debug("REST request to save Grille : {}", grilleDTO);
        if (grilleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new grille cannot already have an ID")).body(null);
        }
        GrilleDTO result = grilleService.save(grilleDTO);
        return ResponseEntity.created(new URI("/api/grilles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /grilles : Updates an existing grille.
     *
     * @param grilleDTO the grilleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated grilleDTO,
     * or with status 400 (Bad Request) if the grilleDTO is not valid,
     * or with status 500 (Internal Server Error) if the grilleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/grilles")
    @Timed
    public ResponseEntity<GrilleDTO> updateGrille(@Valid @RequestBody GrilleDTO grilleDTO) throws URISyntaxException {
        log.debug("REST request to update Grille : {}", grilleDTO);
        if (grilleDTO.getId() == null) {
            return createGrille(grilleDTO);
        }
        GrilleDTO result = grilleService.save(grilleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, grilleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /grilles : get all the grilles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of grilles in body
     */
    @GetMapping("/grilles")
    @Timed
    public ResponseEntity<List<GrilleDTO>> getAllGrilles(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Grilles");
        Page<GrilleDTO> page = grilleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/grilles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /grilles/:id : get the "id" grille.
     *
     * @param id the id of the grilleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the grilleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/grilles/{id}")
    @Timed
    public ResponseEntity<GrilleDTO> getGrille(@PathVariable Long id) {
        log.debug("REST request to get Grille : {}", id);
        GrilleDTO grilleDTO = grilleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(grilleDTO));
    }

    /**
     * DELETE  /grilles/:id : delete the "id" grille.
     *
     * @param id the id of the grilleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/grilles/{id}")
    @Timed
    public ResponseEntity<Void> deleteGrille(@PathVariable Long id) {
        log.debug("REST request to delete Grille : {}", id);
        grilleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/grilles?query=:query : search for the grille corresponding
     * to the query.
     *
     * @param query the query of the grille search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/grilles")
    @Timed
    public ResponseEntity<List<GrilleDTO>> searchGrilles(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Grilles for query {}", query);
        Page<GrilleDTO> page = grilleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/grilles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
