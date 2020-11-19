package com.gaconnecte.auxilium.repository.prm;

import com.gaconnecte.auxilium.domain.prm.Convention;
import com.gaconnecte.auxilium.domain.referential.RefPack;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RefPack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConventionRepository extends JpaRepository<Convention, Long> {

	@Query("SELECT convention from Convention convention order by convention.client")
	Set<Convention> findAllPacks();
	@Query("SELECT convention.packs from Convention convention WHERE convention.client.id =:partnerId")
	Set<RefPack> findPackByPartner(@Param("partnerId") Long partnerId);

}
