package com.gaconnecte.auxilium.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gaconnecte.auxilium.domain.QuotationMP;

public interface QuotationMPRepository extends JpaRepository<QuotationMP,Long>{
	
	@Query(value = "SELECT nextval('hibernate_sequence') ", nativeQuery = true)
    Long getNextSeriesId();
    
	@Query("SELECT quotationMP from QuotationMP quotationMP where quotationMP.sinisterPecId =:id ")
	Set<QuotationMP> findOneBySinisterPec(@Param("id") Long id);

}
