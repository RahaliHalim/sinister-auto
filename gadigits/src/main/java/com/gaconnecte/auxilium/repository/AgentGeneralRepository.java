package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.AgentGeneral;

import org.springframework.stereotype.Repository;


import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AgentGeneral entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgentGeneralRepository extends JpaRepository<AgentGeneral, Long> {
   /* 
	@Query("select agent.listAgence from AgentGeneral agent where agent.id =:id")
    List<AgentAgence> findAgencesByAgent(@Param("id") Long id);*/
}
