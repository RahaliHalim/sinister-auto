package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.AgentGeneralService;
import com.gaconnecte.auxilium.service.CustomUserService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.AgentGeneralDTO;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AgentGeneral.
 */
@RestController
@RequestMapping("/api")
public class AgentGeneralResource {

    private static final String ROLE_AGGENERAL = "ROLE_AGGENERAL";

	private final Logger log = LoggerFactory.getLogger(AgentGeneralResource.class);

    private static final String ENTITY_NAME = "agentGeneral";

    private final AgentGeneralService agentGeneralService;

    @Autowired
    private CustomUserService customUserService;
    
    public AgentGeneralResource(AgentGeneralService agentGeneralService) {
        this.agentGeneralService = agentGeneralService;
    }

    /**
     * POST  /agent-generals : Create a new agentGeneral.
     *
     * @param agentGeneralDTO the agentGeneralDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agentGeneralDTO, or with status 400 (Bad Request) if the agentGeneral has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agent-generals")
    @Timed
    public ResponseEntity<AgentGeneralDTO> createAgentGeneral(@Valid @RequestBody AgentGeneralDTO agentGeneralDTO) throws URISyntaxException {
        log.debug("REST request to save AgentGeneral : {}", agentGeneralDTO);
        if (agentGeneralDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new agentGeneral cannot already have an ID")).body(null);
        }
        
        AgentGeneralDTO result = agentGeneralService.save(agentGeneralDTO);
        //customUserService.addRoleToUser(result.getPersonnePhysiqueId(), ROLE_AGGENERAL);
        return ResponseEntity.created(new URI("/api/agent-generals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

     

    /**
     * PUT  /agent-generals : Updates an existing agentGeneral.
     *
     * @param agentGeneralDTO the agentGeneralDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agentGeneralDTO,
     * or with status 400 (Bad Request) if the agentGeneralDTO is not valid,
     * or with status 500 (Internal Server Error) if the agentGeneralDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agent-generals")
    @Timed
    public ResponseEntity<AgentGeneralDTO> updateAgentGeneral(@Valid @RequestBody AgentGeneralDTO agentGeneralDTO) throws URISyntaxException {
        log.debug("REST request to update AgentGeneral : {}", agentGeneralDTO);
        if (agentGeneralDTO.getId() == null) {
            return createAgentGeneral(agentGeneralDTO);
        }
        AgentGeneralDTO result = agentGeneralService.save(agentGeneralDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, agentGeneralDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agent-generals : get all the agentGenerals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of agentGenerals in body
     */
    @GetMapping("/agent-generals")
    @Timed
    public ResponseEntity<List<AgentGeneralDTO>> getAllAgentGenerals(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AgentGenerals");
        Page<AgentGeneralDTO> page = agentGeneralService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/agent-generals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /agent-generals/:id : get the "id" agentGeneral.
     *
     * @param id the id of the agentGeneralDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agentGeneralDTO, or with status 404 (Not Found)
     */
    @GetMapping("/agent-generals/{id}")
    @Timed
    public ResponseEntity<AgentGeneralDTO> getAgentGeneral(@PathVariable Long id) {
        log.debug("REST request to get AgentGeneral : {}", id);
        AgentGeneralDTO agentGeneralDTO = agentGeneralService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(agentGeneralDTO));
    }

    /**
     * DELETE  /agent-generals/:id : delete the "id" agentGeneral.
     *
     * @param id the id of the agentGeneralDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agent-generals/{id}")
    @Timed
    public ResponseEntity<Void> deleteAgentGeneral(@PathVariable Long id) {
        log.debug("REST request to delete AgentGeneral : {}", id);
        agentGeneralService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/agent-generals?query=:query : search for the agentGeneral corresponding
     * to the query.
     *
     * @param query the query of the agentGeneral search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/agent-generals")
    @Timed
    public ResponseEntity<List<AgentGeneralDTO>> searchAgentGenerals(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of AgentGenerals for query {}", query);
        Page<AgentGeneralDTO> page = agentGeneralService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/agent-generals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
