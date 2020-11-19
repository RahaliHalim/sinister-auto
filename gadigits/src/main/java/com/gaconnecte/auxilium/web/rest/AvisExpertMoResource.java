package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.AvisExpertMoService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.AvisExpertMoDTO;
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
 * REST controller for managing AvisExpertMo.
 */
@RestController
@RequestMapping("/api")
public class AvisExpertMoResource {

    private final Logger log = LoggerFactory.getLogger(AvisExpertMoResource.class);

    private static final String ENTITY_NAME = "avisExpertMo";

    private final AvisExpertMoService avisExpertMoService;

    public AvisExpertMoResource(AvisExpertMoService avisExpertMoService) {
        this.avisExpertMoService = avisExpertMoService;
    }

    /**
     * POST  /avis-expert-mos : Create a new avisExpertMo.
     *
     * @param avisExpertMoDTO the avisExpertMoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new avisExpertMoDTO, or with status 400 (Bad Request) if the avisExpertMo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/avis-expert-mos")
    @Timed
    public ResponseEntity<AvisExpertMoDTO> createAvisExpertMo(@Valid @RequestBody AvisExpertMoDTO avisExpertMoDTO) throws URISyntaxException {
        log.debug("REST request to save AvisExpertMo : {}", avisExpertMoDTO);
        if (avisExpertMoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new avisExpertMo cannot already have an ID")).body(null);
        }
        AvisExpertMoDTO result = avisExpertMoService.save(avisExpertMoDTO);
        return ResponseEntity.created(new URI("/api/avis-expert-mos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /avis-expert-mos : Updates an existing avisExpertMo.
     *
     * @param avisExpertMoDTO the avisExpertMoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated avisExpertMoDTO,
     * or with status 400 (Bad Request) if the avisExpertMoDTO is not valid,
     * or with status 500 (Internal Server Error) if the avisExpertMoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/avis-expert-mos")
    @Timed
    public ResponseEntity<AvisExpertMoDTO> updateAvisExpertMo(@Valid @RequestBody AvisExpertMoDTO avisExpertMoDTO) throws URISyntaxException {
        log.debug("REST request to update AvisExpertMo : {}", avisExpertMoDTO);
        if (avisExpertMoDTO.getId() == null) {
            return createAvisExpertMo(avisExpertMoDTO);
        }
        AvisExpertMoDTO result = avisExpertMoService.save(avisExpertMoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, avisExpertMoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /avis-expert-mos : get all the avisExpertMos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of avisExpertMos in body
     */
    @GetMapping("/avis-expert-mos")
    @Timed
    public ResponseEntity<List<AvisExpertMoDTO>> getAllAvisExpertMos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AvisExpertMos");
        Page<AvisExpertMoDTO> page = avisExpertMoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/avis-expert-mos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /avis-expert-mos/:id : get the "id" avisExpertMo.
     *
     * @param id the id of the avisExpertMoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the avisExpertMoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/avis-expert-mos/{id}")
    @Timed
    public ResponseEntity<AvisExpertMoDTO> getAvisExpertMo(@PathVariable Long id) {
        log.debug("REST request to get AvisExpertMo : {}", id);
        AvisExpertMoDTO avisExpertMoDTO = avisExpertMoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(avisExpertMoDTO));
    }

    /**
     * DELETE  /avis-expert-mos/:id : delete the "id" avisExpertMo.
     *
     * @param id the id of the avisExpertMoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/avis-expert-mos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAvisExpertMo(@PathVariable Long id) {
        log.debug("REST request to delete AvisExpertMo : {}", id);
        avisExpertMoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/avis-expert-mos?query=:query : search for the avisExpertMo corresponding
     * to the query.
     *
     * @param query the query of the avisExpertMo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/avis-expert-mos")
    @Timed
    public ResponseEntity<List<AvisExpertMoDTO>> searchAvisExpertMos(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of AvisExpertMos for query {}", query);
        Page<AvisExpertMoDTO> page = avisExpertMoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/avis-expert-mos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
