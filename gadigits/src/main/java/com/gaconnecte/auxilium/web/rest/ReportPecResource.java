/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.ViewSinisterPecMonitoringService;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPecMonitoringDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing report assistance.
 * @author hannibaal
 */
@RestController
@RequestMapping("/api/report/pec")
public class ReportPecResource {

    private final Logger log = LoggerFactory.getLogger(ReportPecResource.class);

    private final ViewSinisterPecMonitoringService viewSinisterPecMonitoringService;

    public ReportPecResource(ViewSinisterPecMonitoringService viewSinisterPecMonitoringService) {
        this.viewSinisterPecMonitoringService = viewSinisterPecMonitoringService;
    }

    /**
     * POST  /report-pec-monitoring : get all report-pec-monitoring line.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of report-pec-monitoring in body
     */
    @PostMapping("/report-pec-monitoring")
    @Timed
    public List<ViewSinisterPecMonitoringDTO> getAllReportTugPerformanceLine(@RequestBody SearchDTO searchDTO) {
        log.debug("REST request to get all ReportTugPerformance line");
        return viewSinisterPecMonitoringService.findAll(searchDTO);
    }
   @GetMapping("/report-pec-monitoring")
    @Timed
    public List<ViewSinisterPecMonitoringDTO> getAll() {
        return viewSinisterPecMonitoringService.findAll();
    }


}
