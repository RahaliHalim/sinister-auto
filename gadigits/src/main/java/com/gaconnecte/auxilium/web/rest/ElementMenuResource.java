package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.ElementMenuService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.ElementMenuDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ElementMenu.
 */
@RestController
@RequestMapping("/api")
public class ElementMenuResource {

    private final Logger log = LoggerFactory.getLogger(ElementMenuResource.class);

    private static final String ENTITY_NAME = "elementMenu";

    private final ElementMenuService elementMenuService;

    public ElementMenuResource(ElementMenuService elementMenuService) {
        this.elementMenuService = elementMenuService;
    }

    /**
     * POST  /element-menus : Create a new elementMenu.
     *
     * @param elementMenuDTO the elementMenuDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new elementMenuDTO, or with status 400 (Bad Request) if the elementMenu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/element-menus")
    @Timed
    public ResponseEntity<ElementMenuDTO> createElementMenu(@RequestBody ElementMenuDTO elementMenuDTO) throws URISyntaxException {
        log.debug("REST request to save ElementMenu : {}", elementMenuDTO);
        if (elementMenuDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new elementMenu cannot already have an ID")).body(null);
        }
        ElementMenuDTO result = elementMenuService.save(elementMenuDTO);
        return ResponseEntity.created(new URI("/api/element-menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /element-menus : Updates an existing elementMenu.
     *
     * @param elementMenuDTO the elementMenuDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated elementMenuDTO,
     * or with status 400 (Bad Request) if the elementMenuDTO is not valid,
     * or with status 500 (Internal Server Error) if the elementMenuDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/element-menus")
    @Timed
    public ResponseEntity<ElementMenuDTO> updateElementMenu(@RequestBody ElementMenuDTO elementMenuDTO) throws URISyntaxException {
        log.debug("REST request to update ElementMenu : {}", elementMenuDTO);
        if (elementMenuDTO.getId() == null) {
            return createElementMenu(elementMenuDTO);
        }
        ElementMenuDTO result = elementMenuService.save(elementMenuDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, elementMenuDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /element-menus : get all the elementMenus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of elementMenus in body
     */
    @GetMapping("/element-menus")
    @Timed
    public List<ElementMenuDTO> getAllElementMenus() {
        log.debug("REST request to get all ElementMenus");
        return elementMenuService.findAll();
    }

    /**
     * GET  /element-menus/:id : get the "id" elementMenu.
     *
     * @param id the id of the elementMenuDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the elementMenuDTO, or with status 404 (Not Found)
     */
    @GetMapping("/element-menus/{id}")
    @Timed
    public ResponseEntity<ElementMenuDTO> getElementMenu(@PathVariable Long id) {
        log.debug("REST request to get ElementMenu : {}", id);
        ElementMenuDTO elementMenuDTO = elementMenuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(elementMenuDTO));
    }

    /**
     * DELETE  /element-menus/:id : delete the "id" elementMenu.
     *
     * @param id the id of the elementMenuDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/element-menus/{id}")
    @Timed
    public ResponseEntity<Void> deleteElementMenu(@PathVariable Long id) {
        log.debug("REST request to delete ElementMenu : {}", id);
        elementMenuService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/element-menus?query=:query : search for the elementMenu corresponding
     * to the query.
     *
     * @param query the query of the elementMenu search
     * @return the result of the search
     */
    @GetMapping("/_search/element-menus")
    @Timed
    public List<ElementMenuDTO> searchElementMenus(@RequestParam String query) {
        log.debug("REST request to search ElementMenus for query {}", query);
        return elementMenuService.search(query);
    }

}
