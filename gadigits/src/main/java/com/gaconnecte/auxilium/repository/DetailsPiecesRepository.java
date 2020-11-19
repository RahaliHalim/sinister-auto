package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.DetailsPieces;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Spring Data JPA repository for the DetailsPieces entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailsPiecesRepository extends JpaRepository<DetailsPieces,Long> {

    @Query("select distinct details_pieces from DetailsPieces details_pieces where details_pieces.quotation.id =:devisId and details_pieces.designation.type.id =:typeId and details_pieces.isMo =:isMo and details_pieces.modifiedLine is null")
    List<DetailsPieces> findDetailsMoByDevisAndType(@Param("devisId") Long devisId, @Param("typeId") Long typeId, @Param("isMo") Boolean isMo);
    
    @Query("select distinct details_pieces from DetailsPieces details_pieces where details_pieces.quotation.id =:devisId and details_pieces.designation.type.id =:typeId and details_pieces.isMo =:isMo and details_pieces.modifiedLine != null")
    List<DetailsPieces> findDetailsMoByDevisAndTypeOther(@Param("devisId") Long devisId, @Param("typeId") Long typeId, @Param("isMo") Boolean isMo);
   
    @Query("select  SUM(details_pieces.vetuste*details_pieces.totalTtc/100) from DetailsPieces details_pieces where details_pieces.quotation.id =:devisId and details_pieces.modifiedLine is null group by details_pieces.quotation.id")
    Float sumVetusteByDevis(@Param("devisId") Long devisId);

    @Query("select  SUM(details_pieces.vetuste*details_pieces.totalHt/100) from DetailsPieces details_pieces where details_pieces.quotation.id =:devisId group by details_pieces.quotation.id")
    Float sumVetusteHtByDevis(@Param("devisId") Long devisId);
    
    @Query("select  SUM(detailsPieces.totalTtc) from DetailsPieces detailsPieces where detailsPieces.quotation.id =:devisId and detailsPieces.designation.type.id =:typepiece and detailsPieces.modifiedLine is null and detailsPieces.isMo =:isMo group by detailsPieces.quotation.id")
    Float sumTtcPrByDevis(@Param("devisId") Long devisId, @Param("typepiece") Long typepiece, @Param("isMo") Boolean isMo);
    @Query("select  SUM(detailsPieces.totalHt) from DetailsPieces detailsPieces where detailsPieces.quotation.id =:devisId and detailsPieces.designation.type.id =:typepiece and detailsPieces.modifiedLine is null and detailsPieces.isMo =:isMo group by detailsPieces.quotation.id")
    Float sumThtPrByDevis(@Param("devisId") Long devisId, @Param("typepiece") Long typepiece, @Param("isMo") Boolean isMo);
    
    @Query("select  SUM(detailsPieces.totalTtc) from DetailsPieces detailsPieces where detailsPieces.quotation.id =:devisId and detailsPieces.designation.type.id =:typepiece and detailsPieces.modifiedLine is null group by detailsPieces.quotation.id")
    Float sumTtcPfPiByDevis(@Param("devisId") Long devisId, @Param("typepiece") Long typepiece);
    
    @Query("select distinct details_pieces from DetailsPieces details_pieces where details_pieces.quotationMP.id =:devisId and details_pieces.designation.type.id =:typeId and details_pieces.modifiedLine is null and details_pieces.isMo =:isMo")
    List<DetailsPieces> findDetailsMoByQuotationMPAndType(@Param("devisId") Long devisId, @Param("typeId") Long typeId, @Param("isMo") Boolean isMo);
   
    @Query("select distinct details_pieces from DetailsPieces details_pieces where details_pieces.quotation.id =:quotationId and details_pieces.modifiedLine = :LineModified and  details_pieces.id = (select MAX(id)  from DetailsPieces details_pieces where details_pieces.quotation.id =:quotationId and details_pieces.modifiedLine = :LineModified )")
    DetailsPieces findLineModified(@Param("quotationId") Long quotationId, @Param("LineModified") Long LineModified);
    
    @Query("select distinct details_pieces from DetailsPieces details_pieces where details_pieces.quotation.id =:devisId")
    List<DetailsPieces> findDetailsMoByDevis(@Param("devisId") Long devisId);
    
    @Query("select distinct details_pieces from DetailsPieces details_pieces where details_pieces.quotation.sinisterPec.id =:sinisterPecId and details_pieces.quotation.isConfirme = true and details_pieces.quotation.type = 2")
    Set<DetailsPieces> findDetailsPiecesBySinisterPecId(@Param("sinisterPecId") Long sinisterPecId);
    
    @Query("select distinct details_pieces from DetailsPieces details_pieces where details_pieces.quotation.sinisterPec.id =:sinisterPecId and details_pieces.quotation.isConfirme = false and details_pieces.quotation.type = 2")
    Set<DetailsPieces> findDetailsPiecesAnnuleeBySinisterPecId(@Param("sinisterPecId") Long sinisterPecId);
}
