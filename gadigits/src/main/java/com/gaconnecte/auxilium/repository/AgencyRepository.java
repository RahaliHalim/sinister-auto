package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Agency;
import com.gaconnecte.auxilium.domain.Governorate;
import com.gaconnecte.auxilium.domain.Partner;
import java.util.Set;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Agency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {
    Set<Agency> findAllByPartner(Partner partner);
    Set<Agency> findAllByGenre(Integer genre);
    
    @Query("SELECT agency from Agency agency where agency.partner.id = :partnerId and agency.code = :code and agency.genre = 1 ")
    Agency findAgencyByNameCode(@Param("partnerId") Long partnerId, @Param("code") String code);
    
    
    @Query("SELECT agency from Agency agency where agency.name = :name and agency.partner.id = :id and agency.genre = 2  ")
    Agency findAgenceConcessByNameConcessName(@Param("name") String name, @Param("id") Long id);
    
    @Query("SELECT agency from Agency agency where agency.type = :type and agency.partner = :partner ")
    List<Agency> findAgencyByPartnerAndType(@Param("partner") Partner partner, @Param("type") String type);
    
    @Query("SELECT agency from Agency agency where agency.type IN ('Agent', 'Bureau direct') and agency.partner = :partner ")
    List<Agency> findAgencyByPartnerAndNoCourtier(@Param("partner") Partner partner);
    
    @Query("SELECT agency from Agency agency where agency.governorate = :governorate and agency.partner = :partner ")
    List<Agency> findAgencyByPartnerAndZone(@Param("governorate") Governorate governorate, @Param("partner") Partner partner);
    
    @Query("SELECT agency from Agency agency where agency.governorate = :governorate")
    List<Agency> findAgencyByZone(@Param("governorate") Governorate governorate);
    
    
    
    
}
