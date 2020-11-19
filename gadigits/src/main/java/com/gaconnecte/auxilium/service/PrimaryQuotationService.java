package com.gaconnecte.auxilium.service;

import java.util.Set;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.gaconnecte.auxilium.domain.PrimaryQuotation;
import com.gaconnecte.auxilium.service.dto.PrimaryQuotationDTO;

public interface PrimaryQuotationService {
	
	
	/** 
	 * save a primaryquotation
	 * @param primaryQuotationDTO
	 * @return
	 */
	PrimaryQuotationDTO save(PrimaryQuotationDTO primaryQuotationDTO);
	
	/** 
	 * update a primaryquotation
	 * @param primaryQuotationDTO
	 * @return
	 */
	
	PrimaryQuotationDTO update(PrimaryQuotationDTO primaryQuotationDTO);
	
	/**
	 * get all primaryquotation
	 * @return
	 */
	
    Set<PrimaryQuotationDTO> findAll();


	PrimaryQuotationDTO findOne(Long id);

	PrimaryQuotationDTO findPrimaryQuotationById(Long id);
	
	PrimaryQuotation getEstimation(Long id,Long pecId) throws IllegalAccessException, InvocationTargetException;


}