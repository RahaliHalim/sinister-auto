package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.domain.UserPartnerMode;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.List;


import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the UserExtra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserExtraRepository extends JpaRepository<UserExtra, Long> {

	@Query("select userExtra from UserExtra userExtra where userExtra.user.id = :idUser")
	UserExtra findByUser(@Param("idUser") Long id);
	@Query("select userExtra from UserExtra userExtra where userExtra.profile.id = :idProfil")
	Set<UserExtra> findByProfil(@Param("idProfil") Long idProfil);
        
	@Query("select userExtra from UserExtra userExtra where userExtra.profile.id IN (6,7)")
	Set<UserExtra> findByProfils();

        @Query("select userExtra from UserExtra userExtra where userExtra.profile.id IN (5,9,11,14,15,16,19,21)")
	Set<UserExtra> findAllResponsibles();
	
        @Query("select userExtra.userPartnerModes from UserExtra userExtra where userExtra.user.id = :idUser")
	Set<UserPartnerMode> findPartnerModeByUser(@Param("idUser") Long idUser);
	@Query("select userExtra from UserExtra userExtra where userExtra.userBossId = :userBId")
	Set<UserExtra> findUserChildToUserBoss(@Param("userBId") Long userBId);
	@Query("select userExtra from UserExtra userExtra where userExtra.personId = :personId and userExtra.profile.id = :profilId")
	UserExtra findByPersonProfil(@Param("personId") Long personId, @Param("profilId") Long profilId);
	@Query("select userExtra from UserExtra userExtra where userExtra.personId = :personId and userExtra.profile.id = :profilId")
	Set<UserExtra> findUserByPersonProfil(@Param("personId") Long personId, @Param("profilId") Long profilId);
	
	@Query("select userExtra from UserExtra userExtra where userExtra.profile.id = 27")
	Set<UserExtra> findResponsbablePEC();
}
