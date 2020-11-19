package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.QuotationStatusService;
import com.gaconnecte.auxilium.domain.QuotationStatus;
import com.gaconnecte.auxilium.repository.QuotationStatusRepository;
import com.gaconnecte.auxilium.service.dto.QuotationStatusDTO;
import com.gaconnecte.auxilium.service.mapper.QuotationStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing RefTva.
 */
@Service
@Transactional
public class QuotationStatusServiceImpl implements QuotationStatusService{

    private final Logger log = LoggerFactory.getLogger(QuotationStatusServiceImpl.class);

    private final QuotationStatusRepository quotationStatusRepository;

    private final QuotationStatusMapper quotationStatusMapper;

    public QuotationStatusServiceImpl(QuotationStatusRepository quotationStatusRepository,QuotationStatusMapper quotationStatusMapper) {
        this.quotationStatusRepository = quotationStatusRepository;
        this.quotationStatusMapper = quotationStatusMapper;
    }

    /**
     * Save a refTva.
     *
     * @param refTvaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public QuotationStatusDTO save(QuotationStatusDTO quotationStatusDTO) {
        log.debug("Request to save QuotationStatusDTO : {}", quotationStatusDTO);
        QuotationStatus quotationStatus = quotationStatusMapper.toEntity(quotationStatusDTO);
        quotationStatus= quotationStatusRepository.save(quotationStatus);
        return quotationStatusMapper.toDto(quotationStatus);
    }

    /**
     *  Get all the refTvas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuotationStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all refTvas");
        return quotationStatusRepository.findAll(pageable)
            .map(quotationStatusMapper::toDto);
    }

    /**
     *  Get one Quotation status by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public QuotationStatusDTO findOne(Long id) {
        log.debug("Request to get Quotation Status : {}", id);
        QuotationStatus quotationStatus = quotationStatusRepository.findOne(id);
        return quotationStatusMapper.toDto(quotationStatus);
    }

    /**
     *  Delete the  QuotationStatus by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Quotation Status : {}", id);
        quotationStatusRepository.delete(id);
    }
}
