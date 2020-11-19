package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.UserExtraService;
import com.gaconnecte.auxilium.service.PartnerService;
import com.gaconnecte.auxilium.service.RefModeGestionService;
import com.gaconnecte.auxilium.service.UserPartnerModeService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.dto.UserPartnerModeDTO;
import com.gaconnecte.auxilium.service.dto.PartnerModeMappingDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;

/**
 * REST controller for managing UserExtra.
 */
@RestController
@RequestMapping("/api")
public class UserExtraResource {

    private final Logger log = LoggerFactory.getLogger(UserExtraResource.class);

    private static final String ENTITY_NAME = "userExtra";

    private final UserExtraService userExtraService;

     private final PartnerService partnerService;

    private final RefModeGestionService refModeGestionService;

    private final UserPartnerModeService userPartnerModeService;

    public UserExtraResource(UserExtraService userExtraService, PartnerService partnerService, RefModeGestionService refModeGestionService, UserPartnerModeService userPartnerModeService) {
        this.userExtraService = userExtraService;
        this.partnerService = partnerService;
        this.refModeGestionService = refModeGestionService;
        this.userPartnerModeService = userPartnerModeService;
    }

    /**
     * POST  /user-extras : Create a new userExtra.
     *
     * @param userExtraDTO the userExtraDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userExtraDTO, or with status 400 (Bad Request) if the userExtra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-extras")
    @Timed
    public ResponseEntity<UserExtraDTO> createUserExtra(@RequestBody UserExtraDTO userExtraDTO) throws URISyntaxException {
        log.debug("REST request to save UserExtra : {}", userExtraDTO);
        if (userExtraDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userExtra cannot already have an ID")).body(null);
        }
        UserExtraDTO result = userExtraService.save(userExtraDTO);
        return ResponseEntity.created(new URI("/api/user-extras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-extras : Updates an existing userExtra.
     *
     * @param userExtraDTO the userExtraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userExtraDTO,
     * or with status 400 (Bad Request) if the userExtraDTO is not valid,
     * or with status 500 (Internal Server Error) if the userExtraDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-extras")
    @Timed
    public ResponseEntity<UserExtraDTO> updateUserExtra(@RequestBody UserExtraDTO userExtraDTO) throws URISyntaxException {
        log.debug("REST request to update UserExtra : {}", userExtraDTO);
        if (userExtraDTO.getId() == null) {
            return createUserExtra(userExtraDTO);
        }
        UserExtraDTO result = userExtraService.save(userExtraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userExtraDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-extras : get all the userExtras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userExtras in body
     */
    @GetMapping("/user-extras")
    @Timed
    public List<UserExtraDTO> getAllUserExtras() {
        log.debug("REST request to get all UserExtras");
        return userExtraService.findAll();
    }
        
    @GetMapping("/user-extras/profil/{idprofil}")
    @Timed
    public Set<UserExtraDTO> getAllUserExtrasByProfil(@PathVariable Long idprofil) {
        log.debug("REST request to get all UserExtras By Profil");
        if(idprofil.equals(5L)) {
            return userExtraService.findAllResponsibles();
        } else {
            return userExtraService.findByProfil(idprofil);
        }
    }

    @GetMapping("/user-extras/mode/{iduser}/{partnersId}")
    @Timed
    public Set<PartnerModeMappingDTO> getAllModeByUser(@PathVariable Long iduser, @PathVariable Long[] partnersId) {
        log.debug("REST request to get all partner mode By User");
        Set<PartnerModeMappingDTO> usersMode = new HashSet<>();
        Set<UserPartnerModeDTO> usersPartnerMode = userExtraService.findPartnerModeByUser(iduser);
        
          usersPartnerMode.forEach(item->{
            for(Long partnerId: partnersId){
                if(item.getPartnerId() == partnerId){
                    PartnerModeMappingDTO partnerModeMappingDTO = new PartnerModeMappingDTO();
                    partnerModeMappingDTO.setPartnerId(item.getPartnerId());
                    partnerModeMappingDTO.setModeId(item.getModeId());
                    partnerModeMappingDTO.setLabelPartnerMode(refModeGestionService.findOne(item.getModeId()).getLibelle()+" ( "+partnerService.findOne(item.getPartnerId()).getCompanyName() +" )");
                    partnerModeMappingDTO.setPartnerMode(item.getPartnerId().toString().concat((item.getModeId()).toString()));
                    usersMode.add(partnerModeMappingDTO);
                    break;
                }
            }
		   });

        return usersMode;
    }

     @GetMapping("/user-extras/partner/{iduser}")
    @Timed
    public Set<PartnerDTO> getAllPartnerByUser(@PathVariable Long iduser) {
        log.debug("REST request to get all partner By User");
        Set<PartnerDTO> usersPartner = userPartnerModeService.findPartnerByUser(iduser);
        return usersPartner;
    }
    

    /**
     * GET  /user-extras/:id : get the "id" userExtra.
     *
     * @param id the id of the userExtraDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userExtraDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-extras/{id}")
    @Timed
    public ResponseEntity<UserExtraDTO> getUserExtra(@PathVariable Long id) {
        log.debug("REST request to get UserExtra : {}", id);
        UserExtraDTO userExtraDTO = userExtraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userExtraDTO));
    }
    
    
    /**
     * GET  /user-extras/:id : get the "id" userExtra bu uuser .
     *
     * @param id the id of the userExtraDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userExtraDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-extras/user/{id}")
    @Timed
    public ResponseEntity<UserExtraDTO> getUserExtraByUser(@PathVariable Long id) {
        log.debug("REST request to get UserExtra getUserExtraByUser : {}", id);
        UserExtraDTO userExtraDTO = userExtraService.findOneByUser(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userExtraDTO));
    }
   
    

    /* made to get users where idProfil in {7,9,11} */
    
    @GetMapping("/user-extras/profil")
    @Timed
    public Set<UserExtraDTO> getAllUserExtrasByProfils() {
        log.debug("REST request to get all UserExtras By Profil");
        return userExtraService.findByProfils();
    }
    
    @GetMapping("/user-extras/user/personne/{id}")
    @Timed
    public ResponseEntity<UserExtraDTO> getPersonneIdByUser(@PathVariable Long id) {
        log.debug("REST request to get UserExtra getUserExtraByUser : {}", id);
        UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userExtraDTO));
    }

    @GetMapping("/user-extras/person/profil/{personId}/{profilId}")
    @Timed
    public ResponseEntity<UserExtraDTO> findByPersonProfil(@PathVariable Long personId, @PathVariable Long profilId) {
        log.debug("REST request to get UserExtra findByPersonProfil : {}", personId);
        UserExtraDTO userExtraDTO = userExtraService.findByPersonProfil(personId, profilId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userExtraDTO));
    }
    
    @GetMapping("/user-extras/pers/profil/{personId}/{profilId}")
    @Timed
    public ResponseEntity<Set<UserExtraDTO>> findUsersByPersonProfil(@PathVariable Long personId, @PathVariable Long profilId) {
        log.debug("REST request to get UserExtra findByPersonProfil : {}", personId);
        try {
        	Set<UserExtraDTO> usersExtraDTO = userExtraService.findUserByPersonProfil(personId, profilId);
            return new ResponseEntity<>(usersExtraDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }
    
    
    

    /**
     * DELETE  /user-extras/:id : delete the "id" userExtra.
     *
     * @param id the id of the userExtraDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-extras/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserExtra(@PathVariable Long id) {
        log.debug("REST request to delete UserExtra : {}", id);
        userExtraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-extras?query=:query : search for the userExtra corresponding
     * to the query.
     *
     * @param query the query of the userExtra search
     * @return the result of the search
     */
    @GetMapping("/_search/user-extras")
    @Timed
    public List<UserExtraDTO> searchUserExtras(@RequestParam String query) {
        log.debug("REST request to search UserExtras for query {}", query);
        return userExtraService.search(query);
    }

}
