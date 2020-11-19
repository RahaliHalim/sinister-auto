package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.MotifsSinisterPecService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.MotifsSinisterPecDTO;
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
 * REST controller for managing Partner.
 */
@RestController
@RequestMapping("/api")
public class MotifsSinisterPecResource {

    private final Logger log = LoggerFactory.getLogger(MotifsSinisterPecResource.class);

    private static final String ENTITY_NAME = "motifsSinisterPec";

    private final MotifsSinisterPecService motifsSinisterPecService;

    public MotifsSinisterPecResource(MotifsSinisterPecService motifsSinisterPecService) {
        this.motifsSinisterPecService = motifsSinisterPecService;
    }

    /**
     * POST  /partners : Create a new motif for sinister pec.
     *
     * @param motifsSinisterPecDTO the motifsSinisterPecDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new partnerDTO, or with status 400 (Bad Request) if the partner has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/motifs-pec")
    @Timed
    public ResponseEntity<MotifsSinisterPecDTO> createPecMOtifs(@RequestBody MotifsSinisterPecDTO motifsSinisterPecDTO) throws URISyntaxException {
        System.out.println("save motif backend------");
        log.debug("REST request to save motif sinister pec : {}", motifsSinisterPecDTO);
        if (motifsSinisterPecDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new motif pec cannot already have an ID")).body(null);
        }
        MotifsSinisterPecDTO result = motifsSinisterPecService.save(motifsSinisterPecDTO);
        return ResponseEntity.created(new URI("/api/motifs-pec/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /partners : Updates an existing partner.
     *
     * @param motifsSinisterPecDTO the motifsSinisterPecDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated motifsSinisterPecDTO,
     * or with status 400 (Bad Request) if the partnerDTO is not valid,
     * or with status 500 (Internal Server Error) if the partnerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    /*@PutMapping("/partners")
    @Timed
    public ResponseEntity<PartnerDTO> updatePartner(@RequestBody PartnerDTO partnerDTO) throws URISyntaxException {
        log.debug("REST request to update Partner : {}", partnerDTO);
        if (partnerDTO.getId() == null) {
            return createPartner(partnerDTO);
        }
        PartnerDTO result = partnerService.save(partnerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, partnerDTO.getId().toString()))
            .body(result);
    }*/

    /**
     * GET  /partners : get all the partners.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of partners in body
     */
    /*@GetMapping("/partners")
    @Timed
    public List<PartnerDTO> getAllPartners() {
        log.debug("REST request to get all Partners");
        return partnerService.findAll();
    }*/
    

}
