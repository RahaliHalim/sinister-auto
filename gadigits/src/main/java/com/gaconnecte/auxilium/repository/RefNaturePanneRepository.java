package com.gaconnecte.auxilium.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.RefNaturePanne;
import com.gaconnecte.auxilium.service.dto.RefNaturePanneDTO;

@SuppressWarnings("unused")
@Repository
public interface RefNaturePanneRepository extends JpaRepository<RefNaturePanne, Long> {

	@Query("SELECT vb FROM RefNaturePanne vb WHERE LOWER(REPLACE(vb.label,' ','')) =:label")
	RefNaturePanne findByLabel(@Param("label") String label);
	
	@Query("SELECT vb FROM RefNaturePanne vb WHERE vb.active = TRUE")
	List<RefNaturePanne> findByActive();
}
