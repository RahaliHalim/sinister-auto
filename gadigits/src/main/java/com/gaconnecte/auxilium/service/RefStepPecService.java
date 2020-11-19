package com.gaconnecte.auxilium.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.PriseEnCharge;
import com.gaconnecte.auxilium.domain.RefStepPec;
import com.gaconnecte.auxilium.repository.RefStepPecRepository;
import com.gaconnecte.auxilium.repository.ViewListChargeRepository;
import com.gaconnecte.auxilium.service.dto.ViewListChargeDTO;
import com.gaconnecte.auxilium.service.referential.dto.RefStepPecDTO;
import com.gaconnecte.auxilium.service.referential.mapper.RefStepPecMapper;
import com.gaconnecte.auxilium.service.mapper.ViewListChargeMapper;


@Service
@Transactional
public class RefStepPecService {
	
private final Logger log = LoggerFactory.getLogger(RefStepPecService.class);
	
	
    private final RefStepPecRepository refStepPecRepository;
    private final RefStepPecMapper refStepPecMapper;
   
    public RefStepPecService (RefStepPecRepository refStepPecRepository,RefStepPecMapper refStepPecMapper )
    {
    	
    	this.refStepPecRepository = refStepPecRepository;
        this.refStepPecMapper = refStepPecMapper;
    }
    
 
    @Transactional(readOnly = true)
    public List<RefStepPecDTO> findAll() {
        //return refStepPecRepository.findAll().stream().map(refStepPecMapper::toDto)
        		
        		return refStepPecMapper.toDto(refStepPecRepository.findAll());
    }
    
    @Transactional(readOnly = true)
    public List<RefStepPecDTO> findAllByCode() {
    	List<RefStepPec> entities =  refStepPecRepository.findAll();  
        List<RefStepPec> refByCode = new ArrayList<>();
        
        	for (RefStepPec entity : entities) {
        		if (entity.getNum() != null) {
        		if (entity.getNum().equals(1L)) {      			
        			refByCode.add(entity);        			
        		}
        		}
        	}
        	if (CollectionUtils.isNotEmpty(refByCode)) {
                return refByCode.stream().map(refStepPecMapper::toDto).collect(Collectors.toList());
            }
            return new LinkedList<>();

    	
    }
    
    
}
