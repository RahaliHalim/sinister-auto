package com.gaconnecte.auxilium.web.rest;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.DetailsQuotationService;
import com.gaconnecte.auxilium.service.dto.DetailsQuotationDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class DetailsQuotationResource {

    private final Logger log = LoggerFactory.getLogger(DetailsQuotationResource.class);

    private static final String ENTITY_NAME = "details_quotation";

    private final DetailsQuotationService detailsQuotationService;

    @Autowired
    private LoggerService loggerService;

    public DetailsQuotationResource(DetailsQuotationService detailsQuotationService) {
        this.detailsQuotationService = detailsQuotationService;
    }

    @PostMapping("/details-quotation")
    @Timed
    public ResponseEntity<DetailsQuotationDTO> createDetailsQuotation(
            @Valid @RequestBody DetailsQuotationDTO detailsQuotationDTO) throws URISyntaxException {
        log.debug("REST request to save DetailsQuotation : {}", detailsQuotationDTO.getId());
        if (detailsQuotationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
                    "A new DetailsQuotation cannot already have an ID")).body(null);
        }
        DetailsQuotationDTO result = detailsQuotationService.save(detailsQuotationDTO);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PutMapping("/details-quotation")
    @Timed
    public ResponseEntity<DetailsQuotationDTO> updateDetailsQuotation(
            @Valid @RequestBody DetailsQuotationDTO detailsQuotationDTO) throws URISyntaxException {
        log.debug("REST request to update DetailsQuotation : {}", detailsQuotationDTO.getId());
        if (detailsQuotationDTO.getId() == null) {
            return createDetailsQuotation(detailsQuotationDTO);
        }
        DetailsQuotationDTO result = detailsQuotationService.save(detailsQuotationDTO);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @GetMapping("/details-quotation/{id}")
    @Timed
    public ResponseEntity<DetailsQuotationDTO> getDetailsQuotation(@PathVariable Long id) {
        log.debug("REST request to get DetailsQuotation : {}", id);
        DetailsQuotationDTO detailsQuotationDTO = detailsQuotationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(detailsQuotationDTO));
    }

    @GetMapping("/details-quotation-by-sinister-pec/{id}")
    @Timed
    public ResponseEntity<DetailsQuotationDTO> getDetailsQuotationBySinisterPecId(@PathVariable Long id) {
        log.debug("REST request to get DetailsQuotation : {}", id);
        DetailsQuotationDTO detailsQuotationDTO = detailsQuotationService.findOneBySinisterPecId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(detailsQuotationDTO));
    }

    @DeleteMapping("/details-quotation/{id}")
    @Timed
    public ResponseEntity<Void> deleteDetailsQuotation(@PathVariable Long id) {
        log.debug("REST request to delete DetailsQuotation : {}", id);
        detailsQuotationService.delete(id);
        return ResponseEntity.ok().build();
    }

}