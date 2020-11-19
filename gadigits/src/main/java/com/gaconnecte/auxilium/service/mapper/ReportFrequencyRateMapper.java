package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;

import com.gaconnecte.auxilium.domain.ReportFrequencyRate;
import com.gaconnecte.auxilium.domain.view.ViewSuiviAnnulation;
import com.gaconnecte.auxilium.service.dto.ReportFrequencyRateDTO;

@Mapper(componentModel = "spring", uses = {})
public interface ReportFrequencyRateMapper extends EntityMapper <ReportFrequencyRateDTO, ReportFrequencyRate>{

	

	 default ReportFrequencyRate fromId(Long id) {
	        if (id == null) {
	            return null;
	        }
	        ReportFrequencyRate reportFrequencyRate = new ReportFrequencyRate();
	        reportFrequencyRate.setId(id);
	        return reportFrequencyRate;
	    }
	
}
