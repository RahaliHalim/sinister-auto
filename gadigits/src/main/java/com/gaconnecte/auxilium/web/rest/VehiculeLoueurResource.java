package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.VehiculeLoueurService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefTugTruckDTO;
import com.gaconnecte.auxilium.service.dto.VehiculeLoueurDTO;
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
 * REST controller for managing VehiculeLoueur.
 */
@RestController
@RequestMapping("/api")
public class VehiculeLoueurResource {

    private final Logger log = LoggerFactory.getLogger(VehiculeLoueurResource.class);

    private static final String ENTITY_NAME = "vehiculeLoueur";

    private final VehiculeLoueurService vehiculeLoueurService;

    public VehiculeLoueurResource(VehiculeLoueurService vehiculeLoueurService) {
        this.vehiculeLoueurService = vehiculeLoueurService;
    }

    /**
     * POST  /vehicule-loueurs : Create a new vehiculeLoueur.
     *
     * @param vehiculeLoueurDTO the vehiculeLoueurDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehiculeLoueurDTO, or with status 400 (Bad Request) if the vehiculeLoueur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicule-loueurs")
    @Timed
    public ResponseEntity<VehiculeLoueurDTO> createVehiculeLoueur(@RequestBody VehiculeLoueurDTO vehiculeLoueurDTO) throws URISyntaxException {
        log.debug("REST request to save VehiculeLoueur : {}", vehiculeLoueurDTO);
        if (vehiculeLoueurDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vehiculeLoueur cannot already have an ID")).body(null);
        }
        VehiculeLoueurDTO result = vehiculeLoueurService.save(vehiculeLoueurDTO);
        return ResponseEntity.created(new URI("/api/vehicule-loueurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicule-loueurs : Updates an existing vehiculeLoueur.
     *
     * @param vehiculeLoueurDTO the vehiculeLoueurDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehiculeLoueurDTO,
     * or with status 400 (Bad Request) if the vehiculeLoueurDTO is not valid,
     * or with status 500 (Internal Server Error) if the vehiculeLoueurDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicule-loueurs")
    @Timed
    public ResponseEntity<VehiculeLoueurDTO> updateVehiculeLoueur(@RequestBody VehiculeLoueurDTO vehiculeLoueurDTO) throws URISyntaxException {
        log.debug("REST request to update VehiculeLoueur : {}", vehiculeLoueurDTO);
        if (vehiculeLoueurDTO.getId() == null) {
            return createVehiculeLoueur(vehiculeLoueurDTO);
        }
        VehiculeLoueurDTO result = vehiculeLoueurService.save(vehiculeLoueurDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehiculeLoueurDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicule-loueurs : get all the vehiculeLoueurs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vehiculeLoueurs in body
     */
    @GetMapping("/vehicule-loueurs")
    @Timed
    public ResponseEntity<List<VehiculeLoueurDTO>> getAllVehiculeLoueurs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of VehiculeLoueurs");
        Page<VehiculeLoueurDTO> page = vehiculeLoueurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vehicule-loueurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vehicule-loueurs/:id : get the "id" vehiculeLoueur.
     *
     * @param id the id of the vehiculeLoueurDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehiculeLoueurDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vehicule-loueurs/{id}")
    @Timed
    public ResponseEntity<VehiculeLoueurDTO> getVehiculeLoueur(@PathVariable Long id) {
        log.debug("REST request to get VehiculeLoueur : {}", id);
        VehiculeLoueurDTO vehiculeLoueurDTO = vehiculeLoueurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehiculeLoueurDTO));
    }

    /**
     * DELETE  /vehicule-loueurs/:id : delete the "id" vehiculeLoueur.
     *
     * @param id the id of the vehiculeLoueurDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicule-loueurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteVehiculeLoueur(@PathVariable Long id) {
        log.debug("REST request to delete VehiculeLoueur : {}", id);
        vehiculeLoueurService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/vehicule-loueurs?query=:query : search for the vehiculeLoueur corresponding
     * to the query.
     *
     * @param query the query of the vehiculeLoueur search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/vehicule-loueurs")
    @Timed
    public ResponseEntity<List<VehiculeLoueurDTO>> searchVehiculeLoueurs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of VehiculeLoueurs for query {}", query);
        Page<VehiculeLoueurDTO> page = vehiculeLoueurService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/vehicule-loueurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    
    @GetMapping("/vehicule-loueurs/imm/{imm}")
    @Timed
    public ResponseEntity<VehiculeLoueurDTO> findAllVehiculesByImm(@PathVariable String imm) {
        log.debug("REST request to get truck by it registration number : {}", imm);
        VehiculeLoueurDTO vehicule = vehiculeLoueurService.findVehiculesByImmatriculation(imm);
        if (vehicule == null) {
        	vehicule = new VehiculeLoueurDTO();
        }
        return ResponseEntity.ok().body(vehicule);
    }
    
    @GetMapping("/vehicule-loueurs/loueur/{id}")
    @Timed
    public ResponseEntity<List<VehiculeLoueurDTO>> getVehiculesByLoueur(@ApiParam Pageable pageable, @PathVariable Long id) {
        log.debug("REST request to get vehicule : {}", id);
        Page<VehiculeLoueurDTO> vehiculeLoueurDTO = vehiculeLoueurService.findByLoueur(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(vehiculeLoueurDTO, "/api/vehicule-loueurs/loueur");
        return new ResponseEntity<>(vehiculeLoueurDTO.getContent(), headers, HttpStatus.OK);
    }

}
