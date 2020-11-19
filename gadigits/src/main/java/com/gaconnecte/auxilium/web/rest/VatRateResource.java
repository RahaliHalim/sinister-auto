package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.VatRateService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.VatRateDTO;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.Optional;

/**
 * REST controller for managing VatRate.
 */
@RestController
@RequestMapping("/api")
public class VatRateResource {

    private final Logger log = LoggerFactory.getLogger(VatRateResource.class);

    private static final String ENTITY_NAME = "vatRate";

    private final VatRateService vatRateService;

    @Autowired
    private HistoryService historyService;

    public VatRateResource(VatRateService vatRateService) {
        this.vatRateService = vatRateService;
    }

    /**
     * POST /vat-rates : Create a new vatRate.
     *
     * @param vatRateDTO the vatRateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new vatRateDTO, or with status 400 (Bad Request) if the vatRate has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vat-rates")
    @Timed
    public ResponseEntity<VatRateDTO> createVatRate(@RequestBody VatRateDTO vatRateDTO) throws URISyntaxException {
        log.debug("REST request to save VatRate : {}", vatRateDTO);
        if (vatRateDTO.getId() == null) {
            vatRateDTO.setStartDate(LocalDate.now());
        }
        VatRateDTO result = vatRateService.save(vatRateDTO);
        if (result != null) {
            historyService.historysave("TauxTVA", result.getId(), null, result, 0, 1, "Création");
        }
        return ResponseEntity.created(new URI("/api/vat-rates/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /vat-rates : Updates an existing vatRate.
     *
     * @param vatRateDTO the vatRateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * vatRateDTO, or with status 400 (Bad Request) if the vatRateDTO is not
     * valid, or with status 500 (Internal Server Error) if the vatRateDTO
     * couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vat-rates")
    @Timed
    public ResponseEntity<VatRateDTO> updateVatRate(@RequestBody VatRateDTO vatRateDTO) throws URISyntaxException {
        log.debug("REST request to update VatRate : {}", vatRateDTO);
        Boolean saveCreatHist = false;
        VatRateDTO oldVatRate = new VatRateDTO();
        if (vatRateDTO.getId() != null) {
            oldVatRate = vatRateService.findOne(vatRateDTO.getId());
        }
        if (vatRateDTO.getId() != null) {
            VatRateDTO vatToUpdateDTO = vatRateService.findOne(vatRateDTO.getId());
            if (vatRateDTO.getVatRate().doubleValue() != vatToUpdateDTO.getVatRate().doubleValue()) {
                vatRateDTO.setReferencedId(vatRateDTO.getId());
                vatRateDTO.setId(null);
                saveCreatHist = true;
            }
        }

        VatRateDTO result = vatRateService.save(vatRateDTO);
        if (saveCreatHist) {
            historyService.historysave("TauxTVA", result.getId(), null, result, 0, 1, "Création");
        }
        historyService.historysave("TauxTVA", result.getId(), oldVatRate, result, 0, 0, "Modification");
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * GET /vat-rates : get all the vatRates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vatRates
     * in body
     */
    @GetMapping("/vat-rates")
    @Timed
    public Set<VatRateDTO> getAllVatRates() {
        log.debug("REST request to get all VatRates");
        vatRateService.disableOldVatRates();
        return vatRateService.findAll();
    }

    /**
     * GET /vat-rates/:id : get the "id" vatRate.
     *
     * @param id the id of the vatRateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * vatRateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vat-rates/{id}")
    @Timed
    public ResponseEntity<VatRateDTO> getVatRate(@PathVariable Long id) {
        log.debug("REST request to get VatRate : {}", id);
        VatRateDTO vatRateDTO = vatRateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vatRateDTO));
    }

    /**
     * DELETE /vat-rates/:id : delete the "id" vatRate.
     *
     * @param id the id of the vatRateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vat-rates/{id}")
    @Timed
    public ResponseEntity<Void> deleteVatRate(@PathVariable Long id) {
        log.debug("REST request to delete VatRate : {}", id);
        vatRateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH /_search/vat-rates?query=:query : search for the vatRate
     * corresponding to the query.
     *
     * @param query the query of the vatRate search
     * @return the result of the search
     */
    @GetMapping("/_search/vat-rates")
    @Timed
    public List<VatRateDTO> searchVatRates(@RequestParam String query) {
        log.debug("REST request to search VatRates for query {}", query);
        return vatRateService.search(query);
    }

}
