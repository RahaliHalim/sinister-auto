package com.gaconnecte.auxilium.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.ReportFrequencyRate;
import com.gaconnecte.auxilium.repository.ReportFollowUpAssistanceRepository;
import com.gaconnecte.auxilium.repository.ReportFrequencyRateRepository;
import com.gaconnecte.auxilium.repository.ViewSuiviDossiersRepository;
import com.gaconnecte.auxilium.service.dto.ReportFrequencyRateDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.SinisterDTO;
import com.gaconnecte.auxilium.service.dto.ViewSuiviDossiersDTO;
import com.gaconnecte.auxilium.service.mapper.ReportFrequencyRateMapper;
import com.gaconnecte.auxilium.service.mapper.ViewSuiviDossiersMapper;

@Service
@Transactional
public class ReportFrequencyRateService {
	private final Logger log = LoggerFactory.getLogger(ViewSuiviDossiersService.class);
    private final ReportFrequencyRateRepository reportFrequencyRateRepository;
    private final ReportFrequencyRateMapper reportFrequencyRateMapper;
    private final ReportFollowUpAssistanceRepository reportFollowUpAssistanceRepository;

public ReportFrequencyRateService ( ReportFollowUpAssistanceRepository reportFollowUpAssistanceRepository,ReportFrequencyRateRepository reportFrequencyRateRepository,ReportFrequencyRateMapper reportFrequencyRateMapper) {
    	
    	this.reportFrequencyRateRepository = reportFrequencyRateRepository;
    	this.reportFrequencyRateMapper = reportFrequencyRateMapper;
        this.reportFollowUpAssistanceRepository = reportFollowUpAssistanceRepository;

 }

@Transactional(readOnly = true)
public List<ReportFrequencyRateDTO> findAll(SearchDTO searchDTO) {
	Set<ReportFrequencyRate> sinisters = null;
	List<ReportFrequencyRateDTO> ret = new ArrayList<>();

	LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
	LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
	if (searchDTO.getPartnerId() == null) {
        sinisters = reportFrequencyRateRepository.findAllReportFrequencyRateByDates(startDate, endDate);
		} else {
        sinisters = reportFrequencyRateRepository.findAllReportFrequencyRateByPartnerAndDates(searchDTO.getPartnerId(), startDate, endDate);
			}
		Long oldPartner = 0l;
		double frequencyRate = 0;
		if (CollectionUtils.isNotEmpty(sinisters)) {
			for (ReportFrequencyRate current : sinisters) {
				ReportFrequencyRateDTO dto = new ReportFrequencyRateDTO();
						dto.setId(current.getId());
						dto.setPartnerId(current.getPartnerId());
						dto.setPartnerName(current.getPartnerName());
						dto.setIncidentNature(current.getIncidentNature());
						dto.setUsageLabel(current.getUsageLabel());
						
				Long currentPartner = current.getPartnerId();
				if (oldPartner.equals(currentPartner)) {
					dto.setFrequencyRate(frequencyRate);
				} else {
					long countSinister = reportFollowUpAssistanceRepository
							.countReportFollowUpAssistanceByPartnerAndDates(current.getPartnerId(), startDate,
									endDate);
					frequencyRate = current.getContractCount() == 0 ? 0d : (double) countSinister / (double) current.getContractCount();
					dto.setFrequencyRate(frequencyRate);
				}
				ret.add(dto);

			}
			return ret;

		}
		return new ArrayList<>();

}

@Transactional(readOnly = true)
public List<ReportFrequencyRateDTO> Search(SearchDTO searchDTO) {
    LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
    LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
    
    return reportFrequencyRateRepository.findAllReportFrequencyRateByDates(startDate, endDate).stream()
            .map(reportFrequencyRateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
}


}
