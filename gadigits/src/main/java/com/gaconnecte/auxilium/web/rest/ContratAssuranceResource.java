package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.AssureService;
import com.gaconnecte.auxilium.service.ContratAssuranceService;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.VehiculeAssureService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.AssureDTO;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDT;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDTO;
import com.gaconnecte.auxilium.service.dto.DatatablesRequest;
import com.gaconnecte.auxilium.service.dto.VehicleBrandDTO;
import com.gaconnecte.auxilium.service.dto.VehiculeAssureDTO;

import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.PageRequest;

/**
 * REST controller for managing ContratAssurance.
 */
@RestController
@RequestMapping("/api")
public class ContratAssuranceResource {

    private final Logger log = LoggerFactory.getLogger(ContratAssuranceResource.class);

    private static final String ENTITY_NAME = "contratAssurance";

    private final ContratAssuranceService contratAssuranceService;

    private final HistoryService historyService;
    @Autowired
    private AssureService assureService;
    @Autowired
    private LoggerService loggerService;
    @Autowired
    VehiculeAssureService vehiculeAssureService;

    public ContratAssuranceResource(ContratAssuranceService contratAssuranceService, HistoryService historyService) {
        this.contratAssuranceService = contratAssuranceService;
        this.historyService = historyService;
    }

    /**
     * POST  /contrat-assurances : Create a new contratAssurance.
     *
     * @param contratAssuranceDTO the contratAssuranceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contratAssuranceDTO, or with status 400 (Bad Request) if the contratAssurance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contrat-assurances")
    @Timed
    public ResponseEntity<ContratAssuranceDTO> createContratAssurance(@Valid @RequestBody ContratAssuranceDTO contratAssuranceDTO) throws URISyntaxException {
        log.debug("REST request to save ContratAssurance : {}", contratAssuranceDTO);
        
        try
        {
        ContratAssuranceDTO result = contratAssuranceService.save(contratAssuranceDTO);
        if(contratAssuranceDTO.getId() == null){
     	historyService.historysave("Contrat", result.getId(),null, result,0,1, "Création Contrat");

        
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
        }catch (Exception e) {
            log.error("Error in create contact : ", e);
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * PUT  /contrat-assurances : Updates an existing contratAssurance.
     *
     * @param contratAssuranceDTO the contratAssuranceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contratAssuranceDTO,
     * or with status 400 (Bad Request) if the contratAssuranceDTO is not valid,
     * or with status 500 (Internal Server Error) if the contratAssuranceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contrat-assurances")
    @Timed
    public ResponseEntity<ContratAssuranceDTO> updateContratAssurance(@Valid @RequestBody ContratAssuranceDTO contratAssuranceDTO) throws URISyntaxException {
        log.debug("REST request to update ContratAssurance : {}", contratAssuranceDTO);
        if (contratAssuranceDTO.getId() == null) {
            return createContratAssurance(contratAssuranceDTO);
        }
        
        
        ContratAssuranceDTO oldContratAssurance= contratAssuranceService.findOne(contratAssuranceDTO.getId()); 
        ContratAssuranceDTO result = contratAssuranceService.save(contratAssuranceDTO);
        historyService.historysave("Contrat",result.getId(),oldContratAssurance,result,0,0, "Modification Contrat");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * GET  /contrat-assurances : get all the contratAssurances.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contratAssurances in body
     */
    @GetMapping("/contrat-assurances")
    @Timed
    public ResponseEntity<List<ContratAssuranceDTO>> getAllContratAssurances() {
        log.debug("REST request to get a page of ContratAssurances");
        
        try
        {
        List<ContratAssuranceDTO> page = contratAssuranceService.findAll();
       
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contrat-assurances");
        //return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(page));
        
    }catch (Exception e) {
        loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    }
    /**
     * GET  /contrat-assurances : get all the contratAssurances.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contratAssurances in body
     */
    @PostMapping("/contrat-assurances/test")
    @Timed
    public ResponseEntity<ContratAssuranceDT> getAllContratAssurancesTest(@RequestBody DatatablesRequest datatablesRequest) {
        log.debug("REST request to get a page of ContratAssurances {}", datatablesRequest);
        ContratAssuranceDT dt = new ContratAssuranceDT();
        dt.setRecordsFiltered(50L);
        dt.setRecordsTotal(50L);

        try {
            final Integer page = new Double(Math.ceil(datatablesRequest.getStart() / datatablesRequest.getLength())).intValue();
            final PageRequest pr = new PageRequest(page, datatablesRequest.getLength());
            List<ContratAssuranceDTO> dtos = contratAssuranceService.findAll(pr).getContent();
            if(CollectionUtils.isNotEmpty(dtos)) {
                dt.setData(dtos);
                return ResponseEntity.ok().body(dt);
            } else {
                return ResponseEntity.ok().body(dt);
            }
        
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET  /contrat-assurances/:id : get the "id" contratAssurance.
     *
     * @param id the id of the contratAssuranceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contratAssuranceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contrat-assurances/{id}")
    @Timed
    public ResponseEntity<ContratAssuranceDTO> getContratAssurance(@PathVariable Long id) {
        log.debug("REST request to get ContratAssurance : {}", id);
        ContratAssuranceDTO contratAssuranceDTO = contratAssuranceService.findOneWithVehicles(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contratAssuranceDTO));
    }

    /**
     * GET  /contrat-assurances/:id : get the "id" vehicule.
     *
     * @param id the id of the contratAssuranceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contratAssuranceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contrat-assurances/vehicule/{id}")
    @Timed
    public ResponseEntity<List<ContratAssuranceDTO>> getContratAssuranceByVehicule(@ApiParam Pageable pageable, @PathVariable Long id) {
        log.debug("REST request to get ContratAssurance : {}", id);
        Page<ContratAssuranceDTO> contratAssuranceDTO = contratAssuranceService.findByVehicule(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(contratAssuranceDTO, "/api/contrat-assurances/vehicule");
        return new ResponseEntity<>(contratAssuranceDTO.getContent(), headers, HttpStatus.OK);
    }

    /**
     * DELETE  /contrat-assurances/:id : delete the "id" contratAssurance.
     *
     * @param id the id of the contratAssuranceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contrat-assurances/{id}")
    @Timed
    public ResponseEntity<Void> deleteContratAssurance(@PathVariable Long id) {
        log.debug("REST request to delete ContratAssurance : {}", id);
        Boolean delete = contratAssuranceService.delete(id);
         if (delete) {
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }   else {
           return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "delete", "Impossible de supprimer cette compagnie")).body(null);
       }
    }

    /**
     * SEARCH  /_search/contrat-assurances?query=:query : search for the contratAssurance corresponding
     * to the query.
     *
     * @param query the query of the contratAssurance search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/contrat-assurances")
    @Timed
    public ResponseEntity<List<ContratAssuranceDTO>> searchContratAssurances(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of ContratAssurances for query {}", query);
        Page<ContratAssuranceDTO> page = contratAssuranceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/contrat-assurances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

     @GetMapping("/contrat/{immatriculationVehicule}")
    @Timed
    public ResponseEntity<ContratAssuranceDTO> getVehiculeAssureByImmatriculation(@PathVariable String immatriculationVehicule) {
        log.debug("REST request to get ContratAssurance : {}", immatriculationVehicule);
        ContratAssuranceDTO contratAssuranceDTO = contratAssuranceService.findOneByImmatriculation(immatriculationVehicule);
        if (contratAssuranceDTO == null){return null;}
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contratAssuranceDTO));
    }

    @GetMapping("/contrat/tiers/{immatriculationVehicule}/{clientId}")
    @Timed
    public ResponseEntity<Boolean> getContractByImmatriculation(@PathVariable String immatriculationVehicule, @PathVariable Long clientId) {
        log.debug("REST request to get ContratAssurance : {}", immatriculationVehicule);
        Boolean existContrat = null;
        VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureService.findOneByClientIdAndImmatriculation(clientId, immatriculationVehicule);
        if(vehiculeAssureDTO != null) {
        	existContrat = true;
        }else {
        	existContrat = false;
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(existContrat));
    }

    @GetMapping("/contrat-assurances/byNumber/{pnumber}")
    @Timed
    public ResponseEntity<ContratAssuranceDTO> getPolicyByNumber(@PathVariable String pnumber) {
        log.debug("REST request to get ContratAssurance : {}", pnumber);
        ContratAssuranceDTO contratAssuranceDTO = contratAssuranceService.findPolicyByNumber(pnumber);
        if (contratAssuranceDTO == null) {
            contratAssuranceDTO = new ContratAssuranceDTO();
        }
        return ResponseEntity.ok().body(contratAssuranceDTO);
    }

    @PostMapping("/contrat-assurances/bypnumber")
    @Timed
    public ResponseEntity<ContratAssuranceDTO> getPolicyByNumber(@RequestBody ContratAssuranceDTO policy) {
        log.debug("REST request to get ContratAssurance : {}", policy);
        ContratAssuranceDTO contratAssuranceDTO = contratAssuranceService.findPolicyByNumber(policy.getNumeroContrat());
        if (contratAssuranceDTO == null) {
            contratAssuranceDTO = new ContratAssuranceDTO();
        }
        return ResponseEntity.ok().body(contratAssuranceDTO);
    }

    @GetMapping("/contractDetails/{immatriculationVehicule}")
    @Timed
    public ResponseEntity<ContratAssuranceDTO> findDetailsContract(@PathVariable String immatriculationVehicule) {
        log.debug("REST request to get ContratAssurance : {}", immatriculationVehicule);
        ContratAssuranceDTO contratAssuranceDTO = contratAssuranceService.findOneByImmatriculation(immatriculationVehicule);
        // TODO : assure opt
        if (contratAssuranceDTO == null){
        	ContratAssuranceDTO error = new ContratAssuranceDTO();
        	//error.setAssurePrenom("123456789");
    	return ResponseUtil.wrapOrNotFound(Optional.ofNullable(error));
    	} else {
        AssureDTO assr = new AssureDTO();
        assr = assureService.findOne(1L);
        contratAssuranceDTO.setNumMobile(assr.getPremTelephone());
        contratAssuranceDTO.setCIN(assr.getCin());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contratAssuranceDTO));}
    }
    
    @GetMapping("/contrat-client-immatriculation/{immatriculationVehicule}/{clientId}")
    @Timed
    public ResponseEntity<ContratAssuranceDTO> getVehiculeAssureByImmatriculationAndClientId(@PathVariable String immatriculationVehicule, @PathVariable Long clientId) {
        log.debug("REST request to get ContratAssurance : {}", immatriculationVehicule);
        ContratAssuranceDTO contratAssuranceDTO = new ContratAssuranceDTO();
        VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureService.findOneByClientIdAndImmatriculation(clientId, immatriculationVehicule);
        if(vehiculeAssureDTO != null) {
        	contratAssuranceDTO = contratAssuranceService.findOne(vehiculeAssureDTO.getContratId());
        	if (contratAssuranceDTO == null){return null;}
        }else {
        	return null;
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contratAssuranceDTO));
    }
}