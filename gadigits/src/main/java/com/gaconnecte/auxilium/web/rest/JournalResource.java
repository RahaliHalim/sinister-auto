package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.JournalService;
import com.gaconnecte.auxilium.service.dto.DevisDTO;
import com.gaconnecte.auxilium.service.dto.JournalDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
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
 * REST controller for managing Journal.
 */
@RestController
@RequestMapping("/api")
public class JournalResource {

    private final Logger log = LoggerFactory.getLogger(JournalResource.class);

    private static final String ENTITY_NAME = "journal";

    private final JournalService journalService;

    public JournalResource(JournalService journalService) {
        this.journalService = journalService;
    }

    /**
     * POST  /journals : Create a new journal.
     *
     * @param journalDTO the journalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new journalDTO, or with status 400 (Bad Request) if the journal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/journals")
    @Timed
    public ResponseEntity<JournalDTO> createJournal(@Valid @RequestBody JournalDTO journalDTO) throws URISyntaxException {
        log.debug("REST request to save Journal : {}", journalDTO);
        if (journalDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new journal cannot already have an ID")).body(null);
        }
        JournalDTO result = journalService.save(journalDTO);
        return ResponseEntity.created(new URI("/api/journals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /journals : Updates an existing journal.
     *
     * @param journalDTO the journalDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated journalDTO,
     * or with status 400 (Bad Request) if the journalDTO is not valid,
     * or with status 500 (Internal Server Error) if the journalDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/journals")
    @Timed
    public ResponseEntity<JournalDTO> updateJournal(@Valid @RequestBody JournalDTO journalDTO) throws URISyntaxException {
        log.debug("REST request to update Journal : {}", journalDTO);
        if (journalDTO.getId() == null) {
            return createJournal(journalDTO);
        }
        JournalDTO result = journalService.save(journalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, journalDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /journals : get all the journals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of journals in body
     */
    @GetMapping("/journals")
    @Timed
    public ResponseEntity<List<JournalDTO>> getAllJournals(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Journals");
        Page<JournalDTO> page = journalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/journals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /journals/:id : get the "id" journal.
     *
     * @param id the id of the journalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the journalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/journal/remorqueur/{remorqueurId}")
    @Timed
    public ResponseEntity<JournalDTO> getjournalByRemorqueur(@PathVariable Long remorqueurId) {
        log.debug("REST request to get a page of All journal by remorqueur");
        JournalDTO journal = journalService.findJournalByRemorqueur(remorqueurId);
        return new ResponseEntity<>(journal, HttpStatus.OK);
    }

    /**
     * GET  /journals/:id : get the "id" journal.
     *
     * @param id the id of the journalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the journalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/journal/reparateur/{reparateurId}")
    @Timed
    public ResponseEntity<JournalDTO> getjournalByReparateur(@PathVariable Long reparateurId) {
        log.debug("REST request to get a page of All journal by remorqueur");
        JournalDTO journal = journalService.findJournalByReparateur(reparateurId);
        return new ResponseEntity<>(journal, HttpStatus.OK);
    }
    /**
     * GET  /journals/:id : get the "id" journal.
     *
     * @param id the id of the journalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the journalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/journal/prestaion-pec/{prestationPecId}")
    @Timed
    public ResponseEntity<JournalDTO> getjournalByPrestationPec(@PathVariable Long prestationPecId) {
        log.debug("REST request to get a page of All journal by prestation");
        JournalDTO journal = journalService.findJournalByPrestationPec(prestationPecId);
        return new ResponseEntity<>(journal, HttpStatus.OK);
    }
    /**
     * GET  /journals/:id : get the "id" journal.
     *
     * @param id the id of the journalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the journalDTO, or with status 404 (Not Found)
     */
    
    /*@GetMapping("/journal/quotation/{id}")
    @Timed
    public ResponseEntity<JournalDTO> getjournalByQuotation(@PathVariable Long id) {
        log.debug("REST request to get a page of All journal by quotation non confirme");
        JournalDTO journal = journalService.findJournalByQuotation(id);
        return new ResponseEntity<>(journal, HttpStatus.OK);
    }*/
    
    @GetMapping("/journals/{id}")
    @Timed
    public ResponseEntity<JournalDTO> getJournal(@PathVariable Long id) {
        log.debug("REST request to get Journal : {}", id);
        JournalDTO journalDTO = journalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(journalDTO));
    }

    /**
     * DELETE  /journals/:id : delete the "id" journal.
     *
     * @param id the id of the journalDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/journals/{id}")
    @Timed
    public ResponseEntity<Void> deleteJournal(@PathVariable Long id) {
        log.debug("REST request to delete Journal : {}", id);
        journalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/journals?query=:query : search for the journal corresponding
     * to the query.
     *
     * @param query the query of the journal search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/journals")
    @Timed
    public ResponseEntity<List<JournalDTO>> searchJournals(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Journals for query {}", query);
        Page<JournalDTO> page = journalService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/journals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
