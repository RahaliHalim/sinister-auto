package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefTarifService;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefTugTruckDTO;
import com.gaconnecte.auxilium.service.dto.RefTarifDTO;
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

/**
 * REST controller for managing RefTarif.
 */
@RestController
@RequestMapping("/api")
public class RefTarifResource {

    private final Logger log = LoggerFactory.getLogger(RefTarifResource.class);

    private static final String ENTITY_NAME = "refTarif";

    private final RefTarifService refTarifService;
    private final HistoryService historyService;
    public RefTarifResource(RefTarifService refTarifService, HistoryService historyService) {
        this.refTarifService = refTarifService;
        this.historyService = historyService;
    }

    /**
     * POST  /refTarifs : Create a new refTarif.
     *
     * @param refTarifDTO the refTarifDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refTarifDTO, or with status 400 (Bad Request) if the refTarif has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/refTarifs")
    @Timed
    public ResponseEntity<RefTarifDTO> createRefTarif(@Valid @RequestBody RefTarifDTO refTarifDTO) throws URISyntaxException {
        log.debug("REST request to save RefTariffffffffff : {}", refTarifDTO);
        if (refTarifDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refTarif cannot already have an ID")).body(null);
        }
        RefTarifDTO result = refTarifService.save(refTarifDTO);
        //historyService.historysave("TARIFS",  result.getId(), "CREATION");
        return ResponseEntity.created(new URI("/api/refTarifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
 
    /**
     * PUT  /refTarifs : Updates an existing refTarif.
     *
     * @param refTarifDTO the refTarifDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refTarifDTO,
     * or with status 400 (Bad Request) if the reftarifDTO is not valid,
     * or with status 500 (Internal Server Error) if the tarifDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/refTarifs")
    @Timed
    public ResponseEntity<RefTarifDTO> updateRefarif(@Valid @RequestBody RefTarifDTO refTarifDTO) throws URISyntaxException {
        log.debug("REST request to update RefTarif : {}", refTarifDTO);
        if (refTarifDTO.getId() == null) {
            return createRefTarif(refTarifDTO);
        }
        RefTarifDTO result = refTarifService.save(refTarifDTO);
       // historyService.historysave("TARIFS",  result.getId(), "UPDATE");
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refTarifDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /RefTarifs : get all the refTarifs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refTarifs in body
     */
    @GetMapping("/refTarifs")
    @Timed
    public ResponseEntity<List<RefTarifDTO>> getAllRefTarifs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of refTarifs");
        Page<RefTarifDTO> page = refTarifService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/refTarifs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /refTarifs/:id : get the "id" refTarif.
     *
     * @param id the id of the refTarifDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refTarifDTO, or with status 404 (Not Found)
     */
    @GetMapping("/refTarifs/{id}")
    @Timed
    public ResponseEntity<RefTarifDTO> getRefTarif(@PathVariable Long id) {
        log.debug("REST request to get RefTarif : {}", id);
        RefTarifDTO refTarifDTO = refTarifService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refTarifDTO));
    }
    /**
     * GET  /refTarifs/:id : get the "id" refTarifs.
     *
     * @param id the id of the refTarifsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refTarifsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/refTarifs/tarifLine/{id}")
    @Timed
    public ResponseEntity<List<RefTarifDTO>> getRefTarifsByTarifLine(@ApiParam Pageable pageable, @PathVariable Long id) {
        log.debug("REST request to get Camion : {}", id);
        Page<RefTarifDTO> refTarifDTO = refTarifService.findByTarifLine(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(refTarifDTO, "/api/refTarifs/tarifLine");
        return new ResponseEntity<>(refTarifDTO.getContent(), headers, HttpStatus.OK);
    }
    /**
     * DELETE  /refTarifs/:id : delete the "id" refTarif.
     *
     * @param id the id of the refTarifDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/refTarifs/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefTarif(@PathVariable Long id) {
        log.debug("REST request to delete RefTarif : {}", id);
        refTarifService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
