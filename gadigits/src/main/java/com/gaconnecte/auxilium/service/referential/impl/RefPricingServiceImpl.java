package com.gaconnecte.auxilium.service.referential.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.referential.RefPricing;
import com.gaconnecte.auxilium.repository.referential.RefPricingRepository;
import com.gaconnecte.auxilium.service.referential.RefPricingService;
import com.gaconnecte.auxilium.service.referential.dto.RefPricingDTO;
import com.gaconnecte.auxilium.service.referential.mapper.RefPricingMapper;

/**
 * Service Implementation for managing RefPricing.
 */
@Service
@Transactional
public class RefPricingServiceImpl implements RefPricingService {

    private final Logger log = LoggerFactory.getLogger(RefPricingServiceImpl.class);

    private final RefPricingRepository refPricingRepository;

    private final RefPricingMapper refPricingMapper;

    public RefPricingServiceImpl(RefPricingRepository refPricingRepository, RefPricingMapper refPricingMapper) {
        this.refPricingRepository = refPricingRepository;
        this.refPricingMapper = refPricingMapper;
    }

    /**
     * Save a refPricing.
     *
     * @param refPricingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefPricingDTO save(RefPricingDTO refPricingDTO) {
        log.debug("Request to save RefPricing : {}", refPricingDTO);
        RefPricing refPricing = refPricingMapper.toEntity(refPricingDTO);
        refPricing = refPricingRepository.save(refPricing);
        RefPricingDTO result = refPricingMapper.toDto(refPricing);
        return result;
    }

    /**
     *  Get all the refPricings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefPricingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefPricings");
        return refPricingRepository.findAll(pageable)
            .map(refPricingMapper::toDto);
    }

    /**
     *  Get one refPricing by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefPricingDTO findOne(Long id) {
        log.debug("Request to get RefPricing : {}", id);
        RefPricing refPricing = refPricingRepository.findOne(id);
        return refPricingMapper.toDto(refPricing);
    }

    /**
     *  Delete the  refPricing by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefPricing : {}", id);
        refPricingRepository.delete(id);
    }

}
