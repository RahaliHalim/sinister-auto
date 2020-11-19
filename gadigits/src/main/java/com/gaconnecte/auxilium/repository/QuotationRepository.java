package com.gaconnecte.auxilium.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.gaconnecte.auxilium.domain.Quotation;

@SuppressWarnings("unused")
@Repository
public interface QuotationRepository extends JpaRepository<Quotation,Long>{

    @Query(value = "SELECT nextval('hibernate_sequence') ", nativeQuery = true)
    Long getNextSeriesId();
    @Query("SELECT quotation.stampDuty from Quotation quotation where quotation.id = :id")
    Float findStampDutyByQuotation(@Param("id") Long id);
    @Query("SELECT SUM(quotation.ttcAmount) from Quotation quotation where quotation.sinisterPec.id = :idPec and quotation.id != :idQuot")
    Float findTtcComplementary(@Param("idPec") Long idPec, @Param("idQuot") Long idQuot);
    @Query(value = "SELECT SUM(total_ttc) from details_pieces where quotation_id = :idQuot AND modified_line IS NULL AND designation_id IS NOT NULL", nativeQuery=true)
    Float findTtcQuotation(@Param("idQuot") Long idQuot);
    @Query("select type from Quotation q where q.id = :id")
    Long getQuotationType(@Param("id") Long id);
}
