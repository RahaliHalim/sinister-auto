package com.gaconnecte.auxilium.repository.prm;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.gaconnecte.auxilium.domain.prm.PartnerRules;

@Repository
public interface PartnerRulesRepository extends JpaRepository<PartnerRules,Long> {

    @Query("SELECT partnerRules FROM PartnerRules partnerRules")
    Set<PartnerRules> findAllPartnerRules();
    @Query("SELECT partnerRules FROM PartnerRules partnerRules WHERE partnerRules.partner.id =:partnerId AND partnerRules.refModeGestion.id =:modeId")
    PartnerRules findPartnerRulesByPartnerAndMode(@Param("partnerId") Long partnerId,@Param("modeId") Long modeId);

}
