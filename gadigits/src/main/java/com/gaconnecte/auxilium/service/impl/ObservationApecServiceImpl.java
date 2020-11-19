package com.gaconnecte.auxilium.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.ObservationApec;
import com.gaconnecte.auxilium.repository.ObservationApecRepository;
import com.gaconnecte.auxilium.service.ObservationApecService;
import com.gaconnecte.auxilium.service.dto.ObservationApecDTO;
import com.gaconnecte.auxilium.service.mapper.ObservationApecMapper;


@Service
@Transactional
public class ObservationApecServiceImpl implements ObservationApecService{
	
	private final Logger log = LoggerFactory.getLogger(ObservationApecServiceImpl.class);
	
	private final ObservationApecRepository observationApecRepository;

    private final ObservationApecMapper observationApecMapper;
	
	public ObservationApecServiceImpl(ObservationApecRepository observationApecRepository, ObservationApecMapper observationApecMapper) {
		        this.observationApecRepository = observationApecRepository;
		        this.observationApecMapper = observationApecMapper;
		    }

	@Override
	public ObservationApecDTO save(ObservationApecDTO observationApecDTO) {
		log.debug("Request to save ObservationApec : {}", observationApecDTO);
		ObservationApec observationApec = observationApecMapper.toEntity(observationApecDTO);
		observationApec = observationApecRepository.save(observationApec);
        return observationApecMapper.toDto(observationApec);
	}

	@Override
	public ObservationApecDTO findOne(Long id) {
		log.debug("Request to get ObservationApec : {}", id);
		ObservationApec observationApec = observationApecRepository.findOne(id);
        return observationApecMapper.toDto(observationApec);
	}
	
	@Override
	public ObservationApecDTO findOneByApecId(Long id) {
		log.debug("Request to get ObservationApec : {}", id);
		ObservationApec observationApec = observationApecRepository.findOneByApecId(id);
        return observationApecMapper.toDto(observationApec);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete ObservationApec : {}", id);
		observationApecRepository.delete(id);
		
	}

}
