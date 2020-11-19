package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.DetailsMo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Spring Data JPA repository for the DetailsMo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailsMoRepository extends JpaRepository<DetailsMo,Long> {

    @Query("select distinct details_mo from DetailsMo details_mo where details_mo.devis.id =:devisId")
    Page<DetailsMo> findDetailsMoByDevis(Pageable pageable,@Param("devisId") Long devisId);
    
    
    /** all details by devis */
    
    @Query("select details_mo from DetailsMo details_mo where details_mo.devis.id =:devisId")
    List<DetailsMo> findAllDetailsMoByDevis(@Param("devisId") Long devisId);
    @Query("select SUM(detailsMo.totalTtc) from DetailsMo detailsMo where detailsMo.devis.id =:devisId")
    Float totalTtcDetailsMoByDevis(@Param("devisId") Long devisId);
    @Query("select SUM(detailsMo.totalHt) from DetailsMo detailsMo where detailsMo.devis.id =:devisId")
    Float totalThtDetailsMoByDevis(@Param("devisId") Long devisId);
    
    
    
    
}
