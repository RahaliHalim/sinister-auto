package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.TugPricingService;
import com.gaconnecte.auxilium.domain.TugPricing;
import com.gaconnecte.auxilium.repository.TugPricingRepository;
import com.gaconnecte.auxilium.service.dto.TugPricingDTO;
import com.gaconnecte.auxilium.service.mapper.TugPricingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TugPricing.
 */
@Service
@Transactional
public class TugPricingServiceImpl implements TugPricingService{

    private final Logger log = LoggerFactory.getLogger(TugPricingServiceImpl.class);

    private final TugPricingRepository tugPricingRepository;

    private final TugPricingMapper tugPricingMapper;

    public TugPricingServiceImpl(TugPricingRepository tugPricingRepository, TugPricingMapper tugPricingMapper) {
        this.tugPricingRepository = tugPricingRepository;
        this.tugPricingMapper = tugPricingMapper;
    }

    /**
     * Save a TugPricing.
     *
     * @param tarifDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TugPricingDTO save(TugPricingDTO tugPricingDTO) {
        log.debug("Request to save TugPricing : {}", tugPricingDTO);
        TugPricing tugPricing = tugPricingMapper.toEntity(tugPricingDTO);
        tugPricing = tugPricingRepository.save(tugPricing);
        return tugPricingMapper.toDto(tugPricing);
    }

    /**
     *  Get all the TugPricings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TugPricingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TugPricings");
        return tugPricingRepository.findAll(pageable)
            .map(tugPricingMapper::toDto);
    }

    /**
     *  Get one TugPricing by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TugPricingDTO findBy(Long id) {
        log.debug("Request to get TugPricing : {}", id);
        TugPricing tugPricing = tugPricingRepository.findBy(id);
        return tugPricingMapper.toDto(tugPricing);
    }

    /**
     *  Delete the  TugPricing by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TugPricing : {}", id);
        tugPricingRepository.delete(id);
    }
}
