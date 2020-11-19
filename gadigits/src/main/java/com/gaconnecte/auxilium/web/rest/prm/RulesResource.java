package com.gaconnecte.auxilium.web.rest.prm;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.prm.RulesService;
import com.gaconnecte.auxilium.service.prm.dto.RulesDTO;

@RestController
@RequestMapping("/api")
public class RulesResource {

    private final Logger log = LoggerFactory.getLogger(RulesResource.class);

    private static final String ENTITY_NAME = "rules";

    private final RulesService rulesService;

    private LoggerService loggerService;


    public RulesResource(RulesService rulesService, LoggerService loggerService) {
        this.rulesService = rulesService;
        this.loggerService = loggerService;
    }

    @GetMapping("/rules")
    @Timed
    public ResponseEntity<Set<RulesDTO>> getAllRules() {
        log.debug("REST request to get all of Rules");
        try {
            Set<RulesDTO> rules = rulesService.findAll();
            return new ResponseEntity<>(rules, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/rules/{code}")
    @Timed
    public ResponseEntity<Set<RulesDTO>> getRulesByCode(@PathVariable String code) {
        log.debug("REST request to get Rules By Code");
        try {
            Set<RulesDTO> rules = rulesService.findByCode(code);
            return new ResponseEntity<>(rules, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}