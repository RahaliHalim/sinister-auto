package com.gaconnecte.auxilium.service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.gaconnecte.auxilium.repository.ViewContratRepository;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewContratDTO;
import com.gaconnecte.auxilium.service.mapper.ViewContratMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ViewContratService {
	
    private final Logger log = LoggerFactory.getLogger(ViewContratService.class);

    private final ViewContratRepository viewContratRepository;
    private final ViewContratMapper viewContratMapper;
    
    public ViewContratService ( ViewContratRepository viewContratRepository, ViewContratMapper viewContratMapper) {
    	
    	this.viewContratRepository = viewContratRepository;
    	this.viewContratMapper = viewContratMapper;
  	
    }
    
    @Transactional(readOnly = true)
    public List<ViewContratDTO> findAll() {
        return viewContratRepository.findAll().stream()
                .map(viewContratMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Transactional(readOnly = true)
    public Page<ViewContratDTO> findAll(Pageable pageable) {
        return viewContratRepository.findAll(pageable).map(viewContratMapper::toDto);
    }
    
    
    
    
  @Transactional(readOnly = true)
 public Set<ViewContratDTO> findContrat(SearchDTO rechercheDTO) {
       log.debug("first");
        LocalDate startDate = rechercheDTO.getStartDate() != null ? rechercheDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
       LocalDate endDate = rechercheDTO.getEndDate() != null ? rechercheDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
        if ((rechercheDTO.getZoneId() == null) && (rechercheDTO.getPackId() == null)  && (rechercheDTO.getCompagnieId() == null)  ) {
          
        	return viewContratRepository.findAllByDates(startDate, endDate).stream()
            .map(viewContratMapper::toDto)
            .collect(Collectors.toCollection(HashSet::new)); } 
        
        else  if(rechercheDTO.getPackId() == null && rechercheDTO.getCompagnieId() == null) {

       return viewContratRepository.findAllByZone(rechercheDTO.getZoneId(), startDate, endDate).stream()
               .map(viewContratMapper::toDto)
          .collect(Collectors.toCollection(HashSet::new));
  
        }
        
        else  if(rechercheDTO.getZoneId() == null && rechercheDTO.getCompagnieId() == null) {
            
            return viewContratRepository.findAllByPack(rechercheDTO.getPackId(), startDate, endDate).stream()
                    .map(viewContratMapper::toDto)
               .collect(Collectors.toCollection(HashSet::new));
       
             }
 else  if(rechercheDTO.getZoneId() == null && rechercheDTO.getPackId() == null) {
            
            return viewContratRepository.findAllByCompagnie(rechercheDTO.getCompagnieId(), startDate, endDate).stream()
                    .map(viewContratMapper::toDto)
               .collect(Collectors.toCollection(HashSet::new));
       
             }
 else  if ((rechercheDTO.getCompagnieId() == null)  ){
     
     return viewContratRepository.findAllByPackAndZone(rechercheDTO.getPackId(), rechercheDTO.getZoneId(), startDate, endDate).stream()
             .map(viewContratMapper::toDto)
        .collect(Collectors.toCollection(HashSet::new));

      } 
 else  if ((rechercheDTO.getPackId() == null)) {
          
          return viewContratRepository.findAllByAgentAndZone(rechercheDTO.getCompagnieId(), rechercheDTO.getZoneId(), startDate, endDate).stream()
                  .map(viewContratMapper::toDto)
             .collect(Collectors.toCollection(HashSet::new));
     
           }
 else {
	 
		 return viewContratRepository.findAllByAll(rechercheDTO.getPackId(), rechercheDTO.getZoneId(),rechercheDTO.getCompagnieId(),startDate, endDate).stream()
		            .map(viewContratMapper::toDto)
		            .collect(Collectors.toCollection(HashSet::new)) ;

 }
  
  
  }
  
  
  
  @Transactional(readOnly = true)
  public Long getCount() {
      log.debug("Request to get all ViewPolicies");
      return viewContratRepository.count();
  }
  
  
  @Transactional(readOnly = true)
  public Long getCountsWithFiltter(String filter) {
      log.debug("Request to get all ViewPolicies");
      if(StringUtils.isNotBlank(filter)) {
          return viewContratRepository.countAllWithFilter(filter.toLowerCase());
      } else {
          return viewContratRepository.count();
      }
  }
  @Transactional(readOnly = true)
  public Page<ViewContratDTO> findContrat(SearchDTO rechercheDTO, Pageable pageable) {
        log.debug("first");
         LocalDate startDate = rechercheDTO.getStartDate() != null ? rechercheDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
        LocalDate endDate = rechercheDTO.getEndDate() != null ? rechercheDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
         if ((rechercheDTO.getZoneId() == null) && (rechercheDTO.getPackId() == null)  && (rechercheDTO.getCompagnieId() == null)  ) {
           
         	return viewContratRepository.findAllByDates(startDate, endDate,pageable).map(viewContratMapper::toDto);
            } 
         else  if(rechercheDTO.getPackId() == null && rechercheDTO.getCompagnieId() == null) {

        return viewContratRepository.findAllByZone(rechercheDTO.getZoneId(), startDate, endDate,pageable).map(viewContratMapper::toDto);
        
   
         }
         
         else  if(rechercheDTO.getZoneId() == null && rechercheDTO.getCompagnieId() == null) {
             
             return viewContratRepository.findAllByPack(rechercheDTO.getPackId(), startDate, endDate, pageable).map(viewContratMapper::toDto);
               
              }
  else  if(rechercheDTO.getZoneId() == null && rechercheDTO.getPackId() == null) {
             
             return viewContratRepository.findAllByCompagnie(rechercheDTO.getCompagnieId(), startDate, endDate,pageable).map(viewContratMapper::toDto)
                ;
        
              }
  else  if ((rechercheDTO.getCompagnieId() == null)  ){
      
      return viewContratRepository.findAllByPackAndZone(rechercheDTO.getPackId(), rechercheDTO.getZoneId(), startDate, endDate,pageable).map(viewContratMapper::toDto)
      ;

       } 
  else  if ((rechercheDTO.getPackId() == null)) {
           
           return viewContratRepository.findAllByAgentAndZone(rechercheDTO.getCompagnieId(), rechercheDTO.getZoneId(), startDate, endDate, pageable).map(viewContratMapper::toDto)
             ;
      
            }
  else {
 	 
 		 return viewContratRepository.findAllByAll(rechercheDTO.getPackId(), rechercheDTO.getZoneId(),rechercheDTO.getCompagnieId(),startDate, endDate,pageable).map(viewContratMapper::toDto)
 		         ;

  }
   
   
   }

}
