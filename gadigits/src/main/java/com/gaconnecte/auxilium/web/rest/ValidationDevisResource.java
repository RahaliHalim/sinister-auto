package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.ValidationDevisService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.ValidationDevisDTO;
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
 * REST controller for managing ValidationDevis.
 */
@RestController
@RequestMapping("/api")
public class ValidationDevisResource {

    private final Logger log = LoggerFactory.getLogger(ValidationDevisResource.class);

    private static final String ENTITY_NAME = "validationDevis";

    private final ValidationDevisService validationDevisService;

    public ValidationDevisResource(ValidationDevisService validationDevisService) {
        this.validationDevisService = validationDevisService;
    }

    /**
     * POST  /validation-devis : Create a new validationDevis.
     *
     * @param validationDevisDTO the validationDevisDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new validationDevisDTO, or with status 400 (Bad Request) if the validationDevis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/validation-devis")
    @Timed
    public ResponseEntity<ValidationDevisDTO> createValidationDevis(@Valid @RequestBody ValidationDevisDTO validationDevisDTO) throws URISyntaxException {
        log.debug("REST request to save ValidationDevis : {}", validationDevisDTO);
        if (validationDevisDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new validationDevis cannot already have an ID")).body(null);
        }
        ValidationDevisDTO result = validationDevisService.save(validationDevisDTO);
        return ResponseEntity.created(new URI("/api/validation-devis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /validation-devis : Updates an existing validationDevis.
     *
     * @param validationDevisDTO the validationDevisDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated validationDevisDTO,
     * or with status 400 (Bad Request) if the validationDevisDTO is not valid,
     * or with status 500 (Internal Server Error) if the validationDevisDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/validation-devis")
    @Timed
    public ResponseEntity<ValidationDevisDTO> updateValidationDevis(@Valid @RequestBody ValidationDevisDTO validationDevisDTO) throws URISyntaxException {
        log.debug("REST request to update ValidationDevis : {}", validationDevisDTO);
        if (validationDevisDTO.getId() == null) {
            return createValidationDevis(validationDevisDTO);
        }
        ValidationDevisDTO result = validationDevisService.save(validationDevisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, validationDevisDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /validation-devis : get all the validationDevis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of validationDevis in body
     */
    @GetMapping("/validation-devis")
    @Timed
    public ResponseEntity<List<ValidationDevisDTO>> getAllValidationDevis(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ValidationDevis");
        Page<ValidationDevisDTO> page = validationDevisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/validation-devis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /validation-devis/:id : get the "id" validationDevis.
     *
     * @param id the id of the validationDevisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the validationDevisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/validation-devis/{id}")
    @Timed
    public ResponseEntity<ValidationDevisDTO> getValidationDevis(@PathVariable Long id) {
        log.debug("REST request to get ValidationDevis : {}", id);
        ValidationDevisDTO validationDevisDTO = validationDevisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(validationDevisDTO));
    }

    /**
     * DELETE  /validation-devis/:id : delete the "id" validationDevis.
     *
     * @param id the id of the validationDevisDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/validation-devis/{id}")
    @Timed
    public ResponseEntity<Void> deleteValidationDevis(@PathVariable Long id) {
        log.debug("REST request to delete ValidationDevis : {}", id);
        validationDevisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/validation-devis?query=:query : search for the validationDevis corresponding
     * to the query.
     *
     * @param query the query of the validationDevis search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/validation-devis")
    @Timed
    public ResponseEntity<List<ValidationDevisDTO>> searchValidationDevis(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of ValidationDevis for query {}", query);
        Page<ValidationDevisDTO> page = validationDevisService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/validation-devis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
