package com.gaconnecte.auxilium.web.rest.prm;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;
import java.util.Set;
import java.util.Optional;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.codahale.metrics.annotation.Timed;
import org.springframework.http.HttpStatus;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.prm.PartnerRulesService;
import com.gaconnecte.auxilium.service.prm.HistoryPartnerRulesService;
import com.gaconnecte.auxilium.service.prm.dto.PartnerRulesDTO;
import com.gaconnecte.auxilium.service.prm.dto.HistoryPartnerRulesDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.domain.HistoryPartnerRules;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class PartnerRulesResource {

    private final Logger log = LoggerFactory.getLogger(PartnerRulesResource.class);

    private static final String ENTITY_NAME = "partnerrules";

    private final PartnerRulesService partnerRulesService;

    private final HistoryPartnerRulesService historyPartnerRulesService;

    private LoggerService loggerService;


    public PartnerRulesResource(PartnerRulesService partnerRulesService, HistoryPartnerRulesService historyPartnerRulesService, LoggerService loggerService) {
        this.partnerRulesService = partnerRulesService;
        this.historyPartnerRulesService = historyPartnerRulesService;
        this.loggerService = loggerService;
    }

    @PostMapping("/partner-rules")
    @Timed
    public ResponseEntity<PartnerRulesDTO> createPartnerRules(@Valid @RequestBody PartnerRulesDTO partnerRulesDTO) throws URISyntaxException {
        log.debug("REST request to save Partner Rules : {}", partnerRulesDTO);
        
        PartnerRulesDTO result = partnerRulesService.save(partnerRulesDTO);
        return ResponseEntity.created(new URI("/api/partner-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    @PostMapping("/partner-rules/history")
    @Timed

    public void createHistoryPartnerRules(@Valid @RequestBody HistoryPartnerRulesDTO historypartnerRulesDTO) throws URISyntaxException {
        log.debug("REST request to save History Partner Rules : {}", historypartnerRulesDTO);
        historypartnerRulesDTO.setId(null);       
        HistoryPartnerRulesDTO result = historyPartnerRulesService.save(historypartnerRulesDTO);
        /*return ResponseEntity.created(new URI("/api/partner-rules/history" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);*/

    }
    @GetMapping("/partner-rules")
    @Timed
    public ResponseEntity<Set<PartnerRulesDTO>> getAllPartnerRules() {
        log.debug("REST request to get all Partner Rules");
		try {
			Set<PartnerRulesDTO> partnerRules = partnerRulesService.findAll();
			return new ResponseEntity<>(partnerRules, HttpStatus.OK);
		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }
    @GetMapping("/partner-rules/{id}")
    @Timed
    public ResponseEntity<PartnerRulesDTO> getPartnerRules(@PathVariable Long id) {
        log.debug("REST request to get Partner Rules : {}", id);
        PartnerRulesDTO partnerRulesDTO = partnerRulesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(partnerRulesDTO));
    }
    @GetMapping("/partner-rules/{partnerId}/{modeId}")
    @Timed
    public ResponseEntity<PartnerRulesDTO> getPartnerRulesByPartnerAndMode(@PathVariable Long partnerId, @PathVariable Long modeId) {
        log.debug("REST request to get Partner Rules By partnerId and modeId: {}", partnerId,modeId);
        PartnerRulesDTO partnerRulesDTO = partnerRulesService.findPartnerRulesByPartnerAndMode(partnerId, modeId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(partnerRulesDTO));
    }
}