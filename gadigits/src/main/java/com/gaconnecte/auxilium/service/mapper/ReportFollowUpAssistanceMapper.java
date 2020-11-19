/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.ReportFollowUpAssistance;
import com.gaconnecte.auxilium.service.dto.ReportFollowUpAssistanceDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity ReportFollowUpAssistance and its DTO ReportFollowUpAssistanceDTO.
 * @author hannibaal
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReportFollowUpAssistanceMapper extends EntityMapper <ReportFollowUpAssistanceDTO, ReportFollowUpAssistance> {
    
    
    default ReportFollowUpAssistance fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReportFollowUpAssistance reportFollowUpAssistance = new ReportFollowUpAssistance();
        reportFollowUpAssistance.setId(id);
        return reportFollowUpAssistance;
    }
}
