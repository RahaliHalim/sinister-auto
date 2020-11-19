package com.gaconnecte.auxilium.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.QuotationHistory;
import com.gaconnecte.auxilium.repository.QuotationHistoryRepository;
import com.gaconnecte.auxilium.service.QuotationHistoryService;
import com.gaconnecte.auxilium.service.dto.QuotationHistoryDTO;
import com.gaconnecte.auxilium.service.mapper.QuotationHistoryMapper;

@Service
@Transactional
public class QuotationHistoryServiceImpl implements QuotationHistoryService{


    private final Logger log = LoggerFactory.getLogger(DevisServiceImpl.class);

    private final QuotationHistoryRepository quotationHistoryRepository;

    private final QuotationHistoryMapper quotationHistoryMapper;



    public QuotationHistoryServiceImpl(QuotationHistoryRepository quotationHistoryRepository, QuotationHistoryMapper quotationHistoryMapper) {
        this.quotationHistoryRepository = quotationHistoryRepository;
        this.quotationHistoryMapper = quotationHistoryMapper;
        
    }

	
	
	@Override
	public QuotationHistoryDTO save(QuotationHistoryDTO quotationHistoryDTO) {
		
		QuotationHistory quotationHistory = quotationHistoryMapper.toEntity(quotationHistoryDTO);
		quotationHistory = quotationHistoryRepository.save(quotationHistory);
		QuotationHistoryDTO result = quotationHistoryMapper.toDto(quotationHistory);
		return result;
	}

}
