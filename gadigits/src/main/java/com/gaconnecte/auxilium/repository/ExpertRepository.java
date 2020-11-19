package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Expert;
import com.gaconnecte.auxilium.domain.Reparateur;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



/**
 * Spring Data JPA repository for the Expert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpertRepository extends JpaRepository<Expert,Long> {
    @Query("SELECT distinct expert from Expert expert ")
	Page<Expert> findAllExpertnonbloque(Pageable pageable);

    @Query("select distinct expert from Expert expert where expert.delegation.id =:villeId")
	Page<Expert> findByVille(Pageable pageable, @Param("villeId") Long villeId);
    
    @Query("select e from Expert e, Delegation d where  e.delegation = d.id  and d.governorate.id = :gouvernoratId")
   	Page<Expert> findByGouvernorat(Pageable pageable, @Param("gouvernoratId") Long gouvernoratId);

	@Query("SELECT e from Expert e where e.numeroFTUSA = :numeroFTUSA and e.raisonSociale = :pname")
	Expert findExpertByFTUSA( @Param("numeroFTUSA") String numeroFTUSA,@Param("pname") String pname);
	
    
}
