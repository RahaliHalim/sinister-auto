package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.UserPartnerMode;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.UserPartnerModeService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.AgencyDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.UserPartnerModeDTO;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing UserExtra.
 */
@RestController
@RequestMapping("/api")
public class UserPartnerModeResource {

    private final Logger log = LoggerFactory.getLogger(UserPartnerModeResource.class);

    private static final String ENTITY_NAME = "userPartnerMode";

    private final UserPartnerModeService userPartnerModeService;
    
    private final LoggerService loggerService;
    

    public UserPartnerModeResource(UserPartnerModeService userPartnerModeService, LoggerService loggerService) {
        this.userPartnerModeService = userPartnerModeService;
        this.loggerService = loggerService;
        
    }

    @GetMapping("/user-partner-mode/agency/{iduser}")
    @Timed
    public Set<AgencyDTO> getAgencyByUser(@PathVariable Long iduser) {
        log.debug("REST request to get all Agency By User");
        return userPartnerModeService.findAgencyByUser(iduser);
    }
    
    @GetMapping("/user-partner-mode/partner-agency-mode/{idPartner}/{modeId}")
	@Timed
	public ResponseEntity<Set<UserPartnerModeDTO>> getAllUsersPartnersByPartner(@PathVariable Long idPartner, @PathVariable Long modeId) {
		try {
			Set<UserPartnerModeDTO> UsersPartnerModes = userPartnerModeService.findUserPartnerModeByPartnerAndAgencyAndMode(idPartner, modeId);
			return new ResponseEntity<>(UsersPartnerModes, HttpStatus.OK);
		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
    
    @GetMapping("/user-partner-mode/partner/{idPartner}")
	@Timed
	public ResponseEntity<Set<UserPartnerModeDTO>> getAllUsersModesPartnersByPartner(@PathVariable Long idPartner) {
		try {
			Set<UserPartnerModeDTO> UsersPartnerModes = userPartnerModeService.findUserPartnerModeByPartner(idPartner);
			return new ResponseEntity<>(UsersPartnerModes, HttpStatus.OK);
		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
    
    @GetMapping("/user-partner-mode/partner-mode/{idPartner}/{modeId}")
	@Timed
	public ResponseEntity<Set<UserPartnerModeDTO>> getAllUsersByPartnerAndMode(@PathVariable Long idPartner, @PathVariable Long modeId) {
		try {
			Set<UserPartnerModeDTO> UsersPartnerModes = userPartnerModeService.findAllUsersByPartnerAndMode(idPartner, modeId);
			return new ResponseEntity<>(UsersPartnerModes, HttpStatus.OK);
		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


}