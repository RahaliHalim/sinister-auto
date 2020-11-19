/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.view.ViewSinisterPecMonitoring;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPecMonitoringDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity ViewSinisterPecMonitoring and its DTO ViewSinisterPecMonitoringDTO.
 * @author hannibaal
 */
@Mapper(componentModel = "spring", uses = {})
public interface ViewSinisterPecMonitoringMapper extends EntityMapper <ViewSinisterPecMonitoringDTO, ViewSinisterPecMonitoring> {
    
    
    default ViewSinisterPecMonitoring fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewSinisterPecMonitoring viewSinisterPecMonitoring = new ViewSinisterPecMonitoring();
        viewSinisterPecMonitoring.setId(id);
        return viewSinisterPecMonitoring;
    }
}
