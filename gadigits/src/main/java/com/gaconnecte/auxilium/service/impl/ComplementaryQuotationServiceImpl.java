package com.gaconnecte.auxilium.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gaconnecte.auxilium.domain.ComplementaryQuotation;
import com.gaconnecte.auxilium.repository.ComplementaryQuotationRepository;
import com.gaconnecte.auxilium.service.ComplementaryQuotationService;
import com.gaconnecte.auxilium.service.dto.ComplementaryQuotationDTO;
import com.gaconnecte.auxilium.service.dto.PrimaryQuotationDTO;
import com.gaconnecte.auxilium.service.mapper.ComplementaryQuotationMapper;

@Service
@Transactional
public class ComplementaryQuotationServiceImpl implements ComplementaryQuotationService {


 private final Logger log = LoggerFactory.getLogger(ComplementaryQuotationServiceImpl.class);

	 private final ComplementaryQuotationRepository complementaryQuotationRepository;
	 
	 private final ComplementaryQuotationMapper complementaryQuotationMapper;
	 
 
	 
	 public ComplementaryQuotationServiceImpl(ComplementaryQuotationRepository complementaryQuotationRepository, ComplementaryQuotationMapper complementaryQuotationMapper) {
	        this.complementaryQuotationRepository = complementaryQuotationRepository;
	        this.complementaryQuotationMapper = complementaryQuotationMapper;
	        
	        
	    }
	
	@Override
	public ComplementaryQuotationDTO save(ComplementaryQuotationDTO complementaryQuotationDTO) {
		
		 log.debug("Request to save ComplementaryQuotation : {}", complementaryQuotationDTO);
	        ComplementaryQuotation complementaryQuotation = complementaryQuotationMapper.toEntity(complementaryQuotationDTO);
	        complementaryQuotation = complementaryQuotationRepository.save(complementaryQuotation);
	        ComplementaryQuotationDTO result = complementaryQuotationMapper.toDto(complementaryQuotation);
	       
	        return result;
	}

	@Override
	public ComplementaryQuotationDTO update(ComplementaryQuotationDTO complementaryQuotationDTO) {

		log.debug("Request to update ComplementaryQuotation : {}", complementaryQuotationDTO);
            ComplementaryQuotation complementaryQuotation = complementaryQuotationMapper.toEntity(complementaryQuotationDTO);
            complementaryQuotation = complementaryQuotationRepository.save(complementaryQuotation);
            ComplementaryQuotationDTO result = complementaryQuotationMapper.toDto(complementaryQuotation);
            
            return result;
	}

	@Override
	public Set<ComplementaryQuotationDTO> findAll() {

		 log.debug("Request to get all ComplementaryQuotation");
		 List<ComplementaryQuotationDTO> list = complementaryQuotationMapper.toDto(complementaryQuotationRepository.findAll());
         return (new HashSet<ComplementaryQuotationDTO>(list));
       
           
	}

    @Override
	public ComplementaryQuotationDTO findOne(Long id) {
		
		log.debug("Request to get ComplementaryQuotation : {}", id);
        ComplementaryQuotation complementaryQuotation = complementaryQuotationRepository.findOne(id);
        return complementaryQuotationMapper.toDto(complementaryQuotation);
	}

	@Override
	public Set<ComplementaryQuotationDTO> findComplementaryQuotationByIdPEC(Long id) {
		
		//List<ComplementaryQuotationDTO> list = complementaryQuotationMapper.toDto(complementaryQuotationRepository.findComplementaryQuotationByIdPEC(id));
        return null;
		
	}

}