package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.AssureService;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.JournalService;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.PersonneMoraleService;
import com.gaconnecte.auxilium.service.RefRemorqueurService;
import com.gaconnecte.auxilium.service.VatRateService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;

import com.gaconnecte.auxilium.service.dto.HistoryDTO;
import com.gaconnecte.auxilium.service.dto.PersonneMoraleDTO;
import com.gaconnecte.auxilium.service.dto.RefRemorqueurDTO;
import com.gaconnecte.auxilium.service.dto.VatRateDTO;

import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;

import org.json.JSONObject;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RefRemorqueur.
 */
@RestController
@RequestMapping("/api")
public class RefRemorqueurResource {

    private final Logger log = LoggerFactory.getLogger(RefRemorqueurResource.class);

    private static final String ENTITY_NAME = "refRemorqueur";

    private final RefRemorqueurService refRemorqueurService;
    @Autowired
    PersonneMoraleService personneMoraleService;
    @Autowired
    private AssureService assureService;
    @Autowired
    private JournalService journalService;
    @Autowired
    private LoggerService loggerService;
   
    private final HistoryService historyService;
    
    private final VatRateService vatRateService;
    
    public RefRemorqueurResource(RefRemorqueurService refRemorqueurService,HistoryService historyService, VatRateService vatRateService) {
        this.refRemorqueurService = refRemorqueurService;
        this.historyService = historyService;
        this.vatRateService = vatRateService;
    }

    /**
     * POST  /ref-remorqueurs : Create a new refRemorqueur.
     *
     * @param refRemorqueurDTO the refRemorqueurDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refRemorqueurDTO, or with status 400 (Bad Request) if the refRemorqueur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-remorqueurs")
    @Timed
    public ResponseEntity<RefRemorqueurDTO> createRefRemorqueur(@Valid @RequestBody RefRemorqueurDTO refRemorqueurDTO) throws URISyntaxException {
        log.debug("REST request to save RefRemorqueur : {}", refRemorqueurDTO);
        if (refRemorqueurDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refRemorqueur cannot already have an ID")).body(null);
        }
        refRemorqueurDTO.setIsDelete(Boolean.FALSE);
        RefRemorqueurDTO result = refRemorqueurService.save(refRemorqueurDTO);
        //historyService.historysave("Ref Remoqueur",  result.getId(), "CREATION");
        return ResponseEntity.created(new URI("/api/ref-remorqueurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-remorqueurs : Updates an existing refRemorqueur.
     *
     * @param refRemorqueurDTO the refRemorqueurDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refRemorqueurDTO,
     * or with status 400 (Bad Request) if the refRemorqueurDTO is not valid,
     * or with status 500 (Internal Server Error) if the refRemorqueurDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-remorqueurs")
    @Timed
    public ResponseEntity<RefRemorqueurDTO> updateRefRemorqueur(@Valid @RequestBody RefRemorqueurDTO refRemorqueurDTO) throws URISyntaxException {
        log.debug("REST request to update RefRemorqueur : {}", refRemorqueurDTO);
        if (refRemorqueurDTO.getId() == null) {
            return createRefRemorqueur(refRemorqueurDTO);
        }
        RefRemorqueurDTO result = refRemorqueurService.save(refRemorqueurDTO);
       // historyService.historysave("Ref Remoqueur",  result.getId(), "UPDATE");
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refRemorqueurDTO.getId().toString()))
            .body(result);
    }
    /**
     * GET /service-rmqs/:id : get the "id" serviceRmq.
     *
     * @param id the id of the serviceRmqDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * serviceRmqDTO, or with status 404 (Not Found)
     */
   
    @PutMapping("/ref-remorqueurs/bloquemotif/{id}/{motifs}") 
    @Timed
    public ResponseEntity<RefRemorqueurDTO> createBloqueurMotifRmq(@PathVariable Long id, @PathVariable Long[] motifs) {
        log.debug("REST request to bloquerrr motiff remorqueurr : {}", id);
       RefRemorqueurDTO result = null;
        try {  
        	RefRemorqueurDTO refRemorqueurDTO = refRemorqueurService.findOne(id);
            journalService.journalisationRefRemorqueurMotif("Bloquer Ref Remorqueur", SecurityUtils.getCurrentUserLogin(), 202L, refRemorqueurDTO , motifs);
            return ResponseEntity.ok()
            
                .body(result);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN).body(result);
        }
    } 
    

    
    /**
     * GET  /ref-remorqueurs : get all the refRemorqueurs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refRemorqueurs in body
     */
    @GetMapping("/ref-remorqueurs")
    @Timed
    public List<RefRemorqueurDTO> getAllRefRemorqueurs() {
        log.debug("REST request to get a page of RefRemorqueurs");
    
        return  refRemorqueurService.findAll();
    }
    
   


    /**
     * GET  /ref-remorqueurs : get all the refRemorqueurs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refRemorqueurs in body
     */
    @GetMapping("/ref-remorqueurs/cloture")
    @Timed
    public ResponseEntity<List<RefRemorqueurDTO>> getAllRefRemorqueurscloture(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefRemorqueurs");
        Page<RefRemorqueurDTO> page = refRemorqueurService.findAllRemorqueurCloture(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-remorqueurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET  /ref-remorqueurs : get all the refRemorqueurs for Batch bordereau.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refRemorqueurs in body
     */
    
    @GetMapping("/ref-remorqueurs/nonbloque")
    @Timed
    public ResponseEntity<List<RefRemorqueurDTO>> getAllRefRemorqueursNonBloque(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefRemorqueurs");
        Page<RefRemorqueurDTO> page = refRemorqueurService.findAllRemorqueur(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-remorqueurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    @GetMapping("/ref-remorqueursNnBloques")
    @Timed
    public ResponseEntity<List<RefRemorqueurDTO>> getRefRemorqueursNonBloques() {
        log.debug("REST request to get a page of RefRemorqueurs");
        List<RefRemorqueurDTO> listes = refRemorqueurService.findTousRemorqueurNonBloque();
        return new ResponseEntity<>(listes, HttpStatus.OK);
    }
    
    @GetMapping("/remorqueurs/ref-remorqueurs/{id}")
    @Timed
    public ResponseEntity<List<RefRemorqueurDTO>> getAllRefRemorqueursWithOrder(@PathVariable Long id ,@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefRemorqueurs");
        Page<RefRemorqueurDTO> page = refRemorqueurService.findAllRemorqueurWithOrder(id,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-remorqueurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ref-remorqueurs/:id : get the "id" refRemorqueur.
     *
     * @param id the id of the refRemorqueurDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refRemorqueurDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-remorqueurs/{id}")
    @Timed
    public ResponseEntity<RefRemorqueurDTO> getRefRemorqueur(@PathVariable Long id) {
        log.debug("REST request to get RefRemorqueur : {}", id);
        RefRemorqueurDTO refRemorqueurDTO = refRemorqueurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refRemorqueurDTO));
    }

    /**
     * DELETE  /ref-remorqueurs/:id : delete the "id" refRemorqueur.
     *
     * @param id the id of the refRemorqueurDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-remorqueurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefRemorqueur(@PathVariable Long id) {
        log.debug("REST request to delete RefRemorqueur : {}", id);
        Boolean delete = refRemorqueurService.delete(id);
        if (delete) {
        	return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
		}else {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "deleteRmq", "Impossible de supprimer ce remorqueur")).body(null);
		}

    }

    /**
     * SEARCH  /_search/ref-remorqueurs?query=:query : search for the refRemorqueur corresponding
     * to the query.
     *
     * @param query the query of the refRemorqueur search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-remorqueurs")
    @Timed
    public ResponseEntity<List<RefRemorqueurDTO>> searchRefRemorqueurs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefRemorqueurs for query {}", query);
        Page<RefRemorqueurDTO> page = refRemorqueurService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-remorqueurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * Bloque  /ref-remorqueurs/:id : Bloque the "id" refRemorqueur.
     *
     * @param id the id of the refRemorqueurDTO to Bloque
     * @return the ResponseEntity with status 200 (OK)
     */
    @PutMapping("/ref-remorqueurs/bloque/{id}")
    @Timed
    public ResponseEntity<Void> bloqueRefRemorqueur(@PathVariable Long id) {
        log.debug("REST request to delete refRemorqueur : {}", id);
        refRemorqueurService.bloque(id);
        RefRemorqueurDTO refRemorqueurDTO = refRemorqueurService.findOne(id);
        //historyService.historysave("Ref Remoqueur", id , "BLOQUE");
        return ResponseEntity.ok().headers(HeaderUtil.createEntityBloqueAlert(ENTITY_NAME, id.toString(), refRemorqueurDTO.getIsBloque())).build();
    }
    
    @GetMapping("/ref-remorqueurs/3RemorqueursActif/{latitudeAssure}/{longitudeAssure}/{immatriculation}")
    @Timed
    public ResponseEntity<List<RefRemorqueurDTO>> rechercheremorqueursproches(@PathVariable Double latitudeAssure, @PathVariable Double longitudeAssure ,@PathVariable String immatriculation  ) {
    	
        List<RefRemorqueurDTO> listes = refRemorqueurService.RechercheRemorqueurs(latitudeAssure, longitudeAssure);
        for (RefRemorqueurDTO refRemorqueurDTO :listes ) {
        	PersonneMoraleDTO personneMorale = personneMoraleService.findOne(refRemorqueurDTO.getSocieteId());
        	refRemorqueurDTO.setRegistreCommerce(personneMorale.getRegistreCommerce());
        	refRemorqueurDTO.setNumEtablissement(personneMorale.getNumEtablissement());
        	refRemorqueurDTO.setVilleId(personneMorale.getVilleId());
        	refRemorqueurDTO.setBanque(personneMorale.getBanque());
        	refRemorqueurDTO.setAgence(personneMorale.getAgence());
        	refRemorqueurDTO.setRib(personneMorale.getRib());
        	refRemorqueurDTO.setIsAssujettieTva(personneMorale.isIsAssujettieTva());
        	   VatRateDTO tvaDTO = vatRateService.findOne(personneMorale.getTvaId());
        	refRemorqueurDTO.setCodeTVA(tvaDTO.getVatRate() != null ? tvaDTO.getVatRate().doubleValue() : null);
        }
        return new ResponseEntity<>(listes, HttpStatus.OK);
    }
    @GetMapping("/refRemorqueur/{mail}/{mdp}/{deviceId}")
    @Timed
    public ResponseEntity<RefRemorqueurDTO> connexion(@PathVariable("mail") String mail,@PathVariable("mdp") String mdp,@PathVariable("deviceId") String deviceId) {
        log.debug("REST request to get RefRemorqueurDTO By mail and MDP : {},{}", mail.toString(), mdp.toString());
        RefRemorqueurDTO refRemorqueurDTO = refRemorqueurService.findRefRemorqueurByMailAndMdp(mail, mdp);
        if(refRemorqueurDTO!=null) {
        	RefRemorqueurDTO refRemorqueurDTODevice = refRemorqueurService.findRefRemorqueurByDeviceId(deviceId);
        	if(refRemorqueurDTODevice == null) {
        		refRemorqueurDTO.setIsConnected(true);
        		refRemorqueurService.save(refRemorqueurDTO);
        		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refRemorqueurDTO));
        	}else {
        		if(refRemorqueurDTO.getId().equals(refRemorqueurDTODevice.getId())) {
        			refRemorqueurDTO.setIsConnected(true);
            		refRemorqueurService.save(refRemorqueurDTO);
        			return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refRemorqueurDTO));
            	}else {
                    RefRemorqueurDTO errorDevice = new RefRemorqueurDTO();
                	
        			errorDevice.setSocieteRaisonSociale("deviceId");
        	      	return ResponseUtil.wrapOrNotFound(Optional.ofNullable(errorDevice));
            	}
        	}
        	
        }else {
        	RefRemorqueurDTO error = new RefRemorqueurDTO();
        	
      	  error.setSocieteRaisonSociale("123456789");
      	return ResponseUtil.wrapOrNotFound(Optional.ofNullable(error));
        }
        }
    
    @PostMapping("/refRemorqueurInscription")
    @Timed
    public ResponseEntity<RefRemorqueurDTO> inscription(@RequestParam(name="inscription") String inscription) throws URISyntaxException {
 
    	try {
    		JSONObject inscrip = new JSONObject(String.valueOf(inscription));
    		String mail = (String) inscrip.get("mail");
    		String mdp = (String) inscrip.get("password");
    		String prenom = (String) inscrip.get("prenom");
    		String nom = (String) inscrip.get("nom");
    		String adresse = (String) inscrip.get("adresse");
    		
    		RefRemorqueurDTO remq = refRemorqueurService.findRefRemorqueurByMail(mail);
    		

            if (remq != null) {
            	if(remq.getMdp()==null) {
            	remq.setAdresse(adresse);
            	remq.setSocieteRaisonSociale(prenom+ " " + nom);
            	remq.setMdp(mdp);
            	RefRemorqueurDTO result = refRemorqueurService.save(remq);
            	//HistoryDTO historyDTO = new HistoryDTO();
            	//historyDTO.setEntityName("GAGEO Remorqueur");
            	//historyDTO.setOperationDate(ZonedDateTime.now());
            	//historyDTO.setOperationName("Inscription Remorqueur GAGEO"+ " : "+ remq.getSocieteRaisonSociale());
            	//Long userId=(long) 46565;
            	//historyDTO.setUserId(userId);
            	//historyService.save(historyDTO);
                return ResponseEntity.created(new URI("/api/ref-remorqueurs/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                        .body(result);
            }else {
            	//Test if compte existe
            	RefRemorqueurDTO error = new RefRemorqueurDTO();
            	
          	  error.setSocieteRaisonSociale("1234567");
          	return ResponseUtil.wrapOrNotFound(Optional.ofNullable(error));
            }
            	
            }else {
            	RefRemorqueurDTO error = new RefRemorqueurDTO();
            	
            	  error.setSocieteRaisonSociale("123456789");
            	return ResponseUtil.wrapOrNotFound(Optional.ofNullable(error));
        }
    	}catch(Exception e) {
    		e.printStackTrace();
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}   
    }
    @PostMapping("/refRemorqueurbase")
    @Timed
    public ResponseEntity<RefRemorqueurDTO> getRefRemorqueur1(@RequestParam(name="location") String location) throws URISyntaxException {
        
    	try {
    		JSONObject loc = new JSONObject(String.valueOf(location));
    		String mail = (String) loc.get("mail");
    		Double longitude = (Double) loc.getDouble("longitude1");
    		Double latitude = (Double) loc.getDouble("latitude1");
    		
    		RefRemorqueurDTO refRemorqueurDTO = refRemorqueurService.findRefRemorqueurByMail(mail);
            System.out.println("latitude"+latitude);
            System.out.println("longitude"+longitude);
           refRemorqueurDTO.setLatitude(latitude);
           refRemorqueurDTO.setLongitude(longitude);
           refRemorqueurService.save(refRemorqueurDTO);
           
           return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refRemorqueurDTO));
    		
    		}catch(Exception e) {
    			e.printStackTrace();
    			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    		}
    	
    }
    
    @PostMapping("/deviceId")
    @Timed
    public ResponseEntity<RefRemorqueurDTO> saveDeviceId(@RequestParam(name="location") String location) throws URISyntaxException {
        
    	try {
    		JSONObject loc = new JSONObject(String.valueOf(location));
    		String mail = (String) loc.get("mail");
    		String deviceId = (String) loc.get("deviceId");
    		
    		RefRemorqueurDTO refRemorqueurDTO = refRemorqueurService.findRefRemorqueurByMail(mail);
            
           refRemorqueurDTO.setDeviceId(deviceId);
           refRemorqueurService.save(refRemorqueurDTO);
           
           return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refRemorqueurDTO));
    		
    		}catch(Exception e) {
    			e.printStackTrace();
    			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    		}
    	
    }
    
    @GetMapping("/remorqueur/deviceId/{deviceId}")
    @Timed
    public ResponseEntity<RefRemorqueurDTO> getRefRemorqueurByDeviceId(@PathVariable String deviceId) {
        log.debug("REST request to get RefRemorqueur By deviceId : {}", deviceId);
        RefRemorqueurDTO refRemorqueurDTO = refRemorqueurService.findRefRemorqueurByDeviceId(deviceId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refRemorqueurDTO));
    }
    
    @PostMapping("/GAGEO/isConnected")
    @Timed
    public ResponseEntity<RefRemorqueurDTO> isRemorqueurConnected(@RequestParam(name="location") String location) throws URISyntaxException {
        
    	try {
    		JSONObject loc = new JSONObject(String.valueOf(location));
    		String mail = (String) loc.get("mail");
    		Boolean isConnected = (Boolean) loc.getBoolean("isConnected");
    		
    		RefRemorqueurDTO refRemorqueurDTO = refRemorqueurService.findRefRemorqueurByMail(mail);
           refRemorqueurDTO.setIsConnected(isConnected);
           refRemorqueurDTO.setDeviceId(null);
           refRemorqueurService.save(refRemorqueurDTO);
           
           return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refRemorqueurDTO));
    		
    		}catch(Exception e) {
    			e.printStackTrace();
    			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    		}
    	
    }
    
    @PostMapping("/ref-remorqueurs/byName")
    @Timed
    public ResponseEntity<RefRemorqueurDTO> getTugCompanyByName(@Valid @RequestBody PersonneMoraleDTO personneMoraleDTO) {
        log.debug("REST request to get refRemorqueur : {}", personneMoraleDTO);
        RefRemorqueurDTO refRemorqueurDT = refRemorqueurService.getTugCompanyByName(personneMoraleDTO.getRaisonSociale(),personneMoraleDTO.getMatriculeFiscale(),personneMoraleDTO.getRegistreCommerce());
        if (refRemorqueurDT == null) {
            refRemorqueurDT = new RefRemorqueurDTO();
        }
        return ResponseEntity.ok().body(refRemorqueurDT);
    }
    

}