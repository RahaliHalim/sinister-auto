package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefAgeVehiculeService;
import com.gaconnecte.auxilium.domain.RefAgeVehicule;
import com.gaconnecte.auxilium.repository.RefAgeVehiculeRepository;
import com.gaconnecte.auxilium.service.dto.RefAgeVehiculeDTO;
import com.gaconnecte.auxilium.service.mapper.RefAgeVehiculeMapper;
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
public class RefAgeVehiculeServiceImpl implements RefAgeVehiculeService{

    private final Logger log = LoggerFactory.getLogger(RefAgeVehiculeServiceImpl.class);

    private final RefAgeVehiculeRepository refAgeVehiculeRepository;

    private final RefAgeVehiculeMapper refAgeVehiculeMapper;

    public RefAgeVehiculeServiceImpl(RefAgeVehiculeRepository refAgeVehiculeRepository,RefAgeVehiculeMapper refAgeVehiculeMapper) {
        this.refAgeVehiculeRepository = refAgeVehiculeRepository;
        this.refAgeVehiculeMapper = refAgeVehiculeMapper;
    }

   

    /**
     *  Get all the ref age vehicules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefAgeVehiculeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ref age vehicules");
        return refAgeVehiculeRepository.findAll(pageable)
            .map(refAgeVehiculeMapper::toDto);
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public RefAgeVehiculeDTO findOne(Long id) {
        log.debug("Request to get Ref age vehicule : {}", id);
        RefAgeVehicule refAgeVehicule = refAgeVehiculeRepository.findOne(id);
        return refAgeVehiculeMapper.toDto(refAgeVehicule);
    }
  
 
}
