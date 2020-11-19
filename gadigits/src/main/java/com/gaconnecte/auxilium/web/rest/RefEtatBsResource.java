package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefEtatBsService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefEtatBsDTO;
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
 * REST controller for managing RefEtatBs.
 */
@RestController
@RequestMapping("/api")
public class RefEtatBsResource {

    private final Logger log = LoggerFactory.getLogger(RefEtatBsResource.class);

    private static final String ENTITY_NAME = "refEtatBs";

    private final RefEtatBsService refEtatBsService;

    public RefEtatBsResource(RefEtatBsService refEtatBsService) {
        this.refEtatBsService = refEtatBsService;
    }

    /**
     * POST  /ref-etat-bs : Create a new refEtatBs.
     *
     * @param refEtatBsDTO the refEtatBsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refEtatBsDTO, or with status 400 (Bad Request) if the refEtatBs has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-etat-bs")
    @Timed
    public ResponseEntity<RefEtatBsDTO> createRefEtatBs(@Valid @RequestBody RefEtatBsDTO refEtatBsDTO) throws URISyntaxException {
        log.debug("REST request to save RefEtatBs : {}", refEtatBsDTO);
        if (refEtatBsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refEtatBs cannot already have an ID")).body(null);
        }
        RefEtatBsDTO result = refEtatBsService.save(refEtatBsDTO);
        return ResponseEntity.created(new URI("/api/ref-etat-bs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-etat-bs : Updates an existing refEtatBs.
     *
     * @param refEtatBsDTO the refEtatBsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refEtatBsDTO,
     * or with status 400 (Bad Request) if the refEtatBsDTO is not valid,
     * or with status 500 (Internal Server Error) if the refEtatBsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-etat-bs")
    @Timed
    public ResponseEntity<RefEtatBsDTO> updateRefEtatBs(@Valid @RequestBody RefEtatBsDTO refEtatBsDTO) throws URISyntaxException {
        log.debug("REST request to update RefEtatBs : {}", refEtatBsDTO);
        if (refEtatBsDTO.getId() == null) {
            return createRefEtatBs(refEtatBsDTO);
        }
        RefEtatBsDTO result = refEtatBsService.save(refEtatBsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refEtatBsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-etat-bs : get all the refEtatBs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refEtatBs in body
     */
    @GetMapping("/ref-etat-bs")
    @Timed
    public ResponseEntity<List<RefEtatBsDTO>> getAllRefEtatBs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefEtatBs");
        Page<RefEtatBsDTO> page = refEtatBsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-etat-bs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ref-etat-bs/:id : get the "id" refEtatBs.
     *
     * @param id the id of the refEtatBsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refEtatBsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-etat-bs/{id}")
    @Timed
    public ResponseEntity<RefEtatBsDTO> getRefEtatBs(@PathVariable Long id) {
        log.debug("REST request to get RefEtatBs : {}", id);
        RefEtatBsDTO refEtatBsDTO = refEtatBsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refEtatBsDTO));
    }

    /**
     * DELETE  /ref-etat-bs/:id : delete the "id" refEtatBs.
     *
     * @param id the id of the refEtatBsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-etat-bs/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefEtatBs(@PathVariable Long id) {
        log.debug("REST request to delete RefEtatBs : {}", id);
        refEtatBsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ref-etat-bs?query=:query : search for the refEtatBs corresponding
     * to the query.
     *
     * @param query the query of the refEtatBs search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-etat-bs")
    @Timed
    public ResponseEntity<List<RefEtatBsDTO>> searchRefEtatBs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefEtatBs for query {}", query);
        Page<RefEtatBsDTO> page = refEtatBsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-etat-bs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
