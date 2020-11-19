package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefFractionnementService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefFractionnementDTO;
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
 * REST controller for managing RefFractionnement.
 */
@RestController
@RequestMapping("/api")
public class RefFractionnementResource {

    private final Logger log = LoggerFactory.getLogger(RefFractionnementResource.class);

    private static final String ENTITY_NAME = "refFractionnement";

    private final RefFractionnementService refFractionnementService;

    public RefFractionnementResource(RefFractionnementService refFractionnementService) {
        this.refFractionnementService = refFractionnementService;
    }

    /**
     * POST  /ref-fractionnements : Create a new refFractionnement.
     *
     * @param refFractionnementDTO the refFractionnementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refFractionnementDTO, or with status 400 (Bad Request) if the refFractionnement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-fractionnements")
    @Timed
    public ResponseEntity<RefFractionnementDTO> createRefFractionnement(@Valid @RequestBody RefFractionnementDTO refFractionnementDTO) throws URISyntaxException {
        log.debug("REST request to save RefFractionnement : {}", refFractionnementDTO);
        if (refFractionnementDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refFractionnement cannot already have an ID")).body(null);
        }
        RefFractionnementDTO result = refFractionnementService.save(refFractionnementDTO);
        return ResponseEntity.created(new URI("/api/ref-fractionnements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-fractionnements : Updates an existing refFractionnement.
     *
     * @param refFractionnementDTO the refFractionnementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refFractionnementDTO,
     * or with status 400 (Bad Request) if the refFractionnementDTO is not valid,
     * or with status 500 (Internal Server Error) if the refFractionnementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-fractionnements")
    @Timed
    public ResponseEntity<RefFractionnementDTO> updateRefFractionnement(@Valid @RequestBody RefFractionnementDTO refFractionnementDTO) throws URISyntaxException {
        log.debug("REST request to update RefFractionnement : {}", refFractionnementDTO);
        if (refFractionnementDTO.getId() == null) {
            return createRefFractionnement(refFractionnementDTO);
        }
        RefFractionnementDTO result = refFractionnementService.save(refFractionnementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refFractionnementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-fractionnements : get all the refFractionnements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refFractionnements in body
     */
    @GetMapping("/ref-fractionnements")
    @Timed
    public ResponseEntity<List<RefFractionnementDTO>> getAllRefFractionnements(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefFractionnements");
        Page<RefFractionnementDTO> page = refFractionnementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-fractionnements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ref-fractionnements/:id : get the "id" refFractionnement.
     *
     * @param id the id of the refFractionnementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refFractionnementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-fractionnements/{id}")
    @Timed
    public ResponseEntity<RefFractionnementDTO> getRefFractionnement(@PathVariable Long id) {
        log.debug("REST request to get RefFractionnement : {}", id);
        RefFractionnementDTO refFractionnementDTO = refFractionnementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refFractionnementDTO));
    }

    /**
     * DELETE  /ref-fractionnements/:id : delete the "id" refFractionnement.
     *
     * @param id the id of the refFractionnementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-fractionnements/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefFractionnement(@PathVariable Long id) {
        log.debug("REST request to delete RefFractionnement : {}", id);
        refFractionnementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ref-fractionnements?query=:query : search for the refFractionnement corresponding
     * to the query.
     *
     * @param query the query of the refFractionnement search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-fractionnements")
    @Timed
    public ResponseEntity<List<RefFractionnementDTO>> searchRefFractionnements(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefFractionnements for query {}", query);
        Page<RefFractionnementDTO> page = refFractionnementService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-fractionnements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
