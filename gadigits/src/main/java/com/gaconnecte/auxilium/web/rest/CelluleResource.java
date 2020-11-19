package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.CelluleService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.CelluleDTO;
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
 * REST controller for managing Cellule.
 */
@RestController
@RequestMapping("/api")
public class CelluleResource {

    private final Logger log = LoggerFactory.getLogger(CelluleResource.class);

    private static final String ENTITY_NAME = "cellule";

    private final CelluleService celluleService;

    public CelluleResource(CelluleService celluleService) {
        this.celluleService = celluleService;
    }

    /**
     * POST  /cellules : Create a new cellule.
     *
     * @param celluleDTO the celluleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new celluleDTO, or with status 400 (Bad Request) if the cellule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cellules")
    @Timed
    public ResponseEntity<CelluleDTO> createCellule(@RequestBody CelluleDTO celluleDTO) throws URISyntaxException {
        log.debug("REST request to save Cellule : {}", celluleDTO);
        if (celluleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cellule cannot already have an ID")).body(null);
        }
        CelluleDTO result = celluleService.save(celluleDTO);
        return ResponseEntity.created(new URI("/api/cellules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cellules : Updates an existing cellule.
     *
     * @param celluleDTO the celluleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated celluleDTO,
     * or with status 400 (Bad Request) if the celluleDTO is not valid,
     * or with status 500 (Internal Server Error) if the celluleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cellules")
    @Timed
    public ResponseEntity<CelluleDTO> updateCellule(@RequestBody CelluleDTO celluleDTO) throws URISyntaxException {
        log.debug("REST request to update Cellule : {}", celluleDTO);
        if (celluleDTO.getId() == null) {
            return createCellule(celluleDTO);
        }
        CelluleDTO result = celluleService.save(celluleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, celluleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cellules : get all the cellules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cellules in body
     */
    @GetMapping("/cellules")
    @Timed
    public ResponseEntity<List<CelluleDTO>> getAllCellules(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Cellules");
        Page<CelluleDTO> page = celluleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cellules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cellules/:id : get the "id" cellule.
     *
     * @param id the id of the celluleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the celluleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cellules/{id}")
    @Timed
    public ResponseEntity<CelluleDTO> getCellule(@PathVariable Long id) {
        log.debug("REST request to get Cellule : {}", id);
        CelluleDTO celluleDTO = celluleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(celluleDTO));
    }

    /**
     * DELETE  /cellules/:id : delete the "id" cellule.
     *
     * @param id the id of the celluleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cellules/{id}")
    @Timed
    public ResponseEntity<Void> deleteCellule(@PathVariable Long id) {
        log.debug("REST request to delete Cellule : {}", id);
        celluleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

     /**
     * GET  /cellule/:nom : get the "nom" cellule.
     *
     * @param nom the id of the celluleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the celluleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cellules/nom/{name}")
    @Timed
    public ResponseEntity<CelluleDTO> getCelluleByName(@PathVariable String name) {
        log.debug("REST request to get cellule : {}", name);
        CelluleDTO celluleDTO = celluleService.findByName(name);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(celluleDTO));
    }
}