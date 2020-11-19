package com.gaconnecte.auxilium.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.gaconnecte.auxilium.service.dto.ApecDTO;
import com.gaconnecte.auxilium.service.dto.HistoryPecDTO;
import com.gaconnecte.auxilium.service.dto.QuotationDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;

public interface HistoryPecService {
	
	
    HistoryPecDTO save(HistoryPecDTO historyPecDTO);
    
    
    void historyPecApecSave(String entityName, Long entityId, ApecDTO apecDTO, Long actionId, SinisterPecDTO sinPec,
            String typeAccord);
    
    void historyPecDevisSave(String entityName, Long entityId, QuotationDTO Quotation, Long actionId,
            SinisterPecDTO sinPec, String historyAvisExpert);
    
    public void historyPecsaveUser(String entityName, Long entityId, UserExtraDTO oldValue, UserExtraDTO newValue,
            String operationName);
    
    void historyPecsave(String entityName, Long entityId, Object oldValue, Object NewValue, int lastValue, int newValue,
            String operationName);
    
    QuotationDTO findHistoryPecQuotationByAction(Long id, Long entityId, String entityName);

    
    void historyPecsaveQuote(String entityName, Long entityId, Object oldValue, Object newValue, int lastStep, int newStep,
            String operationName, Long quotationId);
    
    QuotationDTO findHistoryPecQuotationById(Long historyId);
    
    ApecDTO findHistoryPecApecById(Long historyId);
    
    Page<HistoryPecDTO> findAll(Pageable pageable);
    
    HistoryPecDTO findOne(Long id);
    
    void delete(Long id);
    
    List<HistoryPecDTO> findHistoryPecByEntity(Long entityId, String entityName);

    HistoryPecDTO findHistoryPecByEntity(Long id);
    
    List<HistoryPecDTO> findListDevis(Long sinisterPecId);
    List<HistoryPecDTO> findListAccord(Long sinisterPecId);





	

}
