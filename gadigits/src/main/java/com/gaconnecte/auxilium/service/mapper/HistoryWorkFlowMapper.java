package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.HistoryWorkFlow;
import com.gaconnecte.auxilium.service.dto.HistoryWorkFlowDTO;

@Mapper(componentModel = "spring", uses = {RefActionMapper.class, UserMapper.class})
public interface HistoryWorkFlowMapper extends EntityMapper <HistoryWorkFlowDTO, HistoryWorkFlow> {
	
	@Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "action.id", target = "actionId")
    @Mapping(source = "action.label", target = "actionLabel")
	HistoryWorkFlowDTO toDto(HistoryWorkFlow historyWorkFlow);
   
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "actionId", target = "action")
   
    HistoryWorkFlow toEntity(HistoryWorkFlowDTO historyWorkFlowDTO);
    default HistoryWorkFlow fromId(Long id) {
        if (id == null) {
            return null;
        }
        HistoryWorkFlow historyWorkFlow = new HistoryWorkFlow();
        historyWorkFlow.setId(id);
        return historyWorkFlow;
    }

}
