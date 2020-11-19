package com.gaconnecte.auxilium.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.gaconnecte.auxilium.service.HistoryPecService;
import com.gaconnecte.auxilium.service.UserService;
import com.gaconnecte.auxilium.service.dto.ApecDTO;
import com.gaconnecte.auxilium.service.dto.HistoryPecDTO;
import com.gaconnecte.auxilium.service.dto.QuotationDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class HistoryPecRessource {
	
	
	 private final Logger log = LoggerFactory.getLogger(HistoryPecRessource.class);

	    private static final String ENTITY_NAME = "history";

	    private final HistoryPecService historyPecService;
	    
	    private final UserService userService;
	    

	    public HistoryPecRessource(HistoryPecService historyPecService, UserService userService) {
	        this.historyPecService = historyPecService;
	        this.userService = userService;
	    }
	    
	    
	    
	    @PostMapping("/historysPec")
	    @Timed
	    public ResponseEntity<HistoryPecDTO> createHistoryPec(@Valid @RequestBody HistoryPecDTO historyPecDTO) throws URISyntaxException {
	        log.debug("REST request to save history : {}", historyPecDTO);
	        String login = SecurityUtils.getCurrentUserLogin();
	        User user = userService.findOneUserByLogin(login);
	        if (historyPecDTO.getId() != null) {
	            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new history cannot already have an ID")).body(null);
	        }
	        historyPecDTO.setUserId(user.getId());
	      HistoryPecDTO result = historyPecService.save(historyPecDTO);
	        return ResponseEntity.created(new URI("/api/historysPec/" + result.getId()))
	            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
	            .body(result);
	    }

	    @GetMapping("/historysPec/{entityId}/{entityName}")
	    @Timed
	    public List<HistoryPecDTO> getHistoryPecByEntity(@PathVariable Long entityId, @PathVariable String entityName ) {
	        log.debug("REST request to get all Histories By Entity");
	        return historyPecService.findHistoryPecByEntity(entityId, entityName);
	    }
	    
	    @GetMapping("/historysPec/quotation/{actionId}/{entityId}/{entityName}")
	    @Timed
	    public QuotationDTO getQuotationByEntityAndHistoryPec(@PathVariable Long actionId, @PathVariable Long entityId, @PathVariable String entityName ) {
	        log.debug("REST request to get Quotation By Histories By Entity");
	        return historyPecService.findHistoryPecQuotationByAction(actionId, entityId, entityName);
	    }
	    
	    @GetMapping("/historysPec/quotationHistory/{historyId}")
	    @Timed
	    public QuotationDTO findHistoryPecQuotationById(@PathVariable Long historyId) {
	        log.debug("REST request to get Quotation By Histories By Entity");
	        return historyPecService.findHistoryPecQuotationById(historyId);
	    }
	    
	    
	    @GetMapping("/historysPec/apecHistory/{historyId}")
	    @Timed
	    public ApecDTO findHistoryPecAccordById(@PathVariable Long historyId) {
	        log.debug("REST request to get Quotation By Histories By Entity");
	        return historyPecService.findHistoryPecApecById(historyId);
	    }
	    
	    
	    
	    @GetMapping("/historysPec/quotation/{sinisterPecId}")
	    @Timed
	    public List<HistoryPecDTO> findListDevisByHistoryPec(@PathVariable Long sinisterPecId) {
	        return historyPecService.findListDevis(sinisterPecId);
	    }
	    

	    @GetMapping("/historysPec/apec/{sinisterPecId}")
	    @Timed
	    public List<HistoryPecDTO> findListAccordByHistoryPec(@PathVariable Long sinisterPecId) {
	        return historyPecService.findListAccord(sinisterPecId);
	    }

}
