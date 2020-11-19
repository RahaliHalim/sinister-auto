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
import com.gaconnecte.auxilium.service.ComplementaryQuotationService;
import com.gaconnecte.auxilium.service.dto.ComplementaryQuotationDTO;


@RestController
@RequestMapping("/api")
public class ComplementaryQuotationResource {

    private final Logger log = LoggerFactory.getLogger(ComplementaryQuotationResource.class);

    private static final String ENTITY_NAME = "complementaryQuotation";
    private final ComplementaryQuotationService complementaryQuotationService;

        
    @Autowired
    private LoggerService loggerService;

    public ComplementaryQuotationResource(ComplementaryQuotationService complementaryQuotationService) {
        this.complementaryQuotationService = complementaryQuotationService;
       
    }


    @PostMapping("/complementaryQuotation")
    @Timed
    public ResponseEntity<ComplementaryQuotationDTO> createComplementaryQuotation(@Valid @RequestBody ComplementaryQuotationDTO complementaryQuotationDTO) throws URISyntaxException {
        log.debug("REST request to save Complementary Quotation : {}", complementaryQuotationDTO);
        ComplementaryQuotationDTO result = complementaryQuotationService.save(complementaryQuotationDTO);
        return ResponseEntity.created(new URI("/api/complementaryQuotation/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/complementaryQuotation")
    @Timed
    public ResponseEntity<ComplementaryQuotationDTO> updateComplementaryQuotation(@Valid @RequestBody ComplementaryQuotationDTO complementaryQuotationDTO) throws URISyntaxException {
        log.debug("REST request to update Complementary Quotation : {}", complementaryQuotationDTO);
        if (complementaryQuotationDTO.getId() == null) {
            return createComplementaryQuotation(complementaryQuotationDTO);
        }
        ComplementaryQuotationDTO result = complementaryQuotationService.save(complementaryQuotationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, complementaryQuotationDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/complementaryQuotation")
    @Timed
    public ResponseEntity<Set<ComplementaryQuotationDTO>> getAllComplementaryQuotation() {
        log.debug("REST request to get All Primary Quotation");
        Set<ComplementaryQuotationDTO> listComplementaryQuotation = complementaryQuotationService.findAll();
        return new ResponseEntity<>(listComplementaryQuotation, HttpStatus.OK);
    }
    @GetMapping("/complementaryQuotation/prestation/{id}")
    @Timed
    public ResponseEntity<Set<ComplementaryQuotationDTO>> getComplementaryQuotationByIdPEC(@PathVariable Long id) {
        log.debug("REST request to get Complementary Quotation By PEC");
                
     	 try {
        Set<ComplementaryQuotationDTO> listComplementaryQuotationDTO = complementaryQuotationService.findComplementaryQuotationByIdPEC(id);
       
        return new ResponseEntity<>(listComplementaryQuotationDTO, HttpStatus.OK);

         } catch (Exception e) {
             loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
    }
    
     @GetMapping("/complementaryQuotation/{id}")
    @Timed
    public ResponseEntity<ComplementaryQuotationDTO> getComplementaryQuotation(@PathVariable Long id) {
        log.debug("REST request to get Complementary Quotation By Id: {}", id);
        ComplementaryQuotationDTO complementaryQuotationDTO = complementaryQuotationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(complementaryQuotationDTO));
    }

    

}