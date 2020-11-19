package com.gaconnecte.auxilium.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.DetailsQuotation;
import com.gaconnecte.auxilium.repository.DetailsQuotationRepository;
import com.gaconnecte.auxilium.service.DetailsQuotationService;
import com.gaconnecte.auxilium.service.dto.DetailsQuotationDTO;
import com.gaconnecte.auxilium.service.mapper.DetailsQuotationMapper;

@Service
@Transactional
public class DetailsQuotationServiceImpl implements DetailsQuotationService {

    private final Logger log = LoggerFactory.getLogger(DetailsQuotationServiceImpl.class);

    private final DetailsQuotationRepository detailsQuotationRepository;

    private final DetailsQuotationMapper detailsQuotationMapper;

    public DetailsQuotationServiceImpl(DetailsQuotationRepository detailsQuotationRepository,
            DetailsQuotationMapper detailsQuotationMapper) {
        this.detailsQuotationRepository = detailsQuotationRepository;
        this.detailsQuotationMapper = detailsQuotationMapper;
    }

    @Override
    public DetailsQuotationDTO save(DetailsQuotationDTO detailsQuotationDTO) {
        log.debug("Request to save DetailsQuotation : {}", detailsQuotationDTO);
        DetailsQuotation detailsQuotation = detailsQuotationMapper.toEntity(detailsQuotationDTO);
        detailsQuotation = detailsQuotationRepository.save(detailsQuotation);
        return detailsQuotationMapper.toDto(detailsQuotation);
    }

    @Override
    public DetailsQuotationDTO findOne(Long id) {
        log.debug("Request to get DetailsQuotation : {}", id);
        DetailsQuotation detailsQuotation = detailsQuotationRepository.findOne(id);
        return detailsQuotationMapper.toDto(detailsQuotation);
    }

    @Override
    public DetailsQuotationDTO findOneBySinisterPecId(Long id) {
        log.debug("Request to get DetailsQuotation : {}", id);
        DetailsQuotation detailsQuotation = detailsQuotationRepository.findOneBySinisterPecId(id);
        if (detailsQuotation == null) {
            return new DetailsQuotationDTO();
        } else {
            return detailsQuotationMapper.toDto(detailsQuotation);
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetailsQuotation : {}", id);
        detailsQuotationRepository.delete(id);

    }

}