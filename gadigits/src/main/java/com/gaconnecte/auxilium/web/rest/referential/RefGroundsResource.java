package com.gaconnecte.auxilium.web.rest.referential;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.referential.RefGroundsService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.referential.dto.RefGroundsDTO;
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
import java.util.Set;

/**
 * REST controller for managing RefGrounds.
 */
@RestController
@RequestMapping("/api")
public class RefGroundsResource {

    private final Logger log = LoggerFactory.getLogger(RefGroundsResource.class);

    private static final String ENTITY_NAME = "refGrounds";

    private final RefGroundsService refGroundsService;
    
    private final HistoryService historyService;
    public RefGroundsResource(RefGroundsService refGroundsService, HistoryService historyService) {
        this.refGroundsService = refGroundsService;
        this.historyService = historyService;
    }

    /**
     * POST  /ref-grounds : Create a new refGrounds.
     *
     * @param refGroundsDTO the refGroundsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refGroundsDTO, or with status 400 (Bad Request) if the refGrounds has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-grounds")
    @Timed
    public ResponseEntity<RefGroundsDTO> createRefGrounds(@Valid @RequestBody RefGroundsDTO refGroundsDTO) throws URISyntaxException {
        log.debug("REST request to save RefGrounds : {}", refGroundsDTO);
        if (refGroundsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refGrounds cannot already have an ID")).body(null);
        }
        RefGroundsDTO result = refGroundsService.save(refGroundsDTO);
       // historyService.historysave("Jour Ferier",  result.getId(), "CREATION");
        return ResponseEntity.created(new URI("/api/ref-grounds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-grounds : Updates an existing refGrounds.
     *
     * @param refGroundsDTO the refGroundsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refGroundsDTO,
     * or with status 400 (Bad Request) if the refGroundsDTO is not valid,
     * or with status 500 (Internal Server Error) if the refGroundsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-grounds")
    @Timed
    public ResponseEntity<RefGroundsDTO> updateRefGrounds(@Valid @RequestBody RefGroundsDTO refGroundsDTO) throws URISyntaxException {
        log.debug("REST request to update RefGrounds : {}", refGroundsDTO);
        if (refGroundsDTO.getId() == null) {
            return createRefGrounds(refGroundsDTO);
        }
        RefGroundsDTO result = refGroundsService.save(refGroundsDTO);
      //  historyService.historysave("Jour Ferier", result.getId(), "UPDATE");
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refGroundsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-grounds : get all the refGroundss.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refGroundss in body
     */
    @GetMapping("/ref-grounds")
    @Timed
    public ResponseEntity<List<RefGroundsDTO>> getAllRefGrounds(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefGrounds");
        Page<RefGroundsDTO> page = refGroundsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-grounds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ref-grounds : get all the refGroundss.
     *
     * @param id
     * @return the ResponseEntity with status 200 (OK) and the list of refGroundss in body
     */
    @GetMapping("/ref-grounds/status/{id}")
    @Timed
    public ResponseEntity<Set<RefGroundsDTO>> getAllRefGroundsByStatus(@PathVariable Long id) {
        log.debug("REST request to get a page of RefGrounds");
        Set<RefGroundsDTO> grounds = refGroundsService.findByStatus(id);
        return new ResponseEntity<>(grounds, HttpStatus.OK);
    }
    
    /**
     * GET  /ref-grounds/:id : get the "id" refGrounds.
     *
     * @param id the id of the refGroundsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refGroundsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-grounds/{id}")
    @Timed
    public ResponseEntity<RefGroundsDTO> getRefGrounds(@PathVariable Long id) {
        log.debug("REST request to get RefGrounds : {}", id);
        RefGroundsDTO refGroundsDTO = refGroundsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refGroundsDTO));
    }

    /**
     * DELETE  /ref-grounds/:id : delete the "id" refGrounds.
     *
     * @param id the id of the refGroundsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-grounds/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefGrounds(@PathVariable Long id) {
        log.debug("REST request to delete RefGrounds : {}", id);
        refGroundsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
