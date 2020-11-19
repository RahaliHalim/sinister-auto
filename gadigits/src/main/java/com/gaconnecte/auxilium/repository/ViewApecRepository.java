package com.gaconnecte.auxilium.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.ViewApec;

@Repository
public interface ViewApecRepository extends JpaRepository<ViewApec, Long> {
	
	@Query("SELECT apec from ViewApec apec where  apec.etatApec =:etat and apec.stepPecId =:stepPecId ")
	Set<ViewApec> findSinisterPecAccordByEtat(@Param("etat") Integer etat, @Param("stepPecId") Long stepPecId);

}
