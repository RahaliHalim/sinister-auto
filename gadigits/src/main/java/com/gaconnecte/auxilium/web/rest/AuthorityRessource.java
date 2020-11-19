package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.AuthorityService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.AuthorityDTO;
import com.gaconnecte.auxilium.service.dto.UserDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.annotation.Secured;
import com.gaconnecte.auxilium.security.AuthoritiesConstants;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Authority.
 */
@RestController
@RequestMapping("/api")
public class AuthorityRessource {

    private final Logger log = LoggerFactory.getLogger(AuthorityRessource.class);

    private static final String ENTITY_NAME = "authority";

    private final AuthorityService authorityService;

    public AuthorityRessource(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    /**
     * POST  /authorities : Create a new authority.
     *
     * @param authorityDTO the authorityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new affectExpertDTO, or with status 400 (Bad Request) if the affectExpert has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/authorities")
    @Timed
    public ResponseEntity<AuthorityDTO> createAuthority(@Valid @RequestBody AuthorityDTO authorityDTO) throws URISyntaxException {
        log.debug("REST request to save authority : {}", authorityDTO);
        if (authorityDTO.getName() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new authority cannot already have an ID")).body(null);
        }
        AuthorityDTO result = authorityService.save(authorityDTO);
        return ResponseEntity.created(new URI("/api/authorities/" + result.getName()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getName().toString()))
            .body(result);
    }

    /**
     * PUT  /authorities : Updates an existing authorities.
     *
     * @param authorityDTO the authorityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated authorityDTO,
     * or with status 400 (Bad Request) if the authorityDTO is not valid,
     * or with status 500 (Internal Server Error) if the authorityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/authorities")
    @Timed
    public ResponseEntity<AuthorityDTO> updateAuthority(@Valid @RequestBody AuthorityDTO authorityDTO) throws URISyntaxException {
        log.debug("REST request to update Authority : {}", authorityDTO);
        if (authorityDTO.getName() == null) {
            return createAuthority(authorityDTO);
        }
        AuthorityDTO result = authorityService.save(authorityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, authorityDTO.getName().toString()))
            .body(result);
    }

    /**
     * GET  /authorities : get all the authorities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of affectExperts in body
     */
    @GetMapping("/authorities")
    @Timed
    public ResponseEntity<List<AuthorityDTO>> getAllAuthorities(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of authorities");
        Page<AuthorityDTO> page = authorityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/authorities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

         /**
     * GET  /authorities/:nom : get the "nom" authority.
     *
     * @param nom the id of the AuthorityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the AuthorityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/authorities/{nom}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<AuthorityDTO> getAuthorityByName(@PathVariable String nom) {
        log.debug("REST request to get authority : {}", nom);
        AuthorityDTO authorityDTO = authorityService.findByName(nom);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(authorityDTO));
    }

     /**
     * GET  /authorities : get intern the authorities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of authorities in body
     */
    @GetMapping("/authorities/interne")
    @Timed
    public ResponseEntity<List<AuthorityDTO>> getAuthoritiesInterne() {
        log.debug("REST request to get a page of authority");
        List<AuthorityDTO> authorities = authorityService.findAuthorityInterne();
        return new ResponseEntity<>(authorities, HttpStatus.OK);
    }

    /**
     * GET  /authorities : get intern the authorities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of authorities in body
     */
    @GetMapping("/authorities/externe")
    @Timed
    public ResponseEntity<List<AuthorityDTO>> getAuthoritiesExterne() {
        log.debug("REST request to get a page of authority");
         List<AuthorityDTO> authorities = authorityService.findAuthorityExterne();
        return new ResponseEntity<>(authorities, HttpStatus.OK);
    }

     @GetMapping("/authorities/active")
    @Timed
    public ResponseEntity<List<String>> getAuthoritiesActive() {
        log.debug("REST request to get a page of authority active");
         List<String> authorities = authorityService.findAuthorityActive();
        return new ResponseEntity<>(authorities, HttpStatus.OK);
    }



/**
     * GET  /authorities : get intern the authorities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of authorities in body
     */
    @GetMapping("/authorities/authorities_cellules_combinaison")
    @Timed
    public ResponseEntity<List<String>> getAuthoritiesCellules() {
        log.debug("REST request to get a page of authority_cellule");
         List<String> authoritiesCellules = authorityService.findAuthorityCelluleCombinaison();
        return new ResponseEntity<>(authoritiesCellules, HttpStatus.OK);
    }


   /**
     * GET  /authorities : get the users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of users in body
     */
    @GetMapping("/authorities/users")
    @Timed
    public ResponseEntity<List<UserDTO>> findUsersForRoleGestionnaire() {
        log.debug("REST request to get a page of users");
         List<UserDTO> users = authorityService.findUsersForRoleGestionnaire();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
