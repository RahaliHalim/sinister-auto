package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.ConcessionnaireService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.ConcessionnaireDTO;
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
 * REST controller for managing Concessionnaire.
 */
@RestController
@RequestMapping("/api")
public class ConcessionnaireResource {

    private final Logger log = LoggerFactory.getLogger(ConcessionnaireResource.class);

    private static final String ENTITY_NAME = "concessionnaire";

    private final ConcessionnaireService concessionnaireService;

    public ConcessionnaireResource(ConcessionnaireService concessionnaireService) {
        this.concessionnaireService = concessionnaireService;
    }

    /**
     * POST  /concessionnaires : Create a new concessionnaire.
     *
     * @param concessionnaireDTO the concessionnaireDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new concessionnaireDTO, or with status 400 (Bad Request) if the concessionnaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/concessionnaires")
    @Timed
    public ResponseEntity<ConcessionnaireDTO> createConcessionnaire(@Valid @RequestBody ConcessionnaireDTO concessionnaireDTO) throws URISyntaxException {
        log.debug("REST request to save Concessionnaire : {}", concessionnaireDTO);
        if (concessionnaireDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new concessionnaire cannot already have an ID")).body(null);
        }
        ConcessionnaireDTO result = concessionnaireService.save(concessionnaireDTO);
        return ResponseEntity.created(new URI("/api/concessionnaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /concessionnaires : Updates an existing concessionnaire.
     *
     * @param concessionnaireDTO the concessionnaireDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated concessionnaireDTO,
     * or with status 400 (Bad Request) if the concessionnaireDTO is not valid,
     * or with status 500 (Internal Server Error) if the concessionnaireDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/concessionnaires")
    @Timed
    public ResponseEntity<ConcessionnaireDTO> updateConcessionnaire(@Valid @RequestBody ConcessionnaireDTO concessionnaireDTO) throws URISyntaxException {
        log.debug("REST request to update Concessionnaire : {}", concessionnaireDTO);
        if (concessionnaireDTO.getId() == null) {
            return createConcessionnaire(concessionnaireDTO);
        }
        ConcessionnaireDTO result = concessionnaireService.save(concessionnaireDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, concessionnaireDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /concessionnaires : get all the concessionnaires.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of concessionnaires in body
     */
    @GetMapping("/concessionnaires")
    @Timed
    public ResponseEntity<List<ConcessionnaireDTO>> getAllConcessionnaires(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Concessionnaires");
        Page<ConcessionnaireDTO> page = concessionnaireService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/concessionnaires");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /concessionnaires/:id : get the "id" concessionnaire.
     *
     * @param id the id of the concessionnaireDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the concessionnaireDTO, or with status 404 (Not Found)
     */
    @GetMapping("/concessionnaires/{id}")
    @Timed
    public ResponseEntity<ConcessionnaireDTO> getConcessionnaire(@PathVariable Long id) {
        log.debug("REST request to get Concessionnaire : {}", id);
        ConcessionnaireDTO concessionnaireDTO = concessionnaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(concessionnaireDTO));
    }

    /**
     * DELETE  /concessionnaires/:id : delete the "id" concessionnaire.
     *
     * @param id the id of the concessionnaireDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/concessionnaires/{id}")
    @Timed
    public ResponseEntity<Void> deleteConcessionnaire(@PathVariable Long id) {
        log.debug("REST request to delete Concessionnaire : {}", id);
        concessionnaireService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/concessionnaires?query=:query : search for the concessionnaire corresponding
     * to the query.
     *
     * @param query the query of the concessionnaire search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/concessionnaires")
    @Timed
    public ResponseEntity<List<ConcessionnaireDTO>> searchConcessionnaires(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Concessionnaires for query {}", query);
        Page<ConcessionnaireDTO> page = concessionnaireService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/concessionnaires");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
