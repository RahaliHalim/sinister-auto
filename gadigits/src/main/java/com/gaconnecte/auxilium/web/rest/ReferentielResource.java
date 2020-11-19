package com.gaconnecte.auxilium.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.ReferentielService;
import com.gaconnecte.auxilium.service.dto.ReferentielDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Referentiel.
 */
@RestController
@RequestMapping("/api")
public class ReferentielResource {

    private final Logger log = LoggerFactory.getLogger(CelluleResource.class);

    private static final String ENTITY_NAME = "referentiel";

    private final ReferentielService referentielService;

    public ReferentielResource(ReferentielService referentielService) {
        this.referentielService = referentielService;
    }

    /**
     * POST  /referentiel : Create a new referentiel.
     *
     * @param celluleDTO the celluleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new referentielDTO, or with status 400 (Bad Request) if the referentiel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/referentiels")
    @Timed
    public ResponseEntity<ReferentielDTO> createReferentiel(@RequestBody ReferentielDTO referentielDTO) throws URISyntaxException {
        log.debug("REST request to save Cellule : {}", referentielDTO);
        if (referentielDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cellule cannot already have an ID")).body(null);
        }
        ReferentielDTO result = referentielService.save(referentielDTO);
        return ResponseEntity.created(new URI("/api/referentiels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /referentiels : Updates an existing referentiel.
     *
     * @param referentielDTO the creferentielDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated referentielDTO,
     * or with status 400 (Bad Request) if the referentielDTO is not valid,
     * or with status 500 (Internal Server Error) if the referentielDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/referentiels")
    @Timed
    public ResponseEntity<ReferentielDTO> updateReferentiel(@RequestBody ReferentielDTO referentielDTO) throws URISyntaxException {
        log.debug("REST request to update Cellule : {}", referentielDTO);
        if (referentielDTO.getId() == null) {
            return createReferentiel(referentielDTO);
        }
        ReferentielDTO result = referentielService.save(referentielDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, referentielDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /referentiels : get all the referentiel.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of referentiel in body
     */
    @GetMapping("/referentiels")
    @Timed
    public ResponseEntity<List<ReferentielDTO>> getAllReferentiel(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of referentiel");
        Page<ReferentielDTO> page = referentielService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/referentiels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /referentiels/:id : get the "id" referentiel.
     *
     * @param id the id of the referentielDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the referentielDTO, or with status 404 (Not Found)
     */
    @GetMapping("/referentiels/{id}")
    @Timed
    public ResponseEntity<ReferentielDTO> getReferentiel(@PathVariable Long id) {
        log.debug("REST request to get referentiel : {}", id);
        ReferentielDTO referentielDTO = referentielService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(referentielDTO));
    }

    /**
     * DELETE  /Referentiel/:id : delete the "id" Referentiel.
     *
     * @param id the id of the ReferentielDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/referentiels/{id}")
    @Timed
    public ResponseEntity<Void> deleteReferentiel(@PathVariable Long id) {
        log.debug("REST request to delete Referentiel : {}", id);
        referentielService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

     /**
     * GET  /cellule/:nom : get the "nom" cellule.
     *
     * @param nom the id of the celluleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the celluleDTO, or with status 404 (Not Found)
     */
  
}