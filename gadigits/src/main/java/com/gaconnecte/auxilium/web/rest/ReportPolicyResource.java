/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.view.ViewPolicyIndicator;
import com.gaconnecte.auxilium.service.ViewPolicyService;
import com.gaconnecte.auxilium.service.dto.SearchDTO;

/**
 * REST controller for managing report assistance.
 * @author hannibaal
 */
@RestController
@RequestMapping("/api/report/policy")
public class ReportPolicyResource {

    private final Logger log = LoggerFactory.getLogger(ReportPolicyResource.class);

    private final ViewPolicyService viewPolicyService;

    public ReportPolicyResource(ViewPolicyService viewPolicyService) {
        this.viewPolicyService = viewPolicyService;
    }

    /**
     * POST  /report-pec-monitoring : get all report-pec-monitoring line.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of report-pec-monitoring in body
     */
    @PostMapping("/indicator")
    @Timed
    public List<ViewPolicyIndicator> getAllPolicyIndicators(@RequestBody SearchDTO searchDTO) {
        log.debug("REST request to get all policy indicator line");
        return viewPolicyService.findAllPolicyIndicators(searchDTO);
    }
}
