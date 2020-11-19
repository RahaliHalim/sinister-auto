package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.LoueurService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.LoueurDTO;
import com.gaconnecte.auxilium.service.dto.RefRemorqueurDTO;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Loueur.
 */
@RestController
@RequestMapping("/api")
public class LoueurResource {

    private final Logger log = LoggerFactory.getLogger(LoueurResource.class);

    private static final String ENTITY_NAME = "loueur";

    private final LoueurService loueurService;

    public LoueurResource(LoueurService loueurService) {
        this.loueurService = loueurService;
    }

    /**
     * POST  /loueurs : Create a new loueur.
     *
     * @param loueurDTO the loueurDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new loueurDTO, or with status 400 (Bad Request) if the loueur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/loueurs")
    @Timed
    public ResponseEntity<LoueurDTO> createLoueur(@RequestBody LoueurDTO loueurDTO) throws URISyntaxException {
        log.debug("REST request to save Loueur : {}", loueurDTO);
        if (loueurDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new loueur cannot already have an ID")).body(null);
        }
        LoueurDTO result = loueurService.save(loueurDTO);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }
    
    /**
     * PUT  /loueurs : Updates an existing loueur.
     *
     * @param loueurDTO the loueurDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated loueurDTO,
     * or with status 400 (Bad Request) if the loueurDTO is not valid,
     * or with status 500 (Internal Server Error) if the loueurDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/loueurs")
    @Timed
    public ResponseEntity<LoueurDTO> updateLoueur(@RequestBody LoueurDTO loueurDTO) throws URISyntaxException {
        log.debug("REST request to update Loueur : {}", loueurDTO);
        if (loueurDTO.getId() == null) {
            return createLoueur(loueurDTO);
        }
        LoueurDTO result = loueurService.save(loueurDTO);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * GET  /loueurs : get all the loueurs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of loueurs in body
     */
    @GetMapping("/loueurs")
    @Timed
    public ResponseEntity<List<LoueurDTO>> getAllLoueurs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Loueurs");
        Page<LoueurDTO> page = loueurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/loueurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /loueurs/:id : get the "id" loueur.
     *
     * @param id the id of the loueurDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the loueurDTO, or with status 404 (Not Found)
     */
    @GetMapping("/loueurs/{id}")
    @Timed
    public ResponseEntity<LoueurDTO> getLoueur(@PathVariable Long id) {
        log.debug("REST request to get Loueur : {}", id);
        LoueurDTO loueurDTO = loueurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(loueurDTO));
    }
    @GetMapping("/loueurs/gov/{governorateId}")
    @Timed
    public ResponseEntity<List<LoueurDTO>> getLoueurByGov(@PathVariable Long governorateId) {
        log.debug("REST request to get Loueur : {}", governorateId);
        List<LoueurDTO>  List = loueurService.findByGovernorate(governorateId);
        return new ResponseEntity<>(List, HttpStatus.OK);
      }

    /**
     * DELETE  /loueurs/:id : delete the "id" loueur.
     *
     * @param id the id of the loueurDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/loueurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteLoueur(@PathVariable Long id) {
        log.debug("REST request to delete Loueur : {}", id);
        loueurService.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * SEARCH  /_search/loueurs?query=:query : search for the loueur corresponding
     * to the query.
     *
     * @param query the query of the loueur search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/loueurs")
    @Timed
    public ResponseEntity<List<LoueurDTO>> searchLoueurs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Loueurs for query {}", query);
        Page<LoueurDTO> page = loueurService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/loueurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
