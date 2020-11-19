package com.gaconnecte.auxilium.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.RefNaturePanne;
import com.gaconnecte.auxilium.domain.RefTypeService;
import com.gaconnecte.auxilium.repository.RefNaturePanneRepository;
import com.gaconnecte.auxilium.service.RefNaturePanneService;
import com.gaconnecte.auxilium.service.dto.RefNaturePanneDTO;
import com.gaconnecte.auxilium.service.dto.RefTypeServiceDTO;
import com.gaconnecte.auxilium.service.mapper.RefNaturePanneMapper;


@Service
@Transactional
public class RefNaturePanneServiceImpl implements RefNaturePanneService {

	private final Logger log = LoggerFactory.getLogger(RefNaturePanneServiceImpl.class);
	
	private final RefNaturePanneRepository refNaturePanneRepository;
	private final RefNaturePanneMapper refNaturePanneMapper;
	
	public RefNaturePanneServiceImpl(RefNaturePanneRepository refNaturePanneRepository, RefNaturePanneMapper refNaturePanneMapper) {
        this.refNaturePanneRepository = refNaturePanneRepository;
        this.refNaturePanneMapper = refNaturePanneMapper;
    }

	@Override
	@Transactional(readOnly = true)
	public List<RefNaturePanneDTO> findAll() {
		log.debug("Request to get all RefNaturePannes");
        List<RefNaturePanne> serviceTypes = refNaturePanneRepository.findAll();
        if (CollectionUtils.isNotEmpty(serviceTypes)) {
            return serviceTypes.stream().map(refNaturePanneMapper::toDto).collect(Collectors.toCollection(ArrayList::new));
        }
        return new ArrayList<>();
	}

	@Override
	@Transactional(readOnly = true)
	public RefNaturePanneDTO findOne(Long id) {
		log.debug("Request to get RefNaturePanne : {}", id);
		RefNaturePanne RefNaturePanne = refNaturePanneRepository.findOne(id);
        return refNaturePanneMapper.toDto(RefNaturePanne);
	}

	@Override
	public RefNaturePanneDTO save(RefNaturePanneDTO refNaturePanneDTO) {
		log.debug("Request to save RefNaturePanne : {}", refNaturePanneDTO);
		RefNaturePanne refNaturePanne = refNaturePanneMapper.toEntity(refNaturePanneDTO);
		refNaturePanne = refNaturePanneRepository.save(refNaturePanne);
		RefNaturePanneDTO result = refNaturePanneMapper.toDto(refNaturePanne);
        return result;
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete RefNaturePanne : {}", id);
		refNaturePanneRepository.delete(id);		
	}
	@Override	
	@Transactional(readOnly = true)
	public RefNaturePanneDTO findByLabel(String label){
		RefNaturePanne refNaturePanne = refNaturePanneRepository.findByLabel(label.toLowerCase().replaceAll("\\s", ""));
	    return refNaturePanneMapper.toDto(refNaturePanne);
	 }
	
	@Override	
	@Transactional(readOnly = true)
	public List<RefNaturePanneDTO> findByActive(){
		 List<RefNaturePanne> serviceTypes = refNaturePanneRepository.findByActive();
	        if (CollectionUtils.isNotEmpty(serviceTypes)) {
	            return serviceTypes.stream().map(refNaturePanneMapper::toDto).collect(Collectors.toCollection(ArrayList::new));
	        }
	        return new ArrayList<>();
	 }
}
