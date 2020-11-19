package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.VisAVisService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.VisAVisDTO;
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
 * REST controller for managing Contact.
 */
@RestController
@RequestMapping("/api")
public class VisAVisResource {

    private final Logger log = LoggerFactory.getLogger(VisAVisResource.class);

    private static final String ENTITY_NAME = "contact";

    private final VisAVisService visAVisService;

    public VisAVisResource(VisAVisService visAVisService) {
        this.visAVisService = visAVisService;
    }

    /**
     * POST  /contacts : Create a new contact.
     *
     * @param contactDTO the contactDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactDTO, or with status 400 (Bad Request) if the contact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vis-a-viss")
    @Timed
    public ResponseEntity<VisAVisDTO> createVisAVis(@Valid @RequestBody VisAVisDTO visAVisDTO) throws URISyntaxException {
        log.debug("REST request to save vis a vis  : {}", visAVisDTO);
        if (visAVisDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vis a vis cannot already have an ID")).body(null);
        }
        VisAVisDTO result = visAVisService.save(visAVisDTO);
        return ResponseEntity.created(new URI("/api/vis-a-viss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contacts : Updates an existing contact.
     *
     * @param contactDTO the contactDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactDTO,
     * or with status 400 (Bad Request) if the contactDTO is not valid,
     * or with status 500 (Internal Server Error) if the contactDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vis-a-viss")
    @Timed
    public ResponseEntity<VisAVisDTO> updateVisAVis(@Valid @RequestBody VisAVisDTO visAVisDTO) throws URISyntaxException {
        log.debug("REST request to update Contact : {}", visAVisDTO);
        if (visAVisDTO.getId() == null) {
            return createVisAVis(visAVisDTO);
        }
        VisAVisDTO result = visAVisService.save(visAVisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, visAVisDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contacts : get all the contacts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contacts in body
     */
    @GetMapping("/vis-a-viss")
    @Timed
    public ResponseEntity<List<VisAVisDTO>> getAllVisAVis(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Contacts");
        Page<VisAVisDTO> page = visAVisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vis-a-viss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  / vis a viss/:id : get the "id" contact.
     *
     * @param id the id of the contactDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vis-a-viss/{id}")
    @Timed
    public ResponseEntity<VisAVisDTO> getVisAVis(@PathVariable Long id) {
        log.debug("REST request to get Contact : {}", id);
        VisAVisDTO visAVisDTO = visAVisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(visAVisDTO));
    }

   
    
  

    /**
     * DELETE  /contacts/:id : delete the "id" contact.
     *
     * @param id the id of the contactDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vis-a-vis/{id}")
    @Timed
    public ResponseEntity<Void> deleteVisAVis(@PathVariable Long id) {
        log.debug("REST request to delete vis a vis  : {}", id);
        visAVisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/contacts?query=:query : search for the contact corresponding
     * to the query.
     *
     * @param query the query of the contact search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/vis-a-viss")
    @Timed
    public ResponseEntity<List<VisAVisDTO>> searchContacts(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Contacts for query {}", query);
        Page<VisAVisDTO> page = visAVisService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/vis-a-viss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
