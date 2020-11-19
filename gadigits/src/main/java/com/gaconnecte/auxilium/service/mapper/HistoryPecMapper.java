package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.gaconnecte.auxilium.domain.HistoryPec;
import com.gaconnecte.auxilium.service.dto.HistoryPecDTO;

@Mapper(componentModel = "spring", uses = {RefActionMapper.class, UserMapper.class})
public interface HistoryPecMapper extends EntityMapper <HistoryPecDTO, HistoryPec> {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "action.id", target = "actionId")
    @Mapping(source = "action.label", target = "actionLabel")
    HistoryPecDTO toDto(HistoryPec historyPec);
   
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "actionId", target = "action")
   
    HistoryPec toEntity(HistoryPecDTO historyPecDTO);
    default HistoryPec fromId(Long id) {
        if (id == null) {
            return null;
        }
        HistoryPec historyPec = new HistoryPec();
        historyPec.setId(id);
        return historyPec;
    }
}
