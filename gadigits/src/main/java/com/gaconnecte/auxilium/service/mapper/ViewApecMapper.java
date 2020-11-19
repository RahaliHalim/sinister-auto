package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;

import com.gaconnecte.auxilium.domain.ViewApec;
import com.gaconnecte.auxilium.service.dto.ViewApecDTO;

@Mapper(componentModel = "spring", uses = {})
public interface ViewApecMapper extends EntityMapper <ViewApecDTO, ViewApec> {

	default ViewApec fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewApec viewApec = new ViewApec();
        viewApec.setId(id);
        return viewApec;
    }
}
