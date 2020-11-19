package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.History;
import com.gaconnecte.auxilium.service.dto.HistoryDTO;

/**
 * Mapper for the entity Journal and its DTO JournalDTO.
 */
@Mapper(componentModel = "spring", uses = {RefActionMapper.class, UserMapper.class})
public interface HistoryMapper extends EntityMapper <HistoryDTO, History> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "action.id", target = "actionId")
    @Mapping(source = "action.label", target = "actionLabel")
    HistoryDTO toDto(History history);
   
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "actionId", target = "action")
   
    History toEntity(HistoryDTO historyDTO);
    default History fromId(Long id) {
        if (id == null) {
            return null;
        }
        History history = new History();
        history.setId(id);
        return history;
    }
}
