package com.gaconnecte.auxilium.service;

import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.multipart.MultipartFile;

import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import com.gaconnecte.auxilium.service.dto.EstimationDTO;
import com.gaconnecte.auxilium.service.dto.QuotationDTO;
import com.gaconnecte.gadigits.gtestimate.xml.request.EstimateList;

public interface QuotationService {

    /**
     * save a quotation
     *
     * @param quotationDTO
     * @return
     */
    QuotationDTO save(MultipartFile[] quotationFiles, QuotationDTO quotationDTO);

    /**
     * save a quotation
     *
     * @param quotationDTO
     * @return
     */
    QuotationDTO update(MultipartFile[] quotationFiles, QuotationDTO quotationDTO);

    /**
     * update a quotation
     *
     * @param quotationPTO
     * @return
     */
    QuotationDTO update(QuotationDTO quotationDTO);

    /**
     * get all quotation
     *
     * @return
     */
    Set<QuotationDTO> findAll();

    Page<AttachmentDTO> findAttachments(Pageable pageable, Long id);

    QuotationDTO findOne(Long id);

    EstimationDTO getCreationEstimateUrl(Long sinisterPecId);

    EstimationDTO getUpdateEstimateUrl(Long id, Long sinisterPecId);

    Long getQuotationType(Long id);

    Float findTtcComplementary(Long idPec, Long idQuot);
    
    public void delete(Long id);
    
    void getEstimateListUrl();
    
    public void subscribeGreetings(StompSession stompSession) throws ExecutionException, InterruptedException;
    
    public void sendEventGtEstimate(StompSession stompSession, Long sinisterPecId, Long quotationId);
    
    public void sendEventFirstConnexionGtEstimate(StompSession stompSession, Long sinisterPecId, Long quotationId);

}
