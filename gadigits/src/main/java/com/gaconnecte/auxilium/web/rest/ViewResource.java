/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.ViewService;
import com.gaconnecte.auxilium.service.dto.ViewExpertDTO;
import com.gaconnecte.auxilium.service.dto.ViewReparatorDTO;
import com.gaconnecte.auxilium.service.dto.ViewUserDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hannibaal
 */
@RestController
@RequestMapping("/api")
public class ViewResource {

    private final Logger log = LoggerFactory.getLogger(ViewResource.class);

    private final ViewService viewService;
    
    public ViewResource(ViewService viewService) {
        this.viewService = viewService;
    }

    /**
     * GET  /view/users : get all the users
     *
     * @return the ResponseEntity with status 200 (OK) and the list of users in body
     */
    @GetMapping("/view/users")
    @Timed
    public List<ViewUserDTO> getAllUsers() {
        log.debug("REST request to get all Users");
        return viewService.findAllUsers();
    }

    /**
     * GET  /view/experts : get all the experts
     *
     * @return the ResponseEntity with status 200 (OK) and the list of experts in body
     */
    @GetMapping("/view/experts")
    @Timed
    public List<ViewExpertDTO> getAllExperts() {
        log.debug("REST request to get all Experts");
        return viewService.findAllExperts();
    }

    /**
     * GET  /view/reparators : get all the reparators
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reparators in body
     */
    @GetMapping("/view/reparators")
    @Timed
    public List<ViewReparatorDTO> getAllReparators() {
        log.debug("REST request to get all Reparators");
        return viewService.findAllReparators();
    }
    
}
