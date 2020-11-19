package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefNatureExpertiseService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefNatureExpertiseDTO;
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
 * REST controller for managing RefNatureExpertise.
 */
@RestController
@RequestMapping("/api")
public class RefNatureExpertiseResource {

    private final Logger log = LoggerFactory.getLogger(RefNatureExpertiseResource.class);

    private static final String ENTITY_NAME = "refNatureExpertise";

    private final RefNatureExpertiseService refNatureExpertiseService;

    public RefNatureExpertiseResource(RefNatureExpertiseService refNatureExpertiseService) {
        this.refNatureExpertiseService = refNatureExpertiseService;
    }

    /**
     * POST  /ref-nature-expertises : Create a new refNatureExpertise.
     *
     * @param refNatureExpertiseDTO the refNatureExpertiseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refNatureExpertiseDTO, or with status 400 (Bad Request) if the refNatureExpertise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-nature-expertises")
    @Timed
    public ResponseEntity<RefNatureExpertiseDTO> createRefNatureExpertise(@Valid @RequestBody RefNatureExpertiseDTO refNatureExpertiseDTO) throws URISyntaxException {
        log.debug("REST request to save RefNatureExpertise : {}", refNatureExpertiseDTO);
        if (refNatureExpertiseDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refNatureExpertise cannot already have an ID")).body(null);
        }
        RefNatureExpertiseDTO result = refNatureExpertiseService.save(refNatureExpertiseDTO);
        return ResponseEntity.created(new URI("/api/ref-nature-expertises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-nature-expertises : Updates an existing refNatureExpertise.
     *
     * @param refNatureExpertiseDTO the refNatureExpertiseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refNatureExpertiseDTO,
     * or with status 400 (Bad Request) if the refNatureExpertiseDTO is not valid,
     * or with status 500 (Internal Server Error) if the refNatureExpertiseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-nature-expertises")
    @Timed
    public ResponseEntity<RefNatureExpertiseDTO> updateRefNatureExpertise(@Valid @RequestBody RefNatureExpertiseDTO refNatureExpertiseDTO) throws URISyntaxException {
        log.debug("REST request to update RefNatureExpertise : {}", refNatureExpertiseDTO);
        if (refNatureExpertiseDTO.getId() == null) {
            return createRefNatureExpertise(refNatureExpertiseDTO);
        }
        RefNatureExpertiseDTO result = refNatureExpertiseService.save(refNatureExpertiseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refNatureExpertiseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-nature-expertises : get all the refNatureExpertises.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refNatureExpertises in body
     */
    @GetMapping("/ref-nature-expertises")
    @Timed
    public ResponseEntity<List<RefNatureExpertiseDTO>> getAllRefNatureExpertises(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefNatureExpertises");
        Page<RefNatureExpertiseDTO> page = refNatureExpertiseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-nature-expertises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ref-nature-expertises/:id : get the "id" refNatureExpertise.
     *
     * @param id the id of the refNatureExpertiseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refNatureExpertiseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-nature-expertises/{id}")
    @Timed
    public ResponseEntity<RefNatureExpertiseDTO> getRefNatureExpertise(@PathVariable Long id) {
        log.debug("REST request to get RefNatureExpertise : {}", id);
        RefNatureExpertiseDTO refNatureExpertiseDTO = refNatureExpertiseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refNatureExpertiseDTO));
    }

    /**
     * DELETE  /ref-nature-expertises/:id : delete the "id" refNatureExpertise.
     *
     * @param id the id of the refNatureExpertiseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-nature-expertises/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefNatureExpertise(@PathVariable Long id) {
        log.debug("REST request to delete RefNatureExpertise : {}", id);
        refNatureExpertiseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ref-nature-expertises?query=:query : search for the refNatureExpertise corresponding
     * to the query.
     *
     * @param query the query of the refNatureExpertise search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-nature-expertises")
    @Timed
    public ResponseEntity<List<RefNatureExpertiseDTO>> searchRefNatureExpertises(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefNatureExpertises for query {}", query);
        Page<RefNatureExpertiseDTO> page = refNatureExpertiseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-nature-expertises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
