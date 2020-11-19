package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.ReparateurGrilleService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.ReparateurGrilleDTO;
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

/**
 * REST controller for managing ReparateurGrille.
 */
@RestController
@RequestMapping("/api")
public class ReparateurGrilleResource {

    private final Logger log = LoggerFactory.getLogger(ReparateurGrilleResource.class);

    private static final String ENTITY_NAME = "reparateurGrille";

    private final ReparateurGrilleService reparateurGrilleService;

    public ReparateurGrilleResource(ReparateurGrilleService reparateurGrilleService) {
        this.reparateurGrilleService = reparateurGrilleService;
    }

    /**
     * POST  /reparateur-grilles : Create a new reparateurGrille.
     *
     * @param reparateurGrilleDTO the reparateurGrilleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reparateurGrilleDTO, or with status 400 (Bad Request) if the reparateurGrille has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reparateur-grilles")
    @Timed
    public ResponseEntity<ReparateurGrilleDTO> createReparateurGrille(@RequestBody ReparateurGrilleDTO reparateurGrilleDTO) throws URISyntaxException {
        log.debug("REST request to save ReparateurGrille : {}", reparateurGrilleDTO);
        if (reparateurGrilleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new reparateurGrille cannot already have an ID")).body(null);
        }
        ReparateurGrilleDTO result = reparateurGrilleService.save(reparateurGrilleDTO);
        return ResponseEntity.created(new URI("/api/reparateur-grilles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reparateur-grilles : Updates an existing userCellule.
     *
     * @param reparateurGrilleDTO the reparateurGrilleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reparateurGrilleDTO,
     * or with status 400 (Bad Request) if the reparateurGrilleDTO is not valid,
     * or with status 500 (Internal Server Error) if the reparateurGrilleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reparateur-grilles")
    @Timed
    public ResponseEntity<ReparateurGrilleDTO> updateReparateurGrille(@RequestBody ReparateurGrilleDTO reparateurGrilleDTO) throws URISyntaxException {
        log.debug("REST request to update reparateurGrille : {}", reparateurGrilleDTO);
        if (reparateurGrilleDTO.getId() == null) {
            return createReparateurGrille(reparateurGrilleDTO);
        }
        ReparateurGrilleDTO result = reparateurGrilleService.save(reparateurGrilleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reparateurGrilleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reparateur-grilles : get all the reparateurGrilles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reparateurGrilles in body
     */
    @GetMapping("/reparateur-grilles")
    @Timed
    public ResponseEntity<List<ReparateurGrilleDTO>> getAllReparateurGrilles(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of reparateurGrilles");
        Page<ReparateurGrilleDTO> page = reparateurGrilleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reparateur-grilles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /reparateur-grilles/:id : get the "id" reparateurGrille.
     *
     * @param id the id of the reparateurGrilleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reparateurGrilleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reparateur-grilles/{id}")
    @Timed
    public ResponseEntity<ReparateurGrilleDTO> getReparateurGrille(@PathVariable Long id) {
        log.debug("REST request to get ReparateurGrille : {}", id);
        ReparateurGrilleDTO reparateurGrilleDTO = reparateurGrilleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reparateurGrilleDTO));
    }

    /**
     * GET  /reparateur-grilles/:id : get the "id" reparateurGrille.
     *
     * @param id the id of the ReparateurGrilleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ReparateurGrilleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reparateur-grilles/{grilleId}/{reparateurId}")
    @Timed
    public ResponseEntity<ReparateurGrilleDTO> getReparateurGrilleByReparateurAndGrille(@PathVariable Long grilleId, @PathVariable Long reparateurId) {
        log.debug("REST request to get ReparateurGrille : {}", grilleId,reparateurId);
        ReparateurGrilleDTO reparateurGrilleDTO = reparateurGrilleService.findByReparateurAndGrille(grilleId, reparateurId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reparateurGrilleDTO));
    }


    /**
     * DELETE  /user-cellules/:id : delete the "id" reparateurGrille.
     *
     * @param id the id of the reparateurGrilleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reparateur-grilles/{id}")
    @Timed
    public ResponseEntity<Void> deleteReparateurGrille(@PathVariable Long id) {
        log.debug("REST request to delete ReparateurGrille : {}", id);
        reparateurGrilleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

     /**
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refCompagnies in body
     */

    @GetMapping("/reparateur-grilles/reparateur/{reparateurId}")
    @Timed
    public ResponseEntity<List<ReparateurGrilleDTO>> getByReparateur(@PathVariable Long reparateurId) {
        log.debug("REST request to get a page of ReparateurGrille");
        List<ReparateurGrilleDTO> reparateurGrille = reparateurGrilleService.findByReparateur(reparateurId);
        return new ResponseEntity<>(reparateurGrille, HttpStatus.OK);
    }

    /**
     * GET  /reparateur-grilles/:id : get the "id" reparateurGrille.
     *
     * @param id the id of the ReparateurGrilleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ReparateurGrilleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reparateur-grilles/date/{refTypeInterventionId}/{reparateurId}")
    @Timed
    public ResponseEntity<ReparateurGrilleDTO> getByTypeInterventionAndReparateur(@PathVariable Long refTypeInterventionId, @PathVariable Long reparateurId) {
        log.debug("REST request to get ReparateurGrille : {}", refTypeInterventionId,reparateurId);
        ReparateurGrilleDTO reparateurGrilleDTO = reparateurGrilleService.getByTypeInterventionAndReparateur(refTypeInterventionId, reparateurId);
        return new ResponseEntity<>(reparateurGrilleDTO, HttpStatus.OK);
        //return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reparateurGrilleDTO));
    }

      
}