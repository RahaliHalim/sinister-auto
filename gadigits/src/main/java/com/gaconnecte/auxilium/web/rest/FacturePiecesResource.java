package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.FacturePiecesService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.FacturePiecesDTO;
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
 * REST controller for managing FacturePieces.
 */
@RestController
@RequestMapping("/api")
public class FacturePiecesResource {

    private final Logger log = LoggerFactory.getLogger(FacturePiecesResource.class);

    private static final String ENTITY_NAME = "facturePieces";

    private final FacturePiecesService facturePiecesService;

    public FacturePiecesResource(FacturePiecesService facturePiecesService) {
        this.facturePiecesService = facturePiecesService;
    }

    /**
     * POST  /facture-pieces : Create a new facturePieces.
     *
     * @param facturePiecesDTO the facturePiecesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facturePiecesDTO, or with status 400 (Bad Request) if the facturePieces has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/facture-pieces")
    @Timed
    public ResponseEntity<FacturePiecesDTO> createFacturePieces(@Valid @RequestBody FacturePiecesDTO facturePiecesDTO) throws URISyntaxException {
        log.debug("REST request to save FacturePieces : {}", facturePiecesDTO);
        if (facturePiecesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new facturePieces cannot already have an ID")).body(null);
        }
        FacturePiecesDTO result = facturePiecesService.save(facturePiecesDTO);
        return ResponseEntity.created(new URI("/api/facture-pieces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facture-pieces : Updates an existing facturePieces.
     *
     * @param facturePiecesDTO the facturePiecesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facturePiecesDTO,
     * or with status 400 (Bad Request) if the facturePiecesDTO is not valid,
     * or with status 500 (Internal Server Error) if the facturePiecesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/facture-pieces")
    @Timed
    public ResponseEntity<FacturePiecesDTO> updateFacturePieces(@Valid @RequestBody FacturePiecesDTO facturePiecesDTO) throws URISyntaxException {
        log.debug("REST request to update FacturePieces : {}", facturePiecesDTO);
        if (facturePiecesDTO.getId() == null) {
            return createFacturePieces(facturePiecesDTO);
        }
        FacturePiecesDTO result = facturePiecesService.save(facturePiecesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, facturePiecesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facture-pieces : get all the facturePieces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of facturePieces in body
     */
    @GetMapping("/facture-pieces")
    @Timed
    public ResponseEntity<List<FacturePiecesDTO>> getAllFacturePieces(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FacturePieces");
        Page<FacturePiecesDTO> page = facturePiecesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/facture-pieces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /facture-pieces/:id : get the "id" facturePieces.
     *
     * @param id the id of the facturePiecesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facturePiecesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/facture-pieces/{id}")
    @Timed
    public ResponseEntity<FacturePiecesDTO> getFacturePieces(@PathVariable Long id) {
        log.debug("REST request to get FacturePieces : {}", id);
        FacturePiecesDTO facturePiecesDTO = facturePiecesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(facturePiecesDTO));
    }

    /**
     * DELETE  /facture-pieces/:id : delete the "id" facturePieces.
     *
     * @param id the id of the facturePiecesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/facture-pieces/{id}")
    @Timed
    public ResponseEntity<Void> deleteFacturePieces(@PathVariable Long id) {
        log.debug("REST request to delete FacturePieces : {}", id);
        facturePiecesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/facture-pieces?query=:query : search for the facturePieces corresponding
     * to the query.
     *
     * @param query the query of the facturePieces search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/facture-pieces")
    @Timed
    public ResponseEntity<List<FacturePiecesDTO>> searchFacturePieces(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of FacturePieces for query {}", query);
        Page<FacturePiecesDTO> page = facturePiecesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/facture-pieces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
