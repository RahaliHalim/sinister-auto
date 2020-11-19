package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.AgencyService;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.UserExtraService;
import com.gaconnecte.auxilium.service.UserService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.AgencyDTO;
import com.gaconnecte.auxilium.service.dto.ExpertDTO;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.dto.UserPartnerModeDTO;


import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Agency.
 */
@RestController
@RequestMapping("/api")
public class AgencyResource {

    private final Logger log = LoggerFactory.getLogger(AgencyResource.class);

    private static final String ENTITY_NAME = "agency";
    private static final String Agence_Concessionnaire = "agence_concessionnaire";
    private static final String Agent_Assurance = "agent_assurance";

    private final AgencyService agencyService;
    private final UserExtraService userExtraService;
    private final UserService userService;

 
    @Autowired
    private HistoryService historyService;

    public AgencyResource(AgencyService agencyService, UserExtraService userExtraService, UserService userService) {
        this.agencyService = agencyService;
        this.userExtraService = userExtraService;
        this.userService = userService;
    }

    /**
     * POST  /agencies : Create a new agency.
     *
     * @param agencyDTO the agencyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agencyDTO, or with status 400 (Bad Request) if the agency has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agencies")
    @Timed
    public ResponseEntity<AgencyDTO> createAgency(@RequestBody AgencyDTO agencyDTO) throws URISyntaxException {
        log.debug("REST request to save Agency : {}", agencyDTO);
        if (agencyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new agency cannot already have an ID")).body(null);
        }
        AgencyDTO result = agencyService.save(agencyDTO);
        if(result!= null ) {
         	historyService.historysave("Agency", result.getId(),null, result,0,1, "Création");
            }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * PUT  /agencies : Updates an existing agency.
     *
     * @param agencyDTO the agencyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agencyDTO,
     * or with status 400 (Bad Request) if the agencyDTO is not valid,
     * or with status 500 (Internal Server Error) if the agencyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agencies")
    @Timed
    public ResponseEntity<AgencyDTO> updateAgency(@RequestBody AgencyDTO agencyDTO) throws URISyntaxException {
        log.debug("REST request to update Agency : {}", agencyDTO);
        if (agencyDTO.getId() == null) {
            return createAgency(agencyDTO);
        }
        AgencyDTO oldAgency = agencyService.findOne(agencyDTO.getId());   
        AgencyDTO result = agencyService.save(agencyDTO);
        historyService.historysave("Agency",result.getId(),oldAgency,result,0,0, "Modification");
        
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * GET  /agencies : get all the agencies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of agencies in body
     */
    @GetMapping("/agencies")
    @Timed
    public List<AgencyDTO> getAllAgencies() {
        log.debug("REST request to get all Agencies");
        return agencyService.findAll();
    }

    /**
     * GET  /agencies/company/{id} : get all the agencies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of agencies in body
     */
    @GetMapping("/agencies/partner/{id}")
    @Timed
    public List<AgencyDTO> getAllAgenciesByPartner(@PathVariable Long id) {
        log.debug("REST request to get all Agencies by partner");
	String login = SecurityUtils.getCurrentUserLogin();
	User user = userService.findOneUserByLogin(login);
	UserExtraDTO userExtraDTO = userExtraService.findOne(user.getId());
        List<AgencyDTO> dtos = new LinkedList<>();
        if(userExtraDTO != null && new Long(24l).equals(userExtraDTO.getProfileId())) { // agent
            AgencyDTO a = agencyService.findOne(userExtraDTO.getPersonId());
            dtos.add(a);            
            return dtos;
        }
        if(userExtraDTO != null && new Long(23l).equals(userExtraDTO.getProfileId())) { // courtier
            Set<UserPartnerModeDTO> partnerModes = userExtraDTO.getUserPartnerModes();
            for (UserPartnerModeDTO partnerMode : partnerModes) {
                AgencyDTO p = agencyService.findOne(partnerMode.getCourtierId());
                dtos.add(p);
            }
            return dtos;            
        }
        
        return agencyService.findAllAgenciesByPartner(id);
    }
    
    @GetMapping("/agencies/partner-without-user/{id}")
    @Timed
    public List<AgencyDTO> getAllAgenciesByPartnerWithoutFiltrageUser(@PathVariable Long id) {
        log.debug("REST request to get all Agencies by partner");
        return agencyService.findAllAgenciesByPartner(id);
    }
    
    /**
     * GET  /agencies/company/{id}/{type} : get all the agencies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of agencies in body
     */
    @GetMapping("/agencies/partner/{id}/{type}")
    @Timed
    public List<AgencyDTO> getAgencyByPartnerAndType(@PathVariable Long id, @PathVariable String type) {
        log.debug("REST request to get all Agencies by partner and type");
        return agencyService.findAllAgenciesByPartnerAndType(id, type);
    }
    @GetMapping("/agencies/partner/courtier/{partnersId}/{type}")
    @Timed
    public List<AgencyDTO> getCourtierByPartnerAndType(@PathVariable Long[] partnersId, @PathVariable String type) {
        log.debug("REST request to get all Courtiers by partner and type");
        return agencyService.findCourtierByPartnerAndType(partnersId, type);
    }
    
    /**
     * GET  Get all Agent Assurance.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of Agent Assurance in body
     */
    @GetMapping("/agencies/agents")
    @Timed
    public List<AgencyDTO> getAllAgentAssurance() {
        log.debug("Request to get all Agent Assurance");
        return agencyService.findAllAgentAssurance();
    } 
    
    /**
     * Get all Agencies Concess
     *
     * @return the ResponseEntity with status 200 (OK) and the list of Agent Assurance in body
     */
    @GetMapping("/agencies/concess")
    @Timed
    public List<AgencyDTO> getAllAgenceConcessionnares() {
        log.debug("Request to get all Agencies Concess");
        return agencyService.findAllAgenceConcessionnares();
    } 
    
    /**
     * GET  /agencies/:id : get the "id" agency.
     *
     * @param id the id of the agencyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agencyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/agencies/{id}")
    @Timed
    public ResponseEntity<AgencyDTO> getAgency(@PathVariable Long id) {
        log.debug("REST request to get Agency : {}", id);
        AgencyDTO agencyDTO = agencyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(agencyDTO));
    }
    
    
    @GetMapping("/agencies/{partnerId}/{zoneId}")
    @Timed
    public List<AgencyDTO> getAgencyByPartnerAndZone(@PathVariable Long partnerId, @PathVariable Long zoneId) {
        return agencyService.findAllAgenciesByPartnerAndZone(partnerId, zoneId);
    }
    
    @GetMapping("/agencieszone/{zoneId}")
    @Timed
    public List<AgencyDTO> getAgencyByZone(@PathVariable Long zoneId) {
        return agencyService.findAllAgenciesByZone( zoneId);
    }
    
    
    @GetMapping("/agencies/byNameCode/{partnerId}/{code}")
    @Timed
    public ResponseEntity<AgencyDTO> getAgencyByNameCode(@PathVariable Long partnerId, @PathVariable String code) {
        log.debug("REST request to get Agency : {}", partnerId);
        AgencyDTO agencyDTO = agencyService.getAgencyByNameCode(partnerId, code);
        if (agencyDTO == null) {
        	agencyDTO = new AgencyDTO();
        }
        return ResponseEntity.ok().body(agencyDTO);
    }
    
    
    @GetMapping("/agencies/concessionnairebyNameCompanyName/{pname}/{id}")
    @Timed
    public ResponseEntity<AgencyDTO> getAgenceConcessByNameConcessName(@PathVariable String pname, @PathVariable Long id) {
        log.debug("REST request to get ConcessAgency : {}", pname);
        AgencyDTO agencyDTO = agencyService.getAgenceConcessByNameConcessName(pname, id);
        if (agencyDTO == null) {
        	agencyDTO = new AgencyDTO();
        }
        return ResponseEntity.ok().body(agencyDTO);
    }

    /**
     * DELETE  /agencies/:id : delete the "id" agency.
     *
     * @param id the id of the agencyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agencies/{id}")
    @Timed
    public void deleteAgency(@PathVariable Long id) {
        log.debug("REST request to delete Agency : {}", id);
        agencyService.delete(id);
        
    }

    /**
     * SEARCH  /_search/agencies?query=:query : search for the agency corresponding
     * to the query.
     *
     * @param query the query of the agency search
     * @return the result of the search
     */
    @GetMapping("/_search/agencies")
    @Timed
    public List<AgencyDTO> searchAgencies(@RequestParam String query) {
        log.debug("REST request to search Agencies for query {}", query);
        return agencyService.search(query);
    }
    
   /* public void findDiff(AgencyDTO agencyDTO) {
        AgencyDTO agencyDTOA = agencyService.findOne(agencyDTO.getId());
        if(agencyDTOA.getPartnerCompanyName()!= agencyDTO.getPartnerCompanyName()) {
        	historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Compagnie d'assurance de "+agencyDTOA.getPartnerCompanyName()+"à "+agencyDTO.getPartnerCompanyName()  );
        }
        if(agencyDTOA.getNom()!= agencyDTO.getNom()) {
        	historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Nom de "+agencyDTOA.getNom()+"à "+agencyDTO.getNom()  );
        }
        if(agencyDTOA.getPrenom()!= agencyDTO.getPrenom()) {
        	historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Prenom de "+agencyDTOA.getPrenom()+"à "+agencyDTO.getPrenom()  );
        }
        if(agencyDTOA.getName()!= agencyDTO.getName()) {
        	historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Nom agence de "+agencyDTOA.getName()+"à "+agencyDTO.getName()  );
        }
        if(agencyDTOA.getPhone()!= agencyDTO.getPhone()) {
        	historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Téléphone de "+agencyDTOA.getPhone()+"à "+agencyDTO.getPhone()  );
        }
        if(agencyDTOA.getMobile()!= agencyDTO.getMobile()) {
        	historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Mobile de "+agencyDTOA.getMobile()+"à "+agencyDTO.getMobile()  );
        }
        if(agencyDTOA.getEmail()!= agencyDTO.getEmail()) {
        	historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Premier Email de "+agencyDTOA.getEmail()+"à "+agencyDTO.getEmail()  );
        }
        if(agencyDTOA.getDeuxiemeMail()!= agencyDTO.getDeuxiemeMail()) {
        	historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Deuxième Email de "+agencyDTOA.getDeuxiemeMail()+"à "+agencyDTO.getDeuxiemeMail()  );
        }
        if(agencyDTOA.getGovernorateLabel()!= agencyDTO.getGovernorateLabel()) {
        	historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Gouvernorat de "+agencyDTOA.getGovernorateLabel()+"à "+agencyDTO.getGovernorateLabel()  );
        }
        if(agencyDTOA.getDelegationLabel()!= agencyDTO.getDelegationLabel()) {
        	historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Ville de "+agencyDTOA.getDelegationLabel()+"à "+agencyDTO.getDelegationLabel()  );
        }
        if(agencyDTOA.getTypeAgence()!= agencyDTO.getTypeAgence()) {
        	historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Type Agence de "+agencyDTOA.getTypeAgence()+"à "+agencyDTO.getTypeAgence()  );
        }
        
       
    }*/

}
