package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.UserService;
import com.gaconnecte.auxilium.service.dto.AgencyDTO;
import com.gaconnecte.auxilium.service.dto.ApecDTO;
import com.gaconnecte.auxilium.service.dto.AssitancesDTO;
import com.gaconnecte.auxilium.service.dto.HistoryDTO;
import com.gaconnecte.auxilium.service.dto.QuotationDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
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

/**
 * REST controller for managing Journal.
 */
@RestController
@RequestMapping("/api")
public class HistoryResource {

    private final Logger log = LoggerFactory.getLogger(HistoryResource.class);

    private static final String ENTITY_NAME = "history";

    private final HistoryService historyService;
    
    private final UserService userService;
    

    public HistoryResource(HistoryService historyService, UserService userService) {
        this.historyService = historyService;
        this.userService = userService;
    }

    /**
     * POST  /journals : Create a new history.
     *
     * @param journalDTO the journalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new journalDTO, or with status 400 (Bad Request) if the journal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/historys")
    @Timed
    public ResponseEntity<HistoryDTO> createHistory(@Valid @RequestBody HistoryDTO historyDTO) throws URISyntaxException {
        log.debug("REST request to save history : {}", historyDTO);
        String login = SecurityUtils.getCurrentUserLogin();
        User user = userService.findOneUserByLogin(login);
        if (historyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new history cannot already have an ID")).body(null);
        }
        historyDTO.setUserId(user.getId());
      HistoryDTO result = historyService.save(historyDTO);
        return ResponseEntity.created(new URI("/api/historys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/historys/{entityId}/{entityName}")
    @Timed
    public List<HistoryDTO> getHistoryByEntity(@PathVariable Long entityId, @PathVariable String entityName ) {
        log.debug("REST request to get all Histories By Entity");
        return historyService.findHistoryByEntity(entityId, entityName);
    }
    
    @GetMapping("/historys/quotation/{actionId}/{entityId}/{entityName}")
    @Timed
    public QuotationDTO getQuotationByEntityAndHistory(@PathVariable Long actionId, @PathVariable Long entityId, @PathVariable String entityName ) {
        log.debug("REST request to get Quotation By Histories By Entity");
        return historyService.findHistoryQuotationByAction(actionId, entityId, entityName);
    }
    
    @GetMapping("/historys/quotationHistory/{historyId}")
    @Timed
    public QuotationDTO findHistoryQuotationById(@PathVariable Long historyId) {
        log.debug("REST request to get Quotation By Histories By Entity");
        return historyService.findHistoryQuotationById(historyId);
    }
    
    
    @GetMapping("/historys/apecHistory/{historyId}")
    @Timed
    public ApecDTO findHistoryAccordById(@PathVariable Long historyId) {
        log.debug("REST request to get Quotation By Histories By Entity");
        return historyService.findHistoryApecById(historyId);
    }
    
    
    
    @GetMapping("/historys/quotation/{sinisterPecId}")
    @Timed
    public List<HistoryDTO> findListDevisByHistory(@PathVariable Long sinisterPecId) {
        return historyService.findListDevis(sinisterPecId);
    }
    

    @GetMapping("/historys/apec/{sinisterPecId}")
    @Timed
    public List<HistoryDTO> findListAccordByHistory(@PathVariable Long sinisterPecId) {
        return historyService.findListAccord(sinisterPecId);
    }
    
    
   
}
