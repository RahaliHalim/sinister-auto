package com.gaconnecte.auxilium.service;


import com.gaconnecte.auxilium.service.dto.QuotationMPDTO;

public interface QuotationMPService {
	
	QuotationMPDTO save(QuotationMPDTO quotationMPDTO);

	QuotationMPDTO findOneBySinisterPec(Long id);
	
	

}
