package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefMaterielService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefMaterielDTO;
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
 * REST controller for managing RefMateriel.
 */
@RestController
@RequestMapping("/api")
public class RefMaterielResource {

    private final Logger log = LoggerFactory.getLogger(RefMaterielResource.class);

    private static final String ENTITY_NAME = "refMateriel";

    private final RefMaterielService refMaterielService;

    public RefMaterielResource(RefMaterielService refMaterielService) {
        this.refMaterielService = refMaterielService;
    }

    /**
     * POST  /ref-materiels : Create a new refMateriel.
     *
     * @param refMaterielDTO the refMaterielDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refMaterielDTO, or with status 400 (Bad Request) if the refMateriel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-materiels")
    @Timed
    public ResponseEntity<RefMaterielDTO> createRefMateriel(@Valid @RequestBody RefMaterielDTO refMaterielDTO) throws URISyntaxException {
        log.debug("REST request to save RefMateriel : {}", refMaterielDTO);
        if (refMaterielDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refMateriel cannot already have an ID")).body(null);
        }
        RefMaterielDTO result = refMaterielService.save(refMaterielDTO);
        return ResponseEntity.created(new URI("/api/ref-materiels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-materiels : Updates an existing refMateriel.
     *
     * @param refMaterielDTO the refMaterielDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refMaterielDTO,
     * or with status 400 (Bad Request) if the refMaterielDTO is not valid,
     * or with status 500 (Internal Server Error) if the refMaterielDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-materiels")
    @Timed
    public ResponseEntity<RefMaterielDTO> updateRefMateriel(@Valid @RequestBody RefMaterielDTO refMaterielDTO) throws URISyntaxException {
        log.debug("REST request to update RefMateriel : {}", refMaterielDTO);
        if (refMaterielDTO.getId() == null) {
            return createRefMateriel(refMaterielDTO);
        }
        RefMaterielDTO result = refMaterielService.save(refMaterielDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refMaterielDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-materiels : get all the refMateriels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refMateriels in body
     */
    @GetMapping("/ref-materiels")
    @Timed
    public ResponseEntity<List<RefMaterielDTO>> getAllRefMateriels(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefMateriels");
        Page<RefMaterielDTO> page = refMaterielService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-materiels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ref-materiels/:id : get the "id" refMateriel.
     *
     * @param id the id of the refMaterielDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refMaterielDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-materiels/{id}")
    @Timed
    public ResponseEntity<RefMaterielDTO> getRefMateriel(@PathVariable Long id) {
        log.debug("REST request to get RefMateriel : {}", id);
        RefMaterielDTO refMaterielDTO = refMaterielService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refMaterielDTO));
    }

    /**
     * DELETE  /ref-materiels/:id : delete the "id" refMateriel.
     *
     * @param id the id of the refMaterielDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-materiels/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefMateriel(@PathVariable Long id) {
        log.debug("REST request to delete RefMateriel : {}", id);
        refMaterielService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ref-materiels?query=:query : search for the refMateriel corresponding
     * to the query.
     *
     * @param query the query of the refMateriel search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-materiels")
    @Timed
    public ResponseEntity<List<RefMaterielDTO>> searchRefMateriels(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefMateriels for query {}", query);
        Page<RefMaterielDTO> page = refMaterielService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-materiels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
