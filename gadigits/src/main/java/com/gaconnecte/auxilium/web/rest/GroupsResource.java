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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.GroupService;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.dto.GroupDTO;
import com.gaconnecte.auxilium.service.dto.GroupDetailDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class GroupsResource {

 private final Logger log = LoggerFactory.getLogger(GroupsResource.class);

    private static final String ENTITY_NAME = "group";

    private final GroupService groupService;
    
    @Autowired
    private LoggerService loggerService;

    public GroupsResource(GroupService groupService) {
        this.groupService = groupService;
    }


    @PostMapping("/group")
    @Timed
    public ResponseEntity<GroupDTO> createGroup(@Valid @RequestBody GroupDTO groupDTO) throws URISyntaxException {
        log.debug("REST request to save Group : {}", groupDTO);
        /*if (groupDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new group cannot already have an ID")).body(null);
        }*/
        System.out.println("idclient group saved"+groupDTO.getIdClient());
        GroupDTO result = groupService.save(groupDTO);
             if (result != null) {
            return ResponseEntity.created(new URI("/api/group/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
        } else {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "create", "Code group existe!")).body(null);
        }
    }

    @GetMapping("/group/{id}")
    @Timed
    public ResponseEntity<GroupDTO> getGroup(@PathVariable Long id) {
        log.debug("REST request to get Group : {}", id);
        GroupDTO groupDTO = groupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(groupDTO));
    }

    @GetMapping("/group/product/{id}")
    @Timed
    public ResponseEntity<Long> getProductGroup(@PathVariable Long id) {
        log.debug("REST request to get Product by Group : {}", id);
        Long productId = groupService.findProductIdByGroup(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(productId));
    }


    @GetMapping("/group")
    @Timed
    public ResponseEntity<List<GroupDTO>> getAllGroup() {
        log.debug("REST request to get a list of Group");
        List<GroupDTO> listgroupe = groupService.findAll();
        return new ResponseEntity<>(listgroupe, HttpStatus.OK);
    }

    @DeleteMapping("/group/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        log.debug("REST request to delete Group with details : {}", id);
        groupService.deleteGroup(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


}