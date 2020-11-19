package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.VatRate;
import com.gaconnecte.auxilium.repository.VatRateRepository;
import com.gaconnecte.auxilium.repository.search.VatRateSearchRepository;
import com.gaconnecte.auxilium.service.dto.VatRateDTO;
import com.gaconnecte.auxilium.service.mapper.VatRateMapper;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.commons.collections.CollectionUtils;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing VatRate.
 */
@Service
@Transactional
public class VatRateService {

    private final Logger log = LoggerFactory.getLogger(VatRateService.class);

    private final VatRateRepository vatRateRepository;

    private final VatRateMapper vatRateMapper;

    private final VatRateSearchRepository vatRateSearchRepository;

    public VatRateService(VatRateRepository vatRateRepository, VatRateMapper vatRateMapper, VatRateSearchRepository vatRateSearchRepository) {
        this.vatRateRepository = vatRateRepository;
        this.vatRateMapper = vatRateMapper;
        this.vatRateSearchRepository = vatRateSearchRepository;
    }

    /**
     * Save a vatRate.
     *
     * @param vatRateDTO the entity to save
     * @return the persisted entity
     */
    public VatRateDTO save(VatRateDTO vatRateDTO) {
        log.debug("Request to save VatRate : {}", vatRateDTO);
        VatRate vatRate = vatRateMapper.toEntity(vatRateDTO);
        vatRate = vatRateRepository.save(vatRate);
        VatRateDTO result = vatRateMapper.toDto(vatRate);
        //vatRateSearchRepository.save(vatRate);
        return result;
    }

    /**
     * Get all the vatRates.
     *
     * @return the list of entities
     */
    public void disableOldVatRates() {
        log.debug("Request to disable all old VatRates");
        Set<VatRate> vatRates = vatRateRepository.findAllVatRate();
        if (CollectionUtils.isNotEmpty(vatRates)) {
            for (VatRate vatRate : vatRates) {
                if (vatRate.getReferencedId() != null && vatRate.getEffectiveDate() != null 
                        && (vatRate.getEffectiveDate().isBefore(LocalDate.now()) || vatRate.getEffectiveDate().isEqual(LocalDate.now()))) {
                    VatRate vat = vatRateRepository.findOne(vatRate.getReferencedId());
                    if (vat != null) {
                        vat.setActive(Boolean.FALSE);
                        vatRateRepository.save(vat);
                    }
                }
            }
        }
    }
    
    /**
     *  Get all the vatRates.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Set<VatRateDTO> findAll() {
      log.debug("Request to get all VatRates");
       Set<VatRate> vatRates = vatRateRepository.findAllVatRate();
      if(CollectionUtils.isNotEmpty(vatRates)) {
        return vatRates.stream().map(vatRateMapper::toDto).collect(Collectors.toSet());
      }
      return new HashSet<>();
    }

    /**
     *  Get one vatRate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public VatRateDTO findOne(Long id) {
        log.debug("Request to get VatRate : {}", id);
        VatRate vatRate = vatRateRepository.findOne(id);
        return vatRateMapper.toDto(vatRate);
    }

    /**
     *  Delete the  vatRate by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete VatRate : {}", id);
        vatRateRepository.delete(id);
        vatRateSearchRepository.delete(id);
    }

    /**
     * Search for the vatRate corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VatRateDTO> search(String query) {
        log.debug("Request to search VatRates for query {}", query);
        return StreamSupport
            .stream(vatRateSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(vatRateMapper::toDto)
            .collect(Collectors.toList());
    }
}
