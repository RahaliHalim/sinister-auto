package com.gaconnecte.auxilium.web.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.QuotationHistoryService;
import com.gaconnecte.auxilium.service.dto.QuotationHistoryDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Assure.
 */
@RestController
@RequestMapping("/api")
public class QuotationHistoryResource {

    private final Logger log = LoggerFactory.getLogger(AssureResource.class);

    private static final String ENTITY_NAME = "QuotationHistory";

    private final QuotationHistoryService quotationHistoryService;

    public QuotationHistoryResource(QuotationHistoryService quotationHistoryService) {
        this.quotationHistoryService = quotationHistoryService;
    }

    
    @PostMapping("/quotationHistory")
    @Timed
    public ResponseEntity<QuotationHistoryDTO> createQuotationHistory(@Valid @RequestBody QuotationHistoryDTO quotationHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save QuotationHistory : {}", quotationHistoryDTO);
        if (quotationHistoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new QuotationHistory cannot already have an ID")).body(null);
        }
        QuotationHistoryDTO result = quotationHistoryService.save(quotationHistoryDTO);
        return ResponseEntity.created(new URI("/api/quotationHistory/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }



}