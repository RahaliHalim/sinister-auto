package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.PersonneMoraleService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.PersonneMoraleDTO;
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
 * REST controller for managing PersonneMorale.
 */
@RestController
@RequestMapping("/api")
public class PersonneMoraleResource {

    private final Logger log = LoggerFactory.getLogger(PersonneMoraleResource.class);

    private static final String ENTITY_NAME = "personneMorale";

    private final PersonneMoraleService personneMoraleService;

    public PersonneMoraleResource(PersonneMoraleService personneMoraleService) {
        this.personneMoraleService = personneMoraleService;
    }

    /**
     * POST  /personne-morales : Create a new personneMorale.
     *
     * @param personneMoraleDTO the personneMoraleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personneMoraleDTO, or with status 400 (Bad Request) if the personneMorale has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personne-morales")
    @Timed
    public ResponseEntity<PersonneMoraleDTO> createPersonneMorale(@Valid @RequestBody PersonneMoraleDTO personneMoraleDTO) throws URISyntaxException {
        log.debug("REST request to save PersonneMorale : {}", personneMoraleDTO);
        if (personneMoraleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new personneMorale cannot already have an ID")).body(null);
        }
        PersonneMoraleDTO result = personneMoraleService.save(personneMoraleDTO);
        return ResponseEntity.created(new URI("/api/personne-morales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personne-morales : Updates an existing personneMorale.
     *
     * @param personneMoraleDTO the personneMoraleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personneMoraleDTO,
     * or with status 400 (Bad Request) if the personneMoraleDTO is not valid,
     * or with status 500 (Internal Server Error) if the personneMoraleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personne-morales")
    @Timed
    public ResponseEntity<PersonneMoraleDTO> updatePersonneMorale(@Valid @RequestBody PersonneMoraleDTO personneMoraleDTO) throws URISyntaxException {
        log.debug("REST request to update PersonneMorale : {}", personneMoraleDTO);
        if (personneMoraleDTO.getId() == null) {
            return createPersonneMorale(personneMoraleDTO);
        }
        PersonneMoraleDTO result = personneMoraleService.save(personneMoraleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personneMoraleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personne-morales : get all the personneMorales.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personneMorales in body
     */
    @GetMapping("/personne-morales")
    @Timed
    public ResponseEntity<List<PersonneMoraleDTO>> getAllPersonneMorales(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PersonneMorales");
        Page<PersonneMoraleDTO> page = personneMoraleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/personne-morales");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /personne-morales/:id : get the "id" personneMorale.
     *
     * @param id the id of the personneMoraleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personneMoraleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/personne-morales/{id}")
    @Timed
    public ResponseEntity<PersonneMoraleDTO> getPersonneMorale(@PathVariable Long id) {
        log.debug("REST request to get PersonneMorale : {}", id);
        PersonneMoraleDTO personneMoraleDTO = personneMoraleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personneMoraleDTO));
    }

    /**
     * DELETE  /personne-morales/:id : delete the "id" personneMorale.
     *
     * @param id the id of the personneMoraleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personne-morales/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonneMorale(@PathVariable Long id) {
        log.debug("REST request to delete PersonneMorale : {}", id);
        personneMoraleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/personne-morales?query=:query : search for the personneMorale corresponding
     * to the query.
     *
     * @param query the query of the personneMorale search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/personne-morales")
    @Timed
    public ResponseEntity<List<PersonneMoraleDTO>> searchPersonneMorales(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of PersonneMorales for query {}", query);
        Page<PersonneMoraleDTO> page = personneMoraleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/personne-morales");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
