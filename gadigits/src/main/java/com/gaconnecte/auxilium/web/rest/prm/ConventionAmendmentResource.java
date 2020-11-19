package com.gaconnecte.auxilium.web.rest.prm;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.prm.ConventionAmendmentService;
import com.gaconnecte.auxilium.service.prm.dto.ConventionAmendmentDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing ConventionAmendment.
 */
@RestController
@RequestMapping("/api")
public class ConventionAmendmentResource {

    private final Logger log = LoggerFactory.getLogger(ConventionAmendmentResource.class);

    private static final String ENTITY_NAME = "conventionAmendment";

    private final ConventionAmendmentService conventionAmendmentService;

    private final LoggerService loggerService;


    public ConventionAmendmentResource(ConventionAmendmentService conventionAmendmentService, LoggerService loggerService) {
        this.conventionAmendmentService = conventionAmendmentService;
        this.loggerService = loggerService;
    }

    /**
     * POST  /conventionAmendment : Create a new conventionAmendment.
     *
     * @param conventionAmendmentDTO the conventionAmendmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conventionAmendmentDTO, or with status 400 (Bad Request) if the conventionAmendment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping(value = "/conventionAmendment", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<ConventionAmendmentDTO> createConventionAmendment(@RequestPart(name = "signedConventionAmendment", required = false) MultipartFile signedConventionAmendment, @RequestPart(name = "conventionAmendment") ConventionAmendmentDTO conventionAmendmentDTO) throws URISyntaxException {
        log.debug("REST request to save ConventionAmendment : {}", conventionAmendmentDTO.toString());
        if (conventionAmendmentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new conventionAmendment cannot already have an ID")).body(null);
        }
        ConventionAmendmentDTO result = conventionAmendmentService.save(signedConventionAmendment, conventionAmendmentDTO);
        return ResponseEntity.created(new URI("/api/conventionAmendment/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /conventionAmendment : Updates an existing conventionAmendment.
     *
     * @param conventionAmendmentDTO the conventionAmendmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conventionAmendmentDTO,
     * or with status 400 (Bad Request) if the conventionAmendmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the conventionAmendmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/conventionAmendment")
    @Timed
    public ResponseEntity<ConventionAmendmentDTO> updateConventionAmendment(@Valid @RequestBody ConventionAmendmentDTO conventionAmendmentDTO) throws URISyntaxException {
        log.debug("REST request to update ConventionAmendment : {}", conventionAmendmentDTO);
        if (conventionAmendmentDTO.getId() == null) {
            return createConventionAmendment(null, conventionAmendmentDTO);
        }
        ConventionAmendmentDTO result = conventionAmendmentService.save(null, conventionAmendmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, conventionAmendmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /conventionAmendment : get all the conventionAmendments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of conventionAmendments in body
     */
    @GetMapping("/conventionAmendment")
    @Timed
    public ResponseEntity<Set<ConventionAmendmentDTO>> getAllConventionAmendments() {
        log.debug("REST request to get a page of ConventionAmendments");
		try {
			Set<ConventionAmendmentDTO> packs = conventionAmendmentService.findAll();
			return new ResponseEntity<>(packs, HttpStatus.OK);
		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

    /**
     * GET  /conventionAmendment/:id : get the "id" conventionAmendment.
     *
     * @param id the id of the conventionAmendmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conventionAmendmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/conventionAmendment/{id}")
    @Timed
    public ResponseEntity<ConventionAmendmentDTO> getConventionAmendment(@PathVariable Long id) {
        log.debug("REST request to get ConventionAmendment : {}", id);
        ConventionAmendmentDTO conventionAmendmentDTO = conventionAmendmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(conventionAmendmentDTO));
    }

    /**
     * DELETE  /conventionAmendment/:id : delete the "id" conventionAmendment.
     *
     * @param id the id of the conventionAmendmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/conventionAmendment/{id}")
    @Timed
    public ResponseEntity<Void> deleteConventionAmendment(@PathVariable Long id) {
        log.debug("REST request to delete ConventionAmendment : {}", id);
        conventionAmendmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/conventionAmendment?query=:query : search for the conventionAmendment corresponding
     * to the query.
     *
     * @param query the query of the conventionAmendment search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/conventionAmendment")
    @Timed
    public ResponseEntity<List<ConventionAmendmentDTO>> searchConventionAmendments(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of ConventionAmendments for query {}", query);
        Page<ConventionAmendmentDTO> page = conventionAmendmentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/conventionAmendment");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
