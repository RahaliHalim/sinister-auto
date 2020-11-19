package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.LienService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.LienDTO;
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
 * REST controller for managing Lien.
 */
@RestController
@RequestMapping("/api")
public class LienResource {

    private final Logger log = LoggerFactory.getLogger(LienResource.class);

    private static final String ENTITY_NAME = "lien";

    private final LienService lienService;

    public LienResource(LienService lienService) {
        this.lienService = lienService;
    }

    /**
     * POST  /liens : Create a new lien.
     *
     * @param lienDTO the lienDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lienDTO, or with status 400 (Bad Request) if the lien has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/liens")
    @Timed
    public ResponseEntity<LienDTO> createLien(@RequestBody LienDTO lienDTO) throws URISyntaxException {
        log.debug("REST request to save Lien : {}", lienDTO);
        if (lienDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new lien cannot already have an ID")).body(null);
        }
        LienDTO result = lienService.save(lienDTO);
        return ResponseEntity.created(new URI("/api/liens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /liens : Updates an existing lien.
     *
     * @param lienDTO the lienDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lienDTO,
     * or with status 400 (Bad Request) if the lienDTO is not valid,
     * or with status 500 (Internal Server Error) if the lienDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/liens")
    @Timed
    public ResponseEntity<LienDTO> updateLien(@RequestBody LienDTO lienDTO) throws URISyntaxException {
        log.debug("REST request to update Lien : {}", lienDTO);
        if (lienDTO.getId() == null) {
            return createLien(lienDTO);
        }
        LienDTO result = lienService.save(lienDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lienDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /liens : get all the liens.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of liens in body
     */
    @GetMapping("/liens")
    @Timed
    public ResponseEntity<List<LienDTO>> getAllLiens(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Liens");
        Page<LienDTO> page = lienService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/liens");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /liens/:id : get the "id" lien.
     *
     * @param id the id of the lienDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lienDTO, or with status 404 (Not Found)
     */
    @GetMapping("/liens/{id}")
    @Timed
    public ResponseEntity<LienDTO> getLien(@PathVariable Long id) {
        log.debug("REST request to get Lien : {}", id);
        LienDTO lienDTO = lienService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lienDTO));
    }

    /**
     * DELETE  /liens/:id : delete the "id" lien.
     *
     * @param id the id of the lienDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/liens/{id}")
    @Timed
    public ResponseEntity<Void> deleteLien(@PathVariable Long id) {
        log.debug("REST request to delete Lien : {}", id);
        lienService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of liens in body
     */

    @GetMapping("/liensWithoutParent")
    @Timed
    public ResponseEntity<List<LienDTO>> getLiensWithoutParent() {
        log.debug("REST request to get a page of All liens");
        List<LienDTO> liens = lienService.findLienWithoutParent();
        return new ResponseEntity<>(liens, HttpStatus.OK);
    }


/**
     * GET  /liens : get  liens for user.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of authorities in body
     */
    @GetMapping("/liens/user/{parentId}")
    @Timed
    public ResponseEntity<List<LienDTO>> getLiensByUser(@PathVariable Long parentId) {
        log.debug("REST request to get a page of authority_cellule", parentId);
         List<LienDTO> liens = lienService.findLienByUser(parentId);
        return new ResponseEntity<>(liens, HttpStatus.OK);
    }
}