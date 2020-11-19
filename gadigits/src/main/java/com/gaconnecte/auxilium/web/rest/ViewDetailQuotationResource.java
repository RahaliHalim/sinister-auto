/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.ViewDetailQuotationService;
import com.gaconnecte.auxilium.service.dto.ViewDetailQuotationDTO;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * REST controller for managing detail devis.
 * 
 */
@RestController
@RequestMapping("/api/view")
public class ViewDetailQuotationResource {

    private final Logger log = LoggerFactory.getLogger(ViewDetailQuotationResource.class);

    private final ViewDetailQuotationService viewDetailQuotationService;
    @Autowired
    private LoggerService loggerService;

    public ViewDetailQuotationResource(ViewDetailQuotationService viewDetailQuotationService) {
        this.viewDetailQuotationService = viewDetailQuotationService;
    }

    

    @GetMapping("/detail-quotation/{id}")
    @Timed
    public ViewDetailQuotationDTO findViewDetailQuotationByPec(@PathVariable Long id) {
        log.debug("REST request to get a quotation details by pec {} __________________________________________________________________", id);
        ViewDetailQuotationDTO details = viewDetailQuotationService.findViewDetailQuotationByPec(id);
        log.debug("END request to get a quotation details by pec {} __________________________________________________________________", id);
        return details;
    }

}
