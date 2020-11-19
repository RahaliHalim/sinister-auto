package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Tiers;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Tiers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TiersRepository extends JpaRepository<Tiers, Long> {

	@Query("select distinct tiers from Tiers tiers where tiers.sinisterPec.id =:prestationPecId")
	Page<Tiers> findTiersByPrestationPec(Pageable pageable, @Param("prestationPecId") Long prestationPecId);

	@Query("select tiers from Tiers tiers where tiers.immatriculation =:immatriculationTier")
	Tiers findTiersByImmatriculation(@Param("immatriculationTier") String immatriculationTier);
	
	@Query("select tiers from Tiers tiers where tiers.immatriculation =:immatriculationTier and tiers.compagnie.id =:clientId")
	Set<Tiers> findTierssByImmatriculation(@Param("immatriculationTier") String immatriculationTier, @Param("clientId") Long clientId);

}
