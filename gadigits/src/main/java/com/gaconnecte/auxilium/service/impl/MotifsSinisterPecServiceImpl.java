package com.gaconnecte.auxilium.service.impl;

import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gaconnecte.auxilium.domain.MotifsSinisterPec;
import com.gaconnecte.auxilium.repository.MotifsSinisterPecRepository;
import com.gaconnecte.auxilium.service.MotifsSinisterPecService;
import com.gaconnecte.auxilium.service.dto.MotifsSinisterPecDTO;
import com.gaconnecte.auxilium.service.mapper.MotifsSinisterPecMapper;


@Service
@Transactional
public class MotifsSinisterPecServiceImpl implements MotifsSinisterPecService {

   private final Logger log = LoggerFactory.getLogger(MotifsSinisterPecServiceImpl.class);

   private final MotifsSinisterPecRepository motifsSinisterPecRepository;
   private final MotifsSinisterPecMapper motifsSinisterPecMapper;
  
   public MotifsSinisterPecServiceImpl(MotifsSinisterPecRepository motifsSinisterPecRepository, MotifsSinisterPecMapper motifsSinisterPecMapper){
     this.motifsSinisterPecRepository = motifsSinisterPecRepository;
     this.motifsSinisterPecMapper = motifsSinisterPecMapper;
   }

  @Override
  public MotifsSinisterPecDTO save(MotifsSinisterPecDTO motifsSinisterPecDTO) {
    
      MotifsSinisterPec motifsSinisterPec = motifsSinisterPecMapper.toEntity(motifsSinisterPecDTO);
      motifsSinisterPec = motifsSinisterPecRepository.save(motifsSinisterPec);
      MotifsSinisterPecDTO result = motifsSinisterPecMapper.toDto(motifsSinisterPec);
          
      return result;
  }
  /*@Override
  public Set<PartnerRulesDTO> findAll() {
      log.debug("Request to get all Partner Rules");
      Set<PartnerRules> partnerRules = partnerRulesRepository.findAllPartnerRules();
      if(CollectionUtils.isNotEmpty(partnerRules)) {
        return partnerRules.stream().map(partnerRulesMapper::toDto).collect(Collectors.toSet());
      }
    return new HashSet<>();
  }
  @Override
  public PartnerRulesDTO findOne(Long id) {

      log.debug("Request to get Partner Rules : {}", id);
      PartnerRules partnerRules = partnerRulesRepository.findOne(id);
      return partnerRulesMapper.toDto(partnerRules);
  }*/

}