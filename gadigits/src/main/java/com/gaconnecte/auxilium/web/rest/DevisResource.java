package com.gaconnecte.auxilium.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.xml.bind.JAXBException;

import org.jbpm.process.core.datatype.impl.NewInstanceDataTypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.enumeration.EtatDevis;
import com.gaconnecte.auxilium.domain.enumeration.NaturePiece;
import com.gaconnecte.auxilium.repository.DevisRepository;
import com.gaconnecte.auxilium.service.DevisService;
import com.gaconnecte.auxilium.service.DetailsPiecesService;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.dto.DevisDTO;

import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;


import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
/**
 * REST controller for managing Devis.
 */
@RestController
@RequestMapping("/api")
public class DevisResource {

    private final Logger log = LoggerFactory.getLogger(DevisResource.class);

    private static final String ENTITY_NAME = "devis";

    private final DevisService devisService;

    private final DevisRepository devisRepository;
   
    @Autowired
    private LoggerService loggerService;

    public DevisResource(DevisService devisService ,DevisRepository devisRepository) {
        this.devisService = devisService;
       
        this.devisRepository = devisRepository;
  
        
    }

  
    
    
    @PostMapping("/devis")
    @Timed
    public ResponseEntity<DevisDTO> createDevis(@Valid @RequestBody DevisDTO devisDTO) throws URISyntaxException {
        log.debug("REST request to save Devis : {}", devisDTO);
        if (devisDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new devis cannot already have an ID")).body(null);
        }
        try
        {
        DevisDTO result = devisService.save(devisDTO);
        return ResponseEntity.created(new URI("/api/devis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
        
    }catch (Exception e) {
        loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    }

    /**
     * POST  /devis : soumettre a new devis.
     *
     * @param devisDTO the devisDTO to soumettre
     * @return the ResponseEntity with status 201 (Created) and with body the new devisDTO, or with status 400 (Bad Request) if the devis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/soumettreDevis/{id}")
    @Timed
    public ResponseEntity<DevisDTO> soumettreDevis(@PathVariable Long id) throws URISyntaxException {
        
    	
    	DevisDTO devisDTO = devisService.soumettre(id);
        
         return ResponseEntity.ok()
                 .headers(HeaderUtil.createEntityCancelAlert(ENTITY_NAME, devisDTO.getId().toString()))
                 .body(devisDTO);
    	
    	
    }

    /** Bug750 : Btn MAJ Devis ! work
     * 
     * PUT  /devis : Updates an existing devis.
     *
     * @param devisDTO the devisDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated devisDTO,
     * or with status 400 (Bad Request) if the devisDTO is not valid,
     * or with status 500 (Internal Server Error) if the devisDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/devis")
    @Timed
    public ResponseEntity<DevisDTO> updateDevis(@Valid @RequestBody DevisDTO devisDTO) throws URISyntaxException {
        log.debug("REST request to update Devis : {}", devisDTO);
        if (devisDTO.getId() == null) {
            return createDevis(devisDTO);
        }
        DevisDTO result = devisService.update(devisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, devisDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /devis : get all the devis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of devis in body
     */
    @GetMapping("/devis")
    @Timed
    public ResponseEntity<List<DevisDTO>> getAllDevis(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Devis");
        try
        {
        Page<DevisDTO> page = devisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/devis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        
    }catch (Exception e) {
        loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    }

    /**
     * GET  /devis/:id : get the "id" devis.
     *
     * @param id the id of the devisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/devis/{id}")
    @Timed
    public ResponseEntity<DevisDTO> getDevis(@PathVariable Long id) {
        log.debug("REST request to get Devis : {}", id);
        DevisDTO devisDTO = devisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(devisDTO));
    }

    /**
     * PUT  /devis/:id : get the "id" devis.
     *
     * @param id the id of the devisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devisDTO, or with status 404 (Not Found)
     */
    @PutMapping("/devis/accepterDevis/{id}")
    @Timed
    public ResponseEntity<DevisDTO> accepterDevis(@PathVariable Long id) {
        log.debug("REST request to get Devis : {}", id);
        DevisDTO devisDTO = devisService.accepterDevis(id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCancelAlert(ENTITY_NAME, devisDTO.getId().toString()))
                .body(devisDTO);
    }

     /**
     * PUT  /devis/:id : get the "id" devis.
     *
     * @param id the id of the devisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devisDTO, or with status 404 (Not Found)
     */
    @PutMapping("/devis/validerDevis/{id}")
    @Timed
    public ResponseEntity<DevisDTO> validerDevis(@PathVariable Long id) {
        log.debug("REST request to get Devis : {}", id);
        DevisDTO devisDTO = devisService.validerDevis(id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCancelAlert(ENTITY_NAME, devisDTO.getId().toString()))
                .body(devisDTO);
    }

    @PutMapping("/validGesTech/{id}")
    @Timed
    public ResponseEntity<DevisDTO> validerGesDevis(@PathVariable Long id) {
        log.debug("REST request to get Devis : {}", id);
        DevisDTO devisDTO = devisService.validerGesDevis(id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCancelAlert(ENTITY_NAME, devisDTO.getId().toString()))
                .body(devisDTO);
    }


    /**
     * GET  /devis/:id : get the "id" devis.
     *
     * @param id the id of the devisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/devis/soumis/{id}")
    @Timed
    public ResponseEntity<DevisDTO> getDevisSoumis(@PathVariable Long id) {
        log.debug("REST request to get Devis : {}", id);
        DevisDTO devisDTO = devisService.findDevisSoumis(id);
        if (devisDTO != null) {
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(devisDTO));
        } else {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new DevisDTO()));
            }
    }
    
/**
     * GET  /devis/:id : get the "id" devis.
     *
     * @param id the id of the devisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/devis/valideElseSoumis/{id}")
    @Timed
    public ResponseEntity<DevisDTO> getDevisValideElseSoumis(@PathVariable Long id) {
        log.debug("REST request to get Devis : {}", id);
        DevisDTO devisDTO = devisService.findDevisValideElseSoumis(id);
        if (devisDTO != null) {
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(devisDTO));
        } else {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new DevisDTO()));
            }
    }
    /**
     * GET  /devis/:id : get the "id" devis.
     *
     * @param id the id of the devisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/devis/accepteOuValideOuRefuse/{id}")
    @Timed
    public ResponseEntity<DevisDTO> getDevisAccepteOuValideOuRefuse(@PathVariable Long id) {
        log.debug("REST request to get Devis : {}", id);
        DevisDTO devisDTO = devisService.findDevisAccepteOuValideOuRefuse(id);
        if (devisDTO != null) {
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(devisDTO));
        } else {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new DevisDTO()));
            }
    }

     /**
     * GET  /devis/:id : get the "id" devis.
     *
     * @param id the id of the devisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/devis/accepte/{id}")
    @Timed
    public ResponseEntity<DevisDTO> getDevisAccepte(@PathVariable Long id) {
        log.debug("REST request to get Devis : {}", id);
        DevisDTO devisDTO = devisService.findDevisAccepte(id);
        if (devisDTO != null) {
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(devisDTO));
        } else {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new DevisDTO()));
            }
    }

    /**
     * GET  /devis/:id : get the "id" devis.
     *
     * @param id the id of the devisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/devis/facture/{id}")
    @Timed
    public ResponseEntity<DevisDTO> getDevisFacture(@PathVariable Long id) {
        log.debug("REST request to get Devis : {}", id);
        DevisDTO devisDTO = devisService.findDevisFacture(id);
        if (devisDTO != null) {
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(devisDTO));
        } else {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new DevisDTO()));
            }
    }
    /**
     * GET  /devis/:id : get the "id" devis.
     *
     * @param id the id of the devisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/devis/bonSortieAccepte/{id}")
    @Timed
    public ResponseEntity<DevisDTO> getDevisGenereBonSortieByPrestation(@PathVariable Long id) {
        log.debug("REST request to get Devis : {}", id);
        DevisDTO devisDTO = devisService.getGenereBonSortieByPrestation(id);
        if (devisDTO != null) {
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(devisDTO));
        } else {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new DevisDTO()));
            }
    }

     /**
     * GET  /devis/:id : get the "id" devis.
     *
     * @param id the id of the devisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/devis/refusAferFacture/{id}")
    @Timed
    public ResponseEntity<DevisDTO> getDevisRefusAferFactureByPrestation(@PathVariable Long id) {
        log.debug("REST request to get Devis : {}", id);
        DevisDTO devisDTO = devisService.getDevisRefusAferFactureByPrestation(id);
        if (devisDTO != null) {
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(devisDTO));
        } else {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new DevisDTO()));
            }
    }

     /**
     * GET  /devis/:id : get the "id" devis.
     *
     * @param id the id of the devisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/devis/valide/{id}")
    @Timed
    public ResponseEntity<DevisDTO> getDevisValide(@PathVariable Long id) {
        log.debug("REST request to get Devis : {}", id);
        DevisDTO devisDTO = devisService.findDevisValide(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(devisDTO));
    }

     /**
     * GET  /devis/:id : get the "id" devis.
     *
     * @param id the id of the devisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/devis/sauvegarde/{id}")
    @Timed
    public ResponseEntity<DevisDTO> getLastDevisSauvegarde(@PathVariable Long id) {
        log.debug("REST request to get Devis : {}", id);
        DevisDTO devisDTO = devisService.findLastDevisSauvegarde(id);
         if (devisDTO != null) {
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(devisDTO));
        } else {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new DevisDTO()));
            }
    }
    
    /**
     * GET  /devis/:id : get the "id" devis.
     *
     * @param id the id of the devisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/devis/sauvegardeRefuseValidGes/{id}")
    @Timed
    public ResponseEntity<DevisDTO> getDevisSauvegardeRefuse(@PathVariable Long id) {
        log.debug("REST request to get Devis : {}", id);
        DevisDTO devisDTO = devisService.findLastDevisSauvegardeRefuseValidGes(id);
         if (devisDTO != null) {
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(devisDTO));
        } else {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new DevisDTO()));
            }
    }
    
    /**
     * GET  /devis/:id : get the "id" devis.
     *
     * @param id the id of the devisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/devis/valideGestTechnique/{id}")
    @Timed
    public ResponseEntity<DevisDTO> getDevisValidGestionnaire(@PathVariable Long id) {
        log.debug("REST request to get Devis Valid Gestionnaire: {}", id);
        DevisDTO devisDTO = devisService.findLastDevisValidGes(id);
         if (devisDTO != null) {
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(devisDTO));
        } else {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new DevisDTO()));
            }
    }

    /**
     * DELETE  /devis/:id : delete the "id" devis.
     *
     * @param id the id of the devisDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/devis/{id}")
    @Timed
    public ResponseEntity<Void> deleteDevis(@PathVariable Long id) {
        log.debug("REST request to delete Devis : {}", id);
        devisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/devis?query=:query : search for the devis corresponding
     * to the query.
     *
     * @param query the query of the devis search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/devis")
    @Timed
    public ResponseEntity<List<DevisDTO>> searchDevis(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Devis for query {}", query);
        Page<DevisDTO> page = devisService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/devis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of devis in body
     */

    @GetMapping("/devis/prestation/{prestationId}")
    @Timed
    public ResponseEntity<List<DevisDTO>> getDevisByPrestation(@PathVariable Long prestationId) {
        log.debug("REST request to get a page of All devis by prestation");
        List<DevisDTO> devis = devisService.findDevisByPrestation(prestationId);
        return new ResponseEntity<>(devis, HttpStatus.OK);
    }

    @GetMapping("/quotation/prestation/{prestationId}")
    @Timed
    public ResponseEntity<DevisDTO> getQuotationByPrestation(@PathVariable Long prestationId) {
        log.debug("REST request to get a quotation by prestation");
        DevisDTO devis = devisService.findQuotationByPrestation(prestationId);
        return new ResponseEntity<>(devis, HttpStatus.OK);
    }


     /**
     * GET  /devis/:prestationId : get the "prestationId" devis.
     *
     * @param id the id of the devisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/devis/lastVersion/{prestationId}")
    @Timed
    public ResponseEntity<DevisDTO> getLastVersionDevis(@PathVariable Long prestationId) {
        log.debug("REST request to get Devis : {}", prestationId);
        DevisDTO devisDTO = devisService.findLastDevisVersion(prestationId);
        if (devisDTO != null) {
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(devisDTO));
        } else {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new DevisDTO()));
            }
    }
   
    /**
     * GET  /devis/:prestationId : get the "prestationId" devis.
     *
     * @param id the id of the devisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devisDTO, or with status 404 (Not Found)
     */
    
    @GetMapping("/devis/lastEtatDevis/{prestationId}")
    @Timed
    public ResponseEntity<DevisDTO> getLastEtatDevis(@PathVariable Long prestationId) {
        log.debug("REST request to get Etat Devis : {}", prestationId);
        DevisDTO devisDTO = devisService.findEtatLastDevisByPrestation(prestationId);
        if (devisDTO != null) {
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(devisDTO));
        } else {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new DevisDTO()));
            }
    }

    /**
     * GET /service-rmqs/:id : get the "id" serviceRmq.
     *
     * @param id the id of the serviceRmqDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * serviceRmqDTO, or with status 404 (Not Found)
     */
    @PutMapping("/devis/refuser/{id}/{motifs}")
    @Timed
    public ResponseEntity<DevisDTO> refuserDevis(@PathVariable Long id, @PathVariable Long[] motifs) {
        log.debug("REST request to refuser : {}", id);
            DevisDTO devisDTO = devisService.refuserDevis(id);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCancelAlert(ENTITY_NAME, devisDTO.getId().toString()))
                .body(devisDTO);
    }

    /**
     * GET /service-rmqs/:id : get the "id" serviceRmq.
     *
     * @param id the id of the serviceRmqDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * serviceRmqDTO, or with status 404 (Not Found)
     */

    
   
    
}
