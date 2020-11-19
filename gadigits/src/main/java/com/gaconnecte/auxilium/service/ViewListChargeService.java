package com.gaconnecte.auxilium.service;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.repository.ViewListChargeRepository;
import com.gaconnecte.auxilium.service.dto.ViewListChargeDTO;
import com.gaconnecte.auxilium.service.mapper.ViewListChargeMapper;



@Service
@Transactional
public class ViewListChargeService {
	
	private final Logger log = LoggerFactory.getLogger(ViewListChargeService.class);
	
	
    private final ViewListChargeRepository viewListChargeRepository;
    private final ViewListChargeMapper viewListChargeMapper;
   
    
    
    public ViewListChargeService (ViewListChargeRepository viewListChargeRepository,ViewListChargeMapper viewListChargeMapper )
    {
    	
    	this.viewListChargeRepository = viewListChargeRepository;
        this.viewListChargeMapper = viewListChargeMapper;
    }
    
    @Transactional(readOnly = true)
    public List<ViewListChargeDTO> findAll() {
        return viewListChargeRepository.findAll().stream()
                .map(viewListChargeMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }
 

}
