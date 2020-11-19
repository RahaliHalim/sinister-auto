package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.SysActionUtilisateurService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.SysActionUtilisateurDTO;
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
 * REST controller for managing SysActionUtilisateur.
 */
@RestController
@RequestMapping("/api")
public class SysActionUtilisateurResource {

    private final Logger log = LoggerFactory.getLogger(SysActionUtilisateurResource.class);

    private static final String ENTITY_NAME = "sysActionUtilisateur";

    private final SysActionUtilisateurService sysActionUtilisateurService;

    public SysActionUtilisateurResource(SysActionUtilisateurService sysActionUtilisateurService) {
        this.sysActionUtilisateurService = sysActionUtilisateurService;
    }

    /**
     * POST  /sys-action-utilisateurs : Create a new sysActionUtilisateur.
     *
     * @param sysActionUtilisateurDTO the sysActionUtilisateurDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysActionUtilisateurDTO, or with status 400 (Bad Request) if the sysActionUtilisateur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sys-action-utilisateurs")
    @Timed
    public ResponseEntity<SysActionUtilisateurDTO> createSysActionUtilisateur(@Valid @RequestBody SysActionUtilisateurDTO sysActionUtilisateurDTO) throws URISyntaxException {
        log.debug("REST request to save SysActionUtilisateur : {}", sysActionUtilisateurDTO);
        if (sysActionUtilisateurDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sysActionUtilisateur cannot already have an ID")).body(null);
        }
        SysActionUtilisateurDTO result = sysActionUtilisateurService.save(sysActionUtilisateurDTO);
        return ResponseEntity.created(new URI("/api/sys-action-utilisateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sys-action-utilisateurs : Updates an existing sysActionUtilisateur.
     *
     * @param sysActionUtilisateurDTO the sysActionUtilisateurDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sysActionUtilisateurDTO,
     * or with status 400 (Bad Request) if the sysActionUtilisateurDTO is not valid,
     * or with status 500 (Internal Server Error) if the sysActionUtilisateurDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sys-action-utilisateurs")
    @Timed
    public ResponseEntity<SysActionUtilisateurDTO> updateSysActionUtilisateur(@Valid @RequestBody SysActionUtilisateurDTO sysActionUtilisateurDTO) throws URISyntaxException {
        log.debug("REST request to update SysActionUtilisateur : {}", sysActionUtilisateurDTO);
        if (sysActionUtilisateurDTO.getId() == null) {
            return createSysActionUtilisateur(sysActionUtilisateurDTO);
        }
        SysActionUtilisateurDTO result = sysActionUtilisateurService.update(sysActionUtilisateurDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sysActionUtilisateurDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sys-action-utilisateurs : get all the sysActionUtilisateurs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysActionUtilisateurs in body
     */
    @GetMapping("/sys-action-utilisateurs")
    @Timed
    public ResponseEntity<List<SysActionUtilisateurDTO>> getAllSysActionUtilisateurs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SysActionUtilisateurs");
        Page<SysActionUtilisateurDTO> page = sysActionUtilisateurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sys-action-utilisateurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sys-action-utilisateurs/:id : get the "id" sysActionUtilisateur.
     *
     * @param id the id of the sysActionUtilisateurDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sysActionUtilisateurDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sys-action-utilisateurs/{id}")
    @Timed
    public ResponseEntity<SysActionUtilisateurDTO> getSysActionUtilisateur(@PathVariable Long id) {
        log.debug("REST request to get SysActionUtilisateur : {}", id);
        SysActionUtilisateurDTO sysActionUtilisateurDTO = sysActionUtilisateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sysActionUtilisateurDTO));
    }

    /**
     * GET  /sys-action-utilisateurs/:nom : get the "nom" sysActionUtilisateur.
     *
     * @param nom the id of the sysActionUtilisateurDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sysActionUtilisateurDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sys-action-utilisateurs/action/{nom}")
    @Timed
    public ResponseEntity<SysActionUtilisateurDTO> getSysActionUtilisateurByName(@PathVariable String nom) {
        log.debug("REST request to get SysActionUtilisateur : {}", nom);
        SysActionUtilisateurDTO sysActionUtilisateurDTO = sysActionUtilisateurService.findByName(nom);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sysActionUtilisateurDTO));
    }

    /**
     * DELETE  /sys-action-utilisateurs/:id : delete the "id" sysActionUtilisateur.
     *
     * @param id the id of the sysActionUtilisateurDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sys-action-utilisateurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteSysActionUtilisateur(@PathVariable Long id) {
        log.debug("REST request to delete SysActionUtilisateur : {}", id);
        sysActionUtilisateurService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/sys-action-utilisateurs?query=:query : search for the sysActionUtilisateur corresponding
     * to the query.
     *
     * @param query the query of the sysActionUtilisateur search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/sys-action-utilisateurs")
    @Timed
    public ResponseEntity<List<SysActionUtilisateurDTO>> searchSysActionUtilisateurs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of SysActionUtilisateurs for query {}", query);
        Page<SysActionUtilisateurDTO> page = sysActionUtilisateurService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sys-action-utilisateurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
