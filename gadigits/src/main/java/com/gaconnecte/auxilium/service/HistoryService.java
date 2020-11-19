package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.ApecDTO;
import com.gaconnecte.auxilium.service.dto.DelegationDTO;
import com.gaconnecte.auxilium.service.dto.ExpertDTO;
import com.gaconnecte.auxilium.service.dto.HistoryDTO;
import com.gaconnecte.auxilium.service.dto.QuotationDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Journal.
 */
public interface HistoryService {

    /**
     * Save a journal.
     *
     * @param journalDTO the entity to save
     * @return the persisted entity
     */
    HistoryDTO save(HistoryDTO historyDTO);

    /**
     * Get all the journals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<HistoryDTO> findAll(Pageable pageable);

    void historyApecSave(String entityName, Long entityId, ApecDTO apecDTO, Long actionId, SinisterPecDTO sinPec,
            String typeAccord);

    /**
     * Get the "id" journal.
     *
     * @param id the id of the entity
     * @return the entity
     */
    HistoryDTO findHistoryByEntity(Long id);

    ApecDTO findHistoryApecById(Long historyId);

    /**
     * Delete the "id" journal.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    void historysave(String entityName, Long entityId, Object oldValue, Object NewValue, int lastValue, int newValue,
            String operationName);

    void historysaveQuote(String entityName, Long entityId, Object oldValue, Object newValue, int lastStep, int newStep,
            String operationName, Long quotationId);

    void historyDevisSave(String entityName, Long entityId, QuotationDTO Quotation, Long actionId,
            SinisterPecDTO sinPec, String historyAvisExpert);

    List<HistoryDTO> findHistoryByEntity(Long entityId, String entityName);

    QuotationDTO findHistoryQuotationByAction(Long id, Long entityId, String entityName);

    QuotationDTO findHistoryQuotationById(Long historyId);

    /**
     * Search for the journal corresponding to the query.
     *
     * @param query    the query of the search
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    HistoryDTO findHistoryByEntityAndAssistance(Long entityId, String entityName, String operation);

    List<HistoryDTO> findListDevis(Long sinisterPecId);

    List<HistoryDTO> findListAccord(Long sinisterPecId);

    HistoryDTO findOne(Long id);

    public void historysaveUser(String entityName, Long entityId, UserExtraDTO oldValue, UserExtraDTO newValue,
            String operationName);

}
