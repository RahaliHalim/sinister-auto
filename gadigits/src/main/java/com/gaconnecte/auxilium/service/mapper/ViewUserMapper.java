/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.view.ViewUser;
import com.gaconnecte.auxilium.service.dto.ViewUserDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity ViewUser and its DTO ViewUserDTO.
 * @author hannibaal
 */
@Mapper(componentModel = "spring", uses = {})
public interface ViewUserMapper extends EntityMapper <ViewUserDTO, ViewUser> {
    
    
    default ViewUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewUser viewUser = new ViewUser();
        viewUser.setId(id);
        return viewUser;
    }
}
