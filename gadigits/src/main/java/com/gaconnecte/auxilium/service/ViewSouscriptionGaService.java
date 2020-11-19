package com.gaconnecte.auxilium.service;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gaconnecte.auxilium.repository.ViewSouscriptionGaRepository;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewContratDTO;
import com.gaconnecte.auxilium.service.dto.ViewSouscriptionGaDTO;
import com.gaconnecte.auxilium.service.mapper.ViewSouscriptionGaMapper;





@Service
@Transactional
public class ViewSouscriptionGaService {
	private final Logger log = LoggerFactory.getLogger(ViewSouscriptionGaService.class);

	private final ViewSouscriptionGaRepository viewSouscriptionGaRepository;
	private final ViewSouscriptionGaMapper viewSouscriptionGaMapper;
	
	public ViewSouscriptionGaService (ViewSouscriptionGaRepository viewSouscriptionGaRepository, ViewSouscriptionGaMapper viewSouscriptionGaMapper) {
	
			this.viewSouscriptionGaRepository = viewSouscriptionGaRepository;
			this.viewSouscriptionGaMapper = viewSouscriptionGaMapper;


}
	
	
	
	@Transactional(readOnly = true)
    public List<ViewSouscriptionGaDTO> findAll() {
        return viewSouscriptionGaRepository.findAll().stream()
                .map(viewSouscriptionGaMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }
	
	 
    @Transactional(readOnly = true)
    public Page<ViewSouscriptionGaDTO> findAll(Pageable pageable) {
        return viewSouscriptionGaRepository.findAll(pageable)
                .map(viewSouscriptionGaMapper::toDto);
    }
	
	
	@Transactional(readOnly = true)
	 public Page<ViewSouscriptionGaDTO> Search(SearchDTO rechercheDTO,Pageable pageable) {
	        LocalDate startDate = rechercheDTO.getStartDate() != null ? rechercheDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
	       LocalDate endDate = rechercheDTO.getEndDate() != null ? rechercheDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
	    //    if ((rechercheDTO.getZoneId() == null) && (rechercheDTO.getServiceId() == null)  && (rechercheDTO.getCompagnieId() == null)  ) {
	            return viewSouscriptionGaRepository.findAllByDates(startDate, endDate,pageable).map(viewSouscriptionGaMapper::toDto);
	          } 
	        
	       
	
	@Transactional(readOnly = true)
	 public List<ViewSouscriptionGaDTO> Search(SearchDTO rechercheDTO) {
	        LocalDate startDate = rechercheDTO.getStartDate() != null ? rechercheDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
	       LocalDate endDate = rechercheDTO.getEndDate() != null ? rechercheDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
	            return viewSouscriptionGaRepository.findAllByDates(startDate, endDate).stream()
	            		.map(viewSouscriptionGaMapper::toDto)
			            .collect(Collectors.toCollection(LinkedList::new));

	          } 
	        
	
	   
	    @Transactional(readOnly = true)
	    public Long getCount() {
	        log.debug("Request to get all ViewPolicies");
	        return viewSouscriptionGaRepository.count();
	    }
	    
	    
	    @Transactional(readOnly = true)
	    public Long getCountsWithFiltter(String filter) {
	        log.debug("Request to get all ViewPolicies");
	        if(StringUtils.isNotBlank(filter)) {
	            return viewSouscriptionGaRepository.countAllWithFilter(filter.toLowerCase());
	        } else {
	            return viewSouscriptionGaRepository.count();
	        }
	    }
	    
	  
	  

 
}