package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.BonSortieService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.BonSortieDTO;
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
 * REST controller for managing BonSortie.
 */
@RestController
@RequestMapping("/api")
public class BonSortieResource {

    private final Logger log = LoggerFactory.getLogger(BonSortieResource.class);

    private static final String ENTITY_NAME = "bonSortie";

    private final BonSortieService bonSortieService;

    public BonSortieResource(BonSortieService bonSortieService) {
        this.bonSortieService = bonSortieService;
    }

    /**
     * POST  /bon-sorties : Create a new bonSortie.
     *
     * @param bonSortieDTO the bonSortieDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bonSortieDTO, or with status 400 (Bad Request) if the bonSortie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bon-sorties")
    @Timed
    public ResponseEntity<BonSortieDTO> createBonSortie(@Valid @RequestBody BonSortieDTO bonSortieDTO) throws URISyntaxException {
        log.debug("REST request to save BonSortie : {}", bonSortieDTO);
        if (bonSortieDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bonSortie cannot already have an ID")).body(null);
        }
        BonSortieDTO result = bonSortieService.save(bonSortieDTO);
        return ResponseEntity.created(new URI("/api/bon-sorties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bon-sorties : Updates an existing bonSortie.
     *
     * @param bonSortieDTO the bonSortieDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bonSortieDTO,
     * or with status 400 (Bad Request) if the bonSortieDTO is not valid,
     * or with status 500 (Internal Server Error) if the bonSortieDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bon-sorties")
    @Timed
    public ResponseEntity<BonSortieDTO> updateBonSortie(@Valid @RequestBody BonSortieDTO bonSortieDTO) throws URISyntaxException {
        log.debug("REST request to update BonSortie : {}", bonSortieDTO);
        if (bonSortieDTO.getId() == null) {
            return createBonSortie(bonSortieDTO);
        }
        BonSortieDTO result = bonSortieService.save(bonSortieDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bonSortieDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bon-sorties : get all the bonSorties.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bonSorties in body
     */
    @GetMapping("/bon-sorties")
    @Timed
    public ResponseEntity<List<BonSortieDTO>> getAllBonSorties(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BonSorties");
        Page<BonSortieDTO> page = bonSortieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bon-sorties");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bon-sorties/:id : get the "id" bonSortie.
     *
     * @param id the id of the bonSortieDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bonSortieDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bon-sorties/{id}")
    @Timed
    public ResponseEntity<BonSortieDTO> getBonSortie(@PathVariable Long id) {
        log.debug("REST request to get BonSortie : {}", id);
        BonSortieDTO bonSortieDTO = bonSortieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bonSortieDTO));
    }

    /**
     * DELETE  /bon-sorties/:id : delete the "id" bonSortie.
     *
     * @param id the id of the bonSortieDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bon-sorties/{id}")
    @Timed
    public ResponseEntity<Void> deleteBonSortie(@PathVariable Long id) {
        log.debug("REST request to delete BonSortie : {}", id);
        bonSortieService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bon-sorties?query=:query : search for the bonSortie corresponding
     * to the query.
     *
     * @param query the query of the bonSortie search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/bon-sorties")
    @Timed
    public ResponseEntity<List<BonSortieDTO>> searchBonSorties(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of BonSorties for query {}", query);
        Page<BonSortieDTO> page = bonSortieService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/bon-sorties");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
