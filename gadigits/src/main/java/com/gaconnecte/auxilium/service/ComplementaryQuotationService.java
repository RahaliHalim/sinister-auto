package com.gaconnecte.auxilium.service;

import java.util.Set;

import com.gaconnecte.auxilium.service.dto.ComplementaryQuotationDTO;

public interface ComplementaryQuotationService {
	
	
	/** 
	 * save a complementaryquotation
	 * @param complementaryQuotationDTO
	 * @return
	 */
	ComplementaryQuotationDTO save(ComplementaryQuotationDTO complementaryQuotationDTO);
	
	/** 
	 * update a complementaryquotation
	 * @param complementaryQuotationDTO
	 * @return
	 */
	
	ComplementaryQuotationDTO update(ComplementaryQuotationDTO complementaryQuotationDTO);
	
	/**
	 * get all complementaryquotation
	 * @return
	 */
	
    Set<ComplementaryQuotationDTO> findAll();


	ComplementaryQuotationDTO findOne(Long id);

	//ComplementaryQuotationDTO findComplementaryQuotationByPEC(Long id);

    Set<ComplementaryQuotationDTO> findComplementaryQuotationByIdPEC(Long id);


}