package com.gaconnecte.auxilium.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.ReportFrequencyRateService;
import com.gaconnecte.auxilium.service.ViewSuiviDossiersService;
import com.gaconnecte.auxilium.service.dto.ReportFrequencyRateDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewSuiviDossiersDTO;

@RestController
@RequestMapping("/api/view")
public class ReportFrequencyRateResource {

	private final Logger log = LoggerFactory.getLogger(ReportFrequencyRateResource.class);
    
    
    private final ReportFrequencyRateService reportFrequencyRateService;
    
    
    @Autowired
    private LoggerService loggerService;
    
    
 public	ReportFrequencyRateResource (ReportFrequencyRateService reportFrequencyRateService) {
    	
    	this.reportFrequencyRateService = reportFrequencyRateService ;
    	
    }

 
 @PostMapping("/reportFrequencyyy")
 @Timed
 public List<ReportFrequencyRateDTO> getAll(@RequestBody SearchDTO searchDTO) {
     return reportFrequencyRateService.findAll(searchDTO);
 }

 @PostMapping("/reportFrequency")
 @Timed
 public List<ReportFrequencyRateDTO> findDossiersServices(@RequestBody SearchDTO searchDTO) {
     return reportFrequencyRateService.Search(searchDTO);

 }
 
}
