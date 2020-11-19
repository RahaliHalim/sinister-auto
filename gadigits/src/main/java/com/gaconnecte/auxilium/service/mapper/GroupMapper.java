package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.Group;
import com.gaconnecte.auxilium.service.dto.GroupDTO;

@Mapper(componentModel = "spring", uses = {ProduitMapper.class})
public interface GroupMapper extends EntityMapper <GroupDTO, Group>{



 @Mapping(source = "product.id", target = "idProduct")
 @Mapping(source = "product.libelle", target = "productName")
 GroupDTO toDto(Group group);

 @Mapping(source = "idProduct", target = "product")

 Group toEntity(GroupDTO groupDTO); 
    default Group fromId(Long id) {
        if (id == null) {
            return null;
        }
        Group group = new Group();
        group.setId(id);
        return group;
    }
 
}
