package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.FactureDetailsMoService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.FactureDetailsMoDTO;
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
 * REST controller for managing FactureDetailsMo.
 */
@RestController
@RequestMapping("/api")
public class FactureDetailsMoResource {

    private final Logger log = LoggerFactory.getLogger(FactureDetailsMoResource.class);

    private static final String ENTITY_NAME = "factureDetailsMo";

    private final FactureDetailsMoService factureDetailsMoService;

    public FactureDetailsMoResource(FactureDetailsMoService factureDetailsMoService) {
        this.factureDetailsMoService = factureDetailsMoService;
    }

    /**
     * POST  /facture-details-mos : Create a new factureDetailsMo.
     *
     * @param factureDetailsMoDTO the factureDetailsMoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new factureDetailsMoDTO, or with status 400 (Bad Request) if the factureDetailsMo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/facture-details-mos")
    @Timed
    public ResponseEntity<FactureDetailsMoDTO> createFactureDetailsMo(@Valid @RequestBody FactureDetailsMoDTO factureDetailsMoDTO) throws URISyntaxException {
        log.debug("REST request to save FactureDetailsMo : {}", factureDetailsMoDTO);
        if (factureDetailsMoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new factureDetailsMo cannot already have an ID")).body(null);
        }
        FactureDetailsMoDTO result = factureDetailsMoService.save(factureDetailsMoDTO);
        return ResponseEntity.created(new URI("/api/facture-details-mos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facture-details-mos : Updates an existing factureDetailsMo.
     *
     * @param factureDetailsMoDTO the factureDetailsMoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated factureDetailsMoDTO,
     * or with status 400 (Bad Request) if the factureDetailsMoDTO is not valid,
     * or with status 500 (Internal Server Error) if the factureDetailsMoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/facture-details-mos")
    @Timed
    public ResponseEntity<FactureDetailsMoDTO> updateFactureDetailsMo(@Valid @RequestBody FactureDetailsMoDTO factureDetailsMoDTO) throws URISyntaxException {
        log.debug("REST request to update FactureDetailsMo : {}", factureDetailsMoDTO);
        if (factureDetailsMoDTO.getId() == null) {
            return createFactureDetailsMo(factureDetailsMoDTO);
        }
        FactureDetailsMoDTO result = factureDetailsMoService.save(factureDetailsMoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, factureDetailsMoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facture-details-mos : get all the factureDetailsMos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of factureDetailsMos in body
     */
    @GetMapping("/facture-details-mos")
    @Timed
    public ResponseEntity<List<FactureDetailsMoDTO>> getAllFactureDetailsMos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FactureDetailsMos");
        Page<FactureDetailsMoDTO> page = factureDetailsMoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/facture-details-mos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /facture-details-mos/:id : get the "id" factureDetailsMo.
     *
     * @param id the id of the factureDetailsMoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the factureDetailsMoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/facture-details-mos/{id}")
    @Timed
    public ResponseEntity<FactureDetailsMoDTO> getFactureDetailsMo(@PathVariable Long id) {
        log.debug("REST request to get FactureDetailsMo : {}", id);
        FactureDetailsMoDTO factureDetailsMoDTO = factureDetailsMoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(factureDetailsMoDTO));
    }

    /**
     * DELETE  /facture-details-mos/:id : delete the "id" factureDetailsMo.
     *
     * @param id the id of the factureDetailsMoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/facture-details-mos/{id}")
    @Timed
    public ResponseEntity<Void> deleteFactureDetailsMo(@PathVariable Long id) {
        log.debug("REST request to delete FactureDetailsMo : {}", id);
        factureDetailsMoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/facture-details-mos?query=:query : search for the factureDetailsMo corresponding
     * to the query.
     *
     * @param query the query of the factureDetailsMo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/facture-details-mos")
    @Timed
    public ResponseEntity<List<FactureDetailsMoDTO>> searchFactureDetailsMos(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of FactureDetailsMos for query {}", query);
        Page<FactureDetailsMoDTO> page = factureDetailsMoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/facture-details-mos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
