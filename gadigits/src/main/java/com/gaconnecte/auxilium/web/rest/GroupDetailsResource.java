package com.gaconnecte.auxilium.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.GroupDetailsService;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.dto.DossierDTO;
import com.gaconnecte.auxilium.service.dto.GroupDetailDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class GroupDetailsResource {

private final Logger log = LoggerFactory.getLogger(GroupDetailsResource.class);

    private static final String ENTITY_NAME = "group_details";

    private final GroupDetailsService groupDetailsService;
    
    @Autowired
    private LoggerService loggerService;

    public GroupDetailsResource(GroupDetailsService groupDetailsService) {
        this.groupDetailsService = groupDetailsService;
    }


    @PostMapping("/group-details")
    @Timed
    public ResponseEntity<GroupDetailDTO> createGroupDetails(@Valid @RequestBody GroupDetailDTO groupDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save Group Details : {}", groupDetailsDTO);
        /*if (groupDetailsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new group-details cannot already have an ID")).body(null);
       
        }*/
        groupDetailsService.delete(groupDetailsDTO.getGroupId());
        GroupDetailDTO result = groupDetailsService.save(groupDetailsDTO);
             if (result != null) {
            return ResponseEntity.created(new URI("/api/group-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
        } else {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "create", "Code group-details existe!")).body(null);
        }
    }



     @GetMapping("/group-details")
    @Timed
    public ResponseEntity<List<GroupDetailDTO>> getAllGroupDetails() {
        log.debug("REST request to get a list of Group Details");
        List<GroupDetailDTO> listGroupDetails = groupDetailsService.findAll();
        return new ResponseEntity<>(listGroupDetails, HttpStatus.OK);
    }
     
     
     @GetMapping("/group-details/{id}")
     @Timed
     public ResponseEntity<GroupDetailDTO> getGroupDetail(@PathVariable Long id) {
         log.debug("REST request to get GroupDetail : {}", id);
         GroupDetailDTO groupDetailsDTO = groupDetailsService.findOne(id);
         return ResponseUtil.wrapOrNotFound(Optional.ofNullable(groupDetailsDTO));
     }
     
     @GetMapping("/group-details/group/{id}")
     @Timed
     public ResponseEntity<List<GroupDetailDTO>> getGroupDetailByGroupId(@PathVariable Long id) {
         log.debug("REST request to get GroupDetail By GroupID : {}", id);
         List<GroupDetailDTO> listgroupDetailsDTO = groupDetailsService.findByGroupId(id);
         return new ResponseEntity<>(listgroupDetailsDTO, HttpStatus.OK);
     }

    @DeleteMapping("/group-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroupdetails(@PathVariable Long id) {
        log.debug("REST request to delete Group details : {}", id);
        groupDetailsService.deleteGroupDetails(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


}