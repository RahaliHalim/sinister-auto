package com.gaconnecte.auxilium.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.repository.ViewBonificationRepository;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewBonificationDTO;
import com.gaconnecte.auxilium.service.mapper.ViewBonificationMapper;


@Service
@Transactional
public class ViewBonificationService {
	private final Logger log = LoggerFactory.getLogger(ViewBonificationService.class);

	private final ViewBonificationRepository viewBonificationRepository;
	private final ViewBonificationMapper viewBonificationMapper;
	
	
	public ViewBonificationService (ViewBonificationRepository viewBonificationRepository, ViewBonificationMapper viewBonificationMapper) {
		
		this.viewBonificationRepository = viewBonificationRepository;
		this.viewBonificationMapper = viewBonificationMapper;


}
	@Transactional(readOnly = true)
    public List<ViewBonificationDTO> findAll() {
        return viewBonificationRepository.findAll().stream()
                .map(viewBonificationMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }
	
	@Transactional(readOnly = true)
    public List<ViewBonificationDTO> Search(SearchDTO searchDTO) {
        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
        
        return viewBonificationRepository.findAllByDates(startDate, endDate).stream()
                .map(viewBonificationMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }
	
	
	
	
	
	
	
	
	
	
	
	
}
