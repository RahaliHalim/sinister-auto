package com.gaconnecte.auxilium.web.rest;


import java.net.URISyntaxException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.QuotationMPService;
import com.gaconnecte.auxilium.service.dto.QuotationMPDTO;

import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class QuotationMPResource {
	
	private final Logger log = LoggerFactory.getLogger(QuotationResource.class);
	private final QuotationMPService quotationMPService;
	
	
	public QuotationMPResource(QuotationMPService quotationMPService) {
		this.quotationMPService = quotationMPService;
	}
	
	@PostMapping("/quotation-mp")
	@Timed
	public ResponseEntity<QuotationMPDTO> createQuotation(@RequestBody QuotationMPDTO quotationMP)
			throws URISyntaxException {
		log.debug("REST request to save Quotation with Parts: {}", quotationMP);
		QuotationMPDTO result = quotationMPService.save(quotationMP);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
	}
	
	@PutMapping("/quotation-mp")
	@Timed
	public ResponseEntity<QuotationMPDTO> updateQuotation(@RequestBody QuotationMPDTO quotationMP)
			throws URISyntaxException {
		log.debug("REST request to save Quotation with Parts: {}", quotationMP);
		if(quotationMP.getId() == null) {
			return createQuotation(quotationMP);
		}
		QuotationMPDTO result = quotationMPService.save(quotationMP);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
	}
	
	@GetMapping("/quotation-mp/{id}")
    @Timed
    public ResponseEntity<QuotationMPDTO> getQuotationMPBySinisterPec(@PathVariable Long id) {
        log.debug("REST request to get QuotationMP : {}", id);
        QuotationMPDTO quotationMPDTO = quotationMPService.findOneBySinisterPec(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(quotationMPDTO));
    }

}
