package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Apec;
import com.gaconnecte.auxilium.domain.SinisterPec;
import com.gaconnecte.auxilium.service.dto.ApecDTO;

import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Apec entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApecRepository extends JpaRepository<Apec, Long> {


	@Query("SELECT apec from Apec apec where apec.etat = 13 and apec.sinisterPec.step.id = 37 and apec.isConfirme is null")
	Set<Apec> findAllValidAccord();

	@Query("SELECT apec from Apec apec where apec.sinisterPec.id =:id and apec.etat = 13 ")
	Set<Apec> findValidAccordBySinPec(@Param("id") Long id);
	
	@Query("SELECT apec from Apec apec where apec.sinisterPec.id =:id and (apec.etat = 13 or apec.etat = 10 or apec.etat = 17) ")
	Set<Apec> findValidAccordBySinPecForBonSortie(@Param("id") Long id);
	
	@Query("SELECT apec from Apec apec where  apec.etat =:etat ")
	Set<Apec> findSinisterPecAccordByEtat(@Param("etat") Integer etat);
	
	@Query("SELECT apec from Apec apec where apec.decision =:decision ")
	List<Apec> findAllAccordByDecision(@Param("decision") Integer decision);
	@Query("SELECT apec from Apec apec where apec.etat =:etat ")
	List<Apec> findAllAccordByStatus(@Param("etat") Integer etat);
	@Query("SELECT apec from Apec apec where apec.quotationId =:id ")
	Apec findAccordByQuotation(@Param("id") Long id);
	@Query("SELECT MAX(apec.accordNumber) from Apec apec")
	Integer findLastApecNumber();

	@Query("SELECT apec from Apec apec where apec.sinisterPec.id =:id and apec.etat =:etat")
	Apec findAccordBySinPecAndEtat(@Param("id") Long id, @Param("etat") Integer etat);
	
	@Query("SELECT apec from Apec apec where apec.sinisterPec.id =:id and (apec.etat = 2 or apec.etat = 8) ")
	Apec findAccordBySinPecAndEtatFixe(@Param("id") Long id);
	
	@Query("SELECT apec from Apec apec where apec.sinisterPec.id =:id")
	Set<Apec> findAccordBySinPec(@Param("id") Long id);
	
	@Query("SELECT apec from Apec apec where apec.quotationId =:quotationId")
	Set<Apec> findAccordByIdDevis(@Param("quotationId") Long quotationId);

	@Query("SELECT apec from Apec apec where apec.quotationId =:id ")
	Set<Apec> findAccordListByQuotation(@Param("id") Long id);
    
}
