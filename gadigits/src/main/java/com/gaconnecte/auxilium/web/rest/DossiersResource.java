package com.gaconnecte.auxilium.web.rest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.DossiersService;
import com.gaconnecte.auxilium.service.UserService;
import com.gaconnecte.auxilium.service.dto.AssitancesDTO;
import com.gaconnecte.auxilium.service.dto.DossiersDTO;
import com.gaconnecte.auxilium.service.dto.PriseEnChargeDTO;
import com.gaconnecte.auxilium.service.dto.RechercheDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.impl.DossiersServiceImpl;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class DossiersResource {
	
	
	@Autowired
    private UserService userService;
	
	private final DossiersService dossiersServices;
	private final Logger log = LoggerFactory.getLogger(DossiersResource.class);
	public DossiersResource(DossiersService dossiersServices) {
        this.dossiersServices = dossiersServices;
    }


    /**
     * GET  /ref-baremes/:id : get the "id" refBareme.
     *
     * @param id the id of the refBaremeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refBaremeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/getdossiers/{id}")
    @Timed
    public ResponseEntity<DossiersDTO> getDossiers(@PathVariable Long id) {
        //log.debug("REST request to get UploadDAO : {}", id);
    	DossiersDTO dossiersDTO = dossiersServices.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dossiersDTO));
    }
    
    /**
     * GET  /upload : get all the upload.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tiers in body
     */
    @GetMapping("/getdossiers")
    @Timed
    public ResponseEntity<List<DossiersDTO>> getAllUploadDTO() {
        log.debug("REST request to get a set of dossiersss");
        try {
            String login = SecurityUtils.getCurrentUserLogin();
            User user = userService.findOneUserByLogin(login);

            List<DossiersDTO> DossiersDTO = dossiersServices.findAll(user.getId());
            log.debug("REST request to get a set of dossiersss"+DossiersDTO.size());
            return new ResponseEntity<>(DossiersDTO, HttpStatus.OK);
        } catch (Exception e) {    
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    
    
    
    
    
   /** public ResponseEntity<List<DossiersDTO>> getAllUploadDTO(@ApiParam Pageable pageable) {
        //log.debug("REST request to get a page of DossiersDTO");
        Page<DossiersDTO> page = dossierServices.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/getdossiers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
     */
    /**
     * POST  /getdossiers : get dossiers by rechercheDTO.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dossiers in body
     */

    

    @PostMapping("/getdossiers")
    @Timed
    public ResponseEntity<List<DossiersDTO>> findDossiersServices(@RequestBody SearchDTO searchDTO) {
    	 try {
             String login = SecurityUtils.getCurrentUserLogin();
             User user = userService.findOneUserByLogin(login);

             List<DossiersDTO> dossiersDTO = dossiersServices.findAll(searchDTO, user.getId());
         return new ResponseEntity<>(dossiersDTO, HttpStatus.OK);

    	 }catch (Exception e) {    
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
    }
    

}
