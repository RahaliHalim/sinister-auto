package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.JournalService;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.ReparateurService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.ExpertDTO;
import com.gaconnecte.auxilium.service.dto.ReparateurDTO;

import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.gaconnecte.auxilium.service.HistoryService;

/**
 * REST controller for managing Reparateur.
 */
@RestController
@RequestMapping("/api")
public class ReparateurResource {

    private final Logger log = LoggerFactory.getLogger(ReparateurResource.class);

    private static final String ENTITY_NAME = "reparateur";

    private final ReparateurService reparateurService;
    @Autowired
    private JournalService journalService;
    @Autowired
    private LoggerService loggerService;
    private final HistoryService historyService;
    public ReparateurResource(ReparateurService reparateurService,HistoryService historyService) {
        this.reparateurService = reparateurService;
        this.historyService = historyService;
    }

    /**
     * POST  /reparateurs : Create a new reparateur.
     *
     * @param reparateurDTO the reparateurDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reparateurDTO, or with status 400 (Bad Request) if the reparateur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reparateurs")
    @Timed
    public ResponseEntity<ReparateurDTO> createReparateur(@Valid @RequestBody ReparateurDTO reparateurDTO) throws URISyntaxException {
        log.debug("REST request to save Reparateur : {}", reparateurDTO);
       
        if (reparateurDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new reparateur cannot already have an ID")).body(null);
        }
        ReparateurDTO result = reparateurService.save(reparateurDTO);
        historyService.historysave("Reparateur",result.getId(),result,result,0,0, "Creation");
        return ResponseEntity.created(new URI("/api/reparateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reparateurs : Updates an existing reparateur.
     *
     * @param reparateurDTO the reparateurDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reparateurDTO,
     * or with status 400 (Bad Request) if the reparateurDTO is not valid,
     * or with status 500 (Internal Server Error) if the reparateurDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reparateurs")
    @Timed
    public ResponseEntity<ReparateurDTO> updateReparateur(@Valid @RequestBody ReparateurDTO reparateurDTO) throws URISyntaxException {
        log.debug("REST request to update Reparateur : {}", reparateurDTO);
        if (reparateurDTO.getId() == null) {
            return createReparateur(reparateurDTO);
        }
        ReparateurDTO oldReparateur = reparateurService.findOne(reparateurDTO.getId());
        ReparateurDTO result = reparateurService.save(reparateurDTO);
        reparateurService.historysaveReparateur("Reparateur",result.getId(),oldReparateur,result,"Modification");
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reparateurDTO.getId().toString()))
            .body(result);
    }
    
    /**
     * PUT  /reparateurs : Affect User to an existing reparateur.
     *
     * @param reparateurDTO the reparateurDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reparateurDTO,
     * or with status 400 (Bad Request) if the reparateurDTO is not valid,
     * or with status 500 (Internal Server Error) if the reparateurDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */


    /**
     * GET  /reparateurs : get all the reparateurs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reparateurs in body
     */
    @GetMapping("/reparateurs")
    @Timed
    public ResponseEntity<List<ReparateurDTO>> getAllReparateurs() {
        log.debug("REST request to get a page of Reparateurs");
        List<ReparateurDTO> page = reparateurService.findAll();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(page));
    }
 
    
    
    
 
    /**
     * GET  /reparateurs/:id : get the "id" reparateur.
     *
     * @param id the id of the reparateurDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reparateurDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reparateurs/{id}")
    @Timed
    public ResponseEntity<ReparateurDTO> getReparateur(@PathVariable Long id) {
        log.debug("REST request to get Reparateur : {}", id);
        ReparateurDTO reparateurDTO = reparateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reparateurDTO));
    }
    
   
    @PostMapping("/reparateurs/byNameRegistreCommerce")
    @Timed
    public ResponseEntity<ReparateurDTO> getRepCompanyByRegistreCommerce(@RequestBody ReparateurDTO reparateurDTO) {
        log.debug("REST request to get reparateur : {}", reparateurDTO);
        ReparateurDTO reparateurDTOExist = reparateurService.getReparateurByRegistreCommerce(reparateurDTO.getRegistreCommerce(),reparateurDTO.getRaisonSociale());
        if (reparateurDTOExist == null) {
        	reparateurDTOExist = new ReparateurDTO();
        }
        return ResponseEntity.ok().body(reparateurDTOExist);
    }
    /**
     * GET  /reparateurs/:id : get the reparateur by idUser.
     *
     */
    
  

    /**
     * DELETE  /reparateurs/:id : delete the "id" reparateur.
     *
     * @param id the id of the reparateurDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reparateurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteReparateur(@PathVariable Long id) {
        log.debug("REST request to delete Reparateur : {}", id);
        reparateurService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/reparateurs?query=:query : search for the reparateur corresponding
     * to the query.
     *
     * @param query the query of the reparateur search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/reparateurs")
    @Timed
    public ResponseEntity<List<ReparateurDTO>> searchReparateurs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Reparateurs for query {}", query);
        Page<ReparateurDTO> page = reparateurService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/reparateurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * GET  /reparateurs : get all the reparateurs of a selected Gouvernorat.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reparateurs in body
     */
    @GetMapping("/reparateurs/gouvernorat/{id}")
    @Timed
    public ResponseEntity<List<ReparateurDTO>> getReparateurByGouvernorat(@PathVariable Long id) {
        log.debug("REST request to get Reparateurs of a Gouvernorat : {}", id);
        List<ReparateurDTO> reparateurDTO = reparateurService.findByGouvernorat(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reparateurDTO));
    }
    /**
     * GET /service-rmqs/:id : get the "id" serviceRmq.
     *
     * @param id the id of the serviceRmqDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * serviceRmqDTO, or with status 404 (Not Found)
     */
   
    @PutMapping("/reparateurs/bloquemotif/{id}/{motifs}")
    @Timed
    public ResponseEntity<ReparateurDTO> createBloqueurMotifRmq(@PathVariable Long id, @PathVariable Long[] motifs) {
        log.debug("REST request to bloquerrr motiff reparateur : {}", id);
        ReparateurDTO result = null;
        try {
        	ReparateurDTO reparateurDTO = reparateurService.findOne(id);
            journalService.journalisationReparateurMotif("Bloquer Reparateur", SecurityUtils.getCurrentUserLogin(), 202L, reparateurDTO , motifs);
            return ResponseEntity.ok()
                .body(result);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN).body(result);
        }
    } 
    
       
}
