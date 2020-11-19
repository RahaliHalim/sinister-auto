package com.gaconnecte.auxilium.web.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import com.codahale.metrics.annotation.Timed;
import org.springframework.http.ResponseEntity;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.PrimaryQuotationService;
import com.gaconnecte.auxilium.service.dto.PrimaryQuotationDTO;


@RestController
@RequestMapping("/api")
public class PrimaryQuotationResource {
    private final Logger log = LoggerFactory.getLogger(PrimaryQuotationResource.class);
    private static final String ENTITY_NAME = "primaryQuotation";
    private final PrimaryQuotationService primaryQuotationService;

        
    @Autowired
    private LoggerService loggerService;

    public PrimaryQuotationResource(PrimaryQuotationService primaryQuotationService) {
        this.primaryQuotationService = primaryQuotationService;
       
    }

    @PostMapping("/primaryQuotation")
    @Timed
    public ResponseEntity<PrimaryQuotationDTO> createPrimaryQuotation(@Valid @RequestBody PrimaryQuotationDTO primaryQuotationDTO) throws URISyntaxException {
        log.debug("REST request to save Primary Quotation : {}", primaryQuotationDTO);
        PrimaryQuotationDTO result = primaryQuotationService.save(primaryQuotationDTO);
        return ResponseEntity.created(new URI("/api/primaryQuotation/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/primaryQuotation")
    @Timed
    public ResponseEntity<PrimaryQuotationDTO> updatePrimaryQuotation(@Valid @RequestBody PrimaryQuotationDTO primaryQuotationDTO) throws URISyntaxException {
        log.debug("REST request to update Primary Quotation : {}", primaryQuotationDTO);
        if (primaryQuotationDTO.getId() == null) {
            return createPrimaryQuotation(primaryQuotationDTO);
        }
        PrimaryQuotationDTO result = primaryQuotationService.save(primaryQuotationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, primaryQuotationDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/primaryQuotation")
    @Timed
    public ResponseEntity<Set<PrimaryQuotationDTO>> getAllPrimaryQuotation() {
        log.debug("REST request to get All Primary Quotation");
        Set<PrimaryQuotationDTO> listPrimaryQuotation = primaryQuotationService.findAll();
        return new ResponseEntity<>(listPrimaryQuotation, HttpStatus.OK);
    }
    /*@GetMapping("/primaryQuotation/prestation/{id}")
    @Timed
    public ResponseEntity<PrimaryQuotationDTO> getPrimaryQuotationByIdPEC(@PathVariable Long id) {
        log.debug("REST request to get Primary Quotation By PEC");
                
     	 try {
        PrimaryQuotationDTO primaryQuotationDTO = primaryQuotationService.findPrimaryQuotationByIdPEC(id);
       
       if (primaryQuotationDTO != null) {
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(primaryQuotationDTO));
        } else {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new PrimaryQuotationDTO()));
            }

         } catch (Exception e) {
             loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
    }*/
    
    
     @GetMapping("/primaryQuotation/{id}")
    @Timed
    public ResponseEntity<PrimaryQuotationDTO> getPrimaryQuotation(@PathVariable Long id) {
        log.debug("REST request to get Primary Quotation By Id: {}", id);
        PrimaryQuotationDTO primaryQuotationDTO = primaryQuotationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(primaryQuotationDTO));
    }

    

}