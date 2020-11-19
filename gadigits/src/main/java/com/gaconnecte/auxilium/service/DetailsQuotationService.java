package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.DetailsQuotationDTO;

public interface DetailsQuotationService {

    DetailsQuotationDTO save(DetailsQuotationDTO detailsQuotationDTO);

    DetailsQuotationDTO findOne(Long id);

    DetailsQuotationDTO findOneBySinisterPecId(Long id);

    void delete(Long id);

}