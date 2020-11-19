package com.gaconnecte.auxilium.service.impl;



import java.util.Comparator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.gaconnecte.auxilium.domain.DetailsPieces;
import com.gaconnecte.auxilium.domain.QuotationMP;

import com.gaconnecte.auxilium.repository.QuotationMPRepository;
import com.gaconnecte.auxilium.service.QuotationMPService;
import com.gaconnecte.auxilium.service.dto.ComplementaryQuotationDTO;
import com.gaconnecte.auxilium.service.dto.DetailsPiecesDTO;
import com.gaconnecte.auxilium.service.dto.QuotationMPDTO;
import com.gaconnecte.auxilium.service.mapper.QuotationMPMapper;


@Service
@Transactional
public class QuotationMPServiceImpl implements QuotationMPService {
	
	private final Logger log = LoggerFactory.getLogger(QuotationServiceImpl.class);
	private final QuotationMPRepository quotationMPRepository;
	private final QuotationMPMapper quotationMPMapper;
	

	public QuotationMPServiceImpl(QuotationMPRepository quotationMPRepository, QuotationMPMapper quotationMPMapper) {
		this.quotationMPRepository = quotationMPRepository;
		this.quotationMPMapper = quotationMPMapper;
	}

	@Override
	public QuotationMPDTO save(QuotationMPDTO quotationMPDTO) {
		log.debug("Request to save Quotation with attachments : {}" + quotationMPDTO.getId());
		QuotationMP quotationMP = quotationMPMapper.toEntity(quotationMPDTO);
		if(quotationMP.getListPieces()!= null){
            for(DetailsPieces dP : quotationMP.getListPieces()) {
            	dP.setQuotationMP(quotationMP);
            }
        } 
		quotationMP = quotationMPRepository.save(quotationMP);
		QuotationMPDTO result = quotationMPMapper.toDto(quotationMP);
		return result;
	}


	@Override
	public QuotationMPDTO findOneBySinisterPec(Long id) {
		log.debug("Request to get Quotation : {}", id);
		Set<QuotationMP> quotationMPs = quotationMPRepository.findOneBySinisterPec(id);
		Comparator<QuotationMP> comparator = Comparator.comparing(QuotationMP::getId);
		QuotationMP quotationMP = quotationMPs.stream().max(comparator).get();
		return quotationMPMapper.toDto(quotationMP);
	}
	
}
