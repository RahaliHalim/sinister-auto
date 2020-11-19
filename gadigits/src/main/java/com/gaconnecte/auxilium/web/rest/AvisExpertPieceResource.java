package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.AvisExpertPieceService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.AvisExpertPieceDTO;
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
 * REST controller for managing AvisExpertPiece.
 */
@RestController
@RequestMapping("/api")
public class AvisExpertPieceResource {

    private final Logger log = LoggerFactory.getLogger(AvisExpertPieceResource.class);

    private static final String ENTITY_NAME = "avisExpertPiece";
    private final AvisExpertPieceService avisExpertPieceService;

    public AvisExpertPieceResource(AvisExpertPieceService avisExpertPieceService) {
        this.avisExpertPieceService = avisExpertPieceService;
    }

    /**
     * POST  /avis-expert-pieces : Create a new avisExpertPiece.
     *
     * @param avisExpertPieceDTO the avisExpertPieceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new avisExpertPieceDTO, or with status 400 (Bad Request) if the avisExpertPiece has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/avis-expert-pieces")
    @Timed
    public ResponseEntity<AvisExpertPieceDTO> createAvisExpertPiece(@Valid @RequestBody AvisExpertPieceDTO avisExpertPieceDTO) throws URISyntaxException {
        log.debug("REST request to save AvisExpertPiece : {}", avisExpertPieceDTO);
        if (avisExpertPieceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new avisExpertPiece cannot already have an ID")).body(null);
        }
        AvisExpertPieceDTO result = avisExpertPieceService.save(avisExpertPieceDTO);
        return ResponseEntity.created(new URI("/api/avis-expert-pieces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /avis-expert-pieces : Updates an existing avisExpertPiece.
     *
     * @param avisExpertPieceDTO the avisExpertPieceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated avisExpertPieceDTO,
     * or with status 400 (Bad Request) if the avisExpertPieceDTO is not valid,
     * or with status 500 (Internal Server Error) if the avisExpertPieceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/avis-expert-pieces")
    @Timed
    public ResponseEntity<AvisExpertPieceDTO> updateAvisExpertPiece(@Valid @RequestBody AvisExpertPieceDTO avisExpertPieceDTO) throws URISyntaxException {
        log.debug("REST request to update AvisExpertPiece : {}", avisExpertPieceDTO);
        if (avisExpertPieceDTO.getId() == null) {
            return createAvisExpertPiece(avisExpertPieceDTO);
        }
        AvisExpertPieceDTO result = avisExpertPieceService.save(avisExpertPieceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, avisExpertPieceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /avis-expert-pieces : get all the avisExpertPieces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of avisExpertPieces in body
     */
    @GetMapping("/avis-expert-pieces")
    @Timed
    public ResponseEntity<List<AvisExpertPieceDTO>> getAllAvisExpertPieces(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AvisExpertPieces");
        Page<AvisExpertPieceDTO> page = avisExpertPieceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/avis-expert-pieces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /avis-expert-pieces/:id : get the "id" avisExpertPiece.
     *
     * @param id the id of the avisExpertPieceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the avisExpertPieceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/avis-expert-pieces/{id}")
    @Timed
    public ResponseEntity<AvisExpertPieceDTO> getAvisExpertPiece(@PathVariable Long id) {
        log.debug("REST request to get AvisExpertPiece : {}", id);
        AvisExpertPieceDTO avisExpertPieceDTO = avisExpertPieceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(avisExpertPieceDTO));
    }

    /**
     * DELETE  /avis-expert-pieces/:id : delete the "id" avisExpertPiece.
     *
     * @param id the id of the avisExpertPieceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/avis-expert-pieces/{id}")
    @Timed
    public ResponseEntity<Void> deleteAvisExpertPiece(@PathVariable Long id) {
        log.debug("REST request to delete AvisExpertPiece : {}", id);
        avisExpertPieceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/avis-expert-pieces?query=:query : search for the avisExpertPiece corresponding
     * to the query.
     *
     * @param query the query of the avisExpertPiece search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/avis-expert-pieces")
    @Timed
    public ResponseEntity<List<AvisExpertPieceDTO>> searchAvisExpertPieces(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of AvisExpertPieces for query {}", query);
        Page<AvisExpertPieceDTO> page = avisExpertPieceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/avis-expert-pieces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
