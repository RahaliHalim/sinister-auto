/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.ReportTugPerformance;
import com.gaconnecte.auxilium.service.dto.ReportTugPerformanceDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity ReportTugPerformance and its DTO ReportTugPerformanceDTO.
 * @author hannibaal
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReportTugPerformanceMapper extends EntityMapper <ReportTugPerformanceDTO, ReportTugPerformance> {
    
    
    default ReportTugPerformance fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReportTugPerformance reportTugPerformance = new ReportTugPerformance();
        reportTugPerformance.setId(id);
        return reportTugPerformance;
    }
}
