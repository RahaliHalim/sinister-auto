package com.gaconnecte.auxilium.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.RefNaturePanneService;
import com.gaconnecte.auxilium.service.dto.RefNaturePanneDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class RefNaturePanneResource {
	
	private final Logger log = LoggerFactory.getLogger(RefNaturePanneResource.class);
	
	private static final String ENTITY_NAME = "naturePanne";
	
	private final RefNaturePanneService refNaturePanneService;
    private final HistoryService historyService;
    
    public RefNaturePanneResource(RefNaturePanneService refNaturePanneService, HistoryService historyService ) {
        this.refNaturePanneService = refNaturePanneService;
      this.historyService = historyService;
    }
    
    @PostMapping("/ref-nature-pannes")
    @Timed
    public ResponseEntity<RefNaturePanneDTO> createRefNaturePanne(@Valid @RequestBody RefNaturePanneDTO refNaturePanneDTO) throws URISyntaxException {
        log.debug("REST request to save RefNaturePanne : {}", refNaturePanneDTO);
        if (refNaturePanneDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refNaturePanne cannot already have an ID")).body(null);
        }
        RefNaturePanneDTO result = refNaturePanneService.save(refNaturePanneDTO);
        historyService.historysave("NaturePanne",  result.getId(),null,result,0,1, "CREATION");
        return ResponseEntity.created(new URI("/api/ref-nature-pannes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    @PutMapping("/ref-nature-pannes")
    @Timed
    public ResponseEntity<RefNaturePanneDTO> updateRefNaturePanne(@Valid @RequestBody RefNaturePanneDTO refNaturePanneDTO) throws URISyntaxException {
        log.debug("REST request to update RefNaturePanne : {}", refNaturePanneDTO);
        if (refNaturePanneDTO.getId() == null) {
            return createRefNaturePanne(refNaturePanneDTO);
        }
        RefNaturePanneDTO oldRefNaturePanne = refNaturePanneService.findOne(refNaturePanneDTO.getId()); 
        RefNaturePanneDTO result = refNaturePanneService.save(refNaturePanneDTO);
       historyService.historysave("NaturePanne",  result.getId(),oldRefNaturePanne,result,0,0, "Modification");
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refNaturePanneDTO.getId().toString()))
            .body(result);
    }
    
    @GetMapping("/ref-nature-pannes")
    @Timed
    public ResponseEntity<List<RefNaturePanneDTO>> getAllRefNaturePannes() {
        log.debug("REST request to get a page of RefNaturePannes");
        List<RefNaturePanneDTO> page = refNaturePanneService.findAll();
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
    
    @GetMapping("/ref-nature-pannes/{id}")
    @Timed
    public ResponseEntity<RefNaturePanneDTO> getRefNaturePanne(@PathVariable Long id) {
        log.debug("REST request to get RefNaturePanne : {}", id);
        RefNaturePanneDTO refNaturePanneDTO = refNaturePanneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refNaturePanneDTO));
    }
    
    @DeleteMapping("/ref-nature-pannes/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefNaturePanne(@PathVariable Long id) {
        log.debug("REST request to delete RefNaturePanne : {}", id);
        refNaturePanneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/ref-nature-pannes/label/{label}")
    @Timed
    public ResponseEntity<RefNaturePanneDTO>  findByLabel(@PathVariable String label){
        log.debug("REST request to get RefNaturePanne by label: {}", label);
        RefNaturePanneDTO refNaturePanneDTO = refNaturePanneService.findByLabel(label);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refNaturePanneDTO));
    }
    @GetMapping("/ref-nature-pannes/active")
    @Timed
    public ResponseEntity<List<RefNaturePanneDTO>> getAllRefNaturePannesAcitve() {
        log.debug("REST request to get a page of RefNaturePannes");
        List<RefNaturePanneDTO> page = refNaturePanneService.findByActive();
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
