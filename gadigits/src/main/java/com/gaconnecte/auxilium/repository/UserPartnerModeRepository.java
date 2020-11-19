package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.UserPartnerMode;
import com.gaconnecte.auxilium.domain.Partner;
import com.gaconnecte.auxilium.domain.Agency;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.*;

import java.util.Set;


/**
 * Spring Data JPA repository for the UserPartnerMode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPartnerModeRepository extends JpaRepository<UserPartnerMode,Long> {
    
    @Query("select DISTINCT(userPartnerMode.partner) from UserPartnerMode userPartnerMode where userPartnerMode.user.id = :idUser")
	Set<Partner> findPartnerByUser(@Param("idUser") Long idUser);
    @Query("select DISTINCT(userPartnerMode.agency) from UserPartnerMode userPartnerMode where userPartnerMode.user.id = :idUser")
	Set<Agency> findAgencyByUser(@Param("idUser") Long idUser);
    @Query("SELECT userPartnerMode from UserPartnerMode userPartnerMode where userPartnerMode.partner.id = :idPartner and userPartnerMode.modeGestion.id = :modeId and userPartnerMode.userExtra.profile.id IN (5, 6)")
	Set<UserPartnerMode> findUserPartnerModeByPartnerAndAgencyAndMode(@Param("idPartner") Long idPartner, @Param("modeId") Long modeId);
    @Query("SELECT userPartnerMode from UserPartnerMode userPartnerMode where userPartnerMode.partner.id = :idPartner and userPartnerMode.userExtra.profile.id IN (5)")
	Set<UserPartnerMode> findUserPartnerModeByPartner(@Param("idPartner") Long idPartner);
    @Query("SELECT userPartnerMode from UserPartnerMode userPartnerMode where userPartnerMode.partner.id = :idPartner and userPartnerMode.modeGestion.id = :modeId and userPartnerMode.userExtra.profile.id IN (5)")
	Set<UserPartnerMode> findAllUsersByPartnerAndMode(@Param("idPartner") Long idPartner, @Param("modeId") Long modeId);
    
    
}
