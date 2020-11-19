package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Loueur;
import com.gaconnecte.auxilium.service.dto.LoueurDTO;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Loueur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoueurRepository extends JpaRepository<Loueur,Long> {
	
  @Query("select loueur from Loueur loueur where loueur.governorate.id =:governorateId")
  List<Loueur> findByGovernorate( @Param("governorateId") Long governorateId);
	    
    
}
