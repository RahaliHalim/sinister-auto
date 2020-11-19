package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.ObservationService;
import com.gaconnecte.auxilium.domain.Observation;
import com.gaconnecte.auxilium.domain.PriseEnCharge;
import com.gaconnecte.auxilium.domain.Authority;
import com.gaconnecte.auxilium.domain.ResourceNotFoundException;
import com.gaconnecte.auxilium.repository.ObservationRepository;
import com.gaconnecte.auxilium.repository.UserExtraRepository;
import com.gaconnecte.auxilium.service.dto.ObservationDTO;
import com.gaconnecte.auxilium.service.dto.PriseEnChargeDTO;
import com.gaconnecte.auxilium.service.mapper.ObservationMapper;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.repository.UserRepository;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing Observation.
 */
@Service
@Transactional
public class ObservationServiceImpl implements ObservationService{

    private final Logger log = LoggerFactory.getLogger(ObservationServiceImpl.class);
    private final UserExtraRepository userExtraRepository;
    private final ObservationRepository observationRepository;
    private final ObservationMapper observationMapper;

    @Autowired
    private UserRepository userRepository;

    public ObservationServiceImpl(ObservationRepository observationRepository, ObservationMapper observationMapper, UserExtraRepository userExtraRepository) {
        this.observationRepository = observationRepository;
        this.observationMapper = observationMapper;
        this.userExtraRepository = userExtraRepository;
    }

    /**
     * Save a observation.
     *
     * @param observationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ObservationDTO save(ObservationDTO observationDTO) {
        log.debug("Request to save Observation : {}", observationDTO);
        log.debug("Request to save Observation prestation : {}", observationDTO.getSinisterPecId());
        ZonedDateTime date = ZonedDateTime.now();
        Observation observation = observationMapper.toEntity(observationDTO);
            observation.setDate(date);
            observation = observationRepository.save(observation);
            return observationMapper.toDto(observation);
    }

    
    @Transactional(readOnly = true)
    public List<ObservationDTO> findAll() {
        
        return observationRepository.findAll().stream().map(observationMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }
    
    
    
    

    @Override
    @Transactional(readOnly = true)
    public List<ObservationDTO> findAll(String login) {
        log.debug("Request to get all Observations");
        User user = userRepository.findOneUserByLogin(login);
        Set <ObservationDTO> dtos = new HashSet<>();
        List <Observation> entities = observationRepository.findAll();
            for (Observation obs : entities) {
                if (user.getId().equals(obs.getUser().getId())){

                        dtos.add(observationMapper.toDto(obs));
                    
            }
            
       
    }
            if (CollectionUtils.isNotEmpty(dtos)) {
                List<ObservationDTO> ret = new LinkedList<>();
                ret.addAll(dtos);
                return ret;
            }
            return new LinkedList<>();
    }

    
    public List<ObservationDTO> findByPrestation(String login){
    	
    	log.debug("Request to get all Observations");
        User user = userRepository.findOneUserByLogin(login);
        UserExtra userExtra = userExtraRepository.findByUser(user.getId());
        Set <ObservationDTO> dtos = new HashSet<>();
        List <Observation> entities = observationRepository.findAll();
    	log.debug("Request to get all Observations aaa " + entities.size());

        for (Observation obs : entities) {
            if (userExtra.getId().equals(obs.getUser().getId())){
            	
            	if ((obs.getSinisterPrestation().getId()) != null) {

                    dtos.add(observationMapper.toDto(obs));
                    
            	}
                
        }
        
   
}
        if (CollectionUtils.isNotEmpty(dtos)) {
            List<ObservationDTO> ret = new LinkedList<>();
            ret.addAll(dtos);
            return ret;
        }
        return new LinkedList<>();
    }

    
   public List<ObservationDTO> findBySinisterPec(String login){
       User user = userRepository.findOneUserByLogin(login);
       UserExtra userExtra = userExtraRepository.findByUser(user.getId());
       Set <ObservationDTO> dtos = new HashSet<>();
       List <Observation> entities = observationRepository.findAll();
   	log.debug("Request to get all Observations aaa " + entities.size());

       for (Observation obs : entities) {
           if (userExtra.getId().equals(obs.getUser().getId())){
           	
           	if (obs.getPrestationId()== null) {

                   dtos.add(observationMapper.toDto(obs));
                   
           	}
               
       }
       
  
}
       if (CollectionUtils.isNotEmpty(dtos)) {
           List<ObservationDTO> ret = new LinkedList<>();
           ret.addAll(dtos);
           return ret;
       }
       return new LinkedList<>();
   }

    /**
     *  Get one observation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ObservationDTO findOne(Long id) {
        log.debug("Request to get Observation : {}", id);
        Observation observation = observationRepository.findOne(id);
        return observationMapper.toDto(observation);
    }

    /**
     *  Delete the  observation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Observation : {}", id);
        observationRepository.delete(id);
    }

     /**
     *  Get observation by prestationId.
     *
     *  @param prestationId the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public List<ObservationDTO> findObservationByPrestation( Long prestationId) {
        log.debug("Request to get Observations : {}", prestationId);
        List<Observation> observations = observationRepository.findObservationByPrestation( prestationId);
           
        return observations.stream().map(observationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
         
    }

     /**
     *  Get observation by devis.
     *
     *  @param prestationId the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ObservationDTO> findObservationByDevis(Pageable pageable, Long devisId) {
        log.debug("Request to get Observations : {}", devisId);
        Page<Observation> observations = observationRepository.findObservationByDevis(pageable, devisId);
        return observations.map(observationMapper::toDto);
         
    }

  
}
