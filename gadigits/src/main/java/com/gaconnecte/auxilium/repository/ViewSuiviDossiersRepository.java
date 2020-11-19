package com.gaconnecte.auxilium.repository;
import com.gaconnecte.auxilium.domain.view.ViewSuiviDossiers;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Set;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewSuiviDossiersRepository extends JpaRepository<ViewSuiviDossiers, Long> {

	 @Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.dateOuverture <= :endDate AND sp.dateOuverture >= :startDate")
	 List<ViewSuiviDossiers> findAllByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	 @Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.compagnieId = :companyId AND sp.dateOuverture <= :endDate AND sp.dateOuverture >= :startDate")
	 List<ViewSuiviDossiers> findAllByDatesAndCompagny(@Param("companyId") Long companyId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


/*		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.zoneId = :zoneF AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByZone(@Param("zoneF") Long zoneF, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.agentId = :agent AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByAgent(@Param("agent") Long agent, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.compagnieId = :compagnie AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByCompagnie(@Param("compagnie") Long compagnie, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.modeGestionId = :mode AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByModeGestion(@Param("mode") Long mode, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.chargeId = :charge AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByCharge(@Param("charge") Long charge, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.idEtatDossier = :etatD AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByEtat(@Param("etatD") Long etatD, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.zoneId = :zoneF AND sp.agentId = :agent AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByZoneAndAgent(@Param("zoneF") Long zoneF,@Param("agent") Long agent, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.zoneId = :zoneF AND sp.compagnieId = :compagnie AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByZoneAndCompagnie(@Param("zoneF") Long zoneF, @Param("compagnie") Long compagnie, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.zoneId = :zoneF AND sp.modeGestionId = :mode AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByZoneAndMode(@Param("zoneF") Long zoneF,@Param("mode") Long mode, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.zoneId = :zoneF AND sp.chargeId = :charge AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByZoneAndCharge(@Param("zoneF") Long zoneF, @Param("charge") Long charge, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.zoneId = :zoneF AND sp.idEtatDossier = :etatD AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByZoneAndEtat(@Param("zoneF") Long zoneF, @Param("etatD") Long etatD, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.agentId = :agent AND sp.compagnieId = :compagnie AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByAgentAndCompagnie(@Param("agent") Long agent, @Param("compagnie") Long compagnie, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.agentId = :agent 	AND sp.modeGestionId = :mode AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByAgentAndMode(@Param("agent") Long agent,@Param("mode") Long mode, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.agentId = :agent AND sp.chargeId = :charge AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByAgentAndCharge(@Param("agent") Long agent, @Param("charge") Long charge, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.agentId = :agent AND sp.idEtatDossier = :etatD AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByAgentAndEtat(@Param("agent") Long agent, @Param("etatD") Long etatD, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.compagnieId = :compagnie AND sp.modeGestionId = :mode AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByCompagnieAndMode(@Param("compagnie") Long compagnie, @Param("mode") Long mode ,@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.compagnieId = :compagnie AND sp.chargeId = :charge AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByCompagnieAndCharge(@Param("compagnie") Long compagnie,@Param("charge") Long charge, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.compagnieId = :compagnie AND sp.idEtatDossier = :etatD AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByCompagnieAndEtat(@Param("compagnie") Long compagnie, @Param("etatD") Long etatD, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.modeGestionId = :mode AND sp.chargeId = :charge AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByModeAndCharge(@Param("mode") Long mode, @Param("charge") Long charge, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.modeGestionId = :mode AND sp.idEtatDossier = :etatD AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByModeAndEtat(@Param("mode") Long mode, @Param("etatD") Long etatD, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.idEtatDossier = :etatD AND sp.chargeId = :charge AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByChargeAndEtat(@Param("charge") Long charge, @Param("etatD") Long etatD, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.zoneId = :zoneF AND sp.agentId = :agent AND sp.compagnieId = :compagnie AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByZoneAndAgentAndCompagnie(@Param("zoneF") Long zoneF,@Param("agent") Long agent,@Param("compagnie") Long compagnie, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.zoneId = :zoneF AND sp.agentId = :agent AND sp.compagnieId = :compagnie AND sp.modeGestionId = :mode AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByZoneAndAgentAndCompagnieAndMode(@Param("zoneF") Long zoneF,@Param("agent") Long agent,@Param("compagnie") Long compagnie, @Param("mode") Long mode, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.zoneId = :zoneF AND sp.agentId = :agent AND sp.compagnieId = :compagnie AND sp.modeGestionId = :mode AND sp.chargeId = :charge AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByZoneAndAgentAndCompagnieAndModeAndCharge(@Param("zoneF") Long zoneF,@Param("agent") Long agent,@Param("compagnie") Long compagnie, @Param("mode") Long mode, @Param("charge") Long charge, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.zoneId = :zoneF AND sp.agentId = :agent AND sp.compagnieId = :compagnie AND sp.modeGestionId = :mode AND sp.chargeId = :charge AND  sp.idEtatDossier = :etatD  AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByAll(@Param("zoneF") Long zoneF,@Param("agent") Long agent,@Param("compagnie") Long compagnie, @Param("mode") Long mode, @Param("charge") Long charge, @Param("etatD") Long etatD, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.agentId = :agent AND sp.compagnieId = :compagnie AND sp.modeGestionId = :mode AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByAgentAndCompagnieAndMode(@Param("agent") Long agent,@Param("compagnie") Long compagnie, @Param("mode") Long mode, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.agentId = :agent AND sp.compagnieId = :compagnie AND sp.modeGestionId = :mode AND sp.chargeId = :charge AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByAgentAndCompagnieAndModeAndCharge(@Param("agent") Long agent,@Param("compagnie") Long compagnie, @Param("mode") Long mode, @Param("charge") Long charge, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.compagnieId = :compagnie AND sp.modeGestionId = :mode AND sp.chargeId = :charge AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByCompagnieAndModeAndCharge(@Param("compagnie") Long compagnie, @Param("mode") Long mode, @Param("charge") Long charge, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.agentId = :agent AND sp.compagnieId = :compagnie AND sp.modeGestionId = :mode AND sp.chargeId = :charge AND  sp.idEtatDossier = :etatD AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByAgentAndCompagnieAndModeAndChargeAndEtat(@Param("agent") Long agent,@Param("compagnie") Long compagnie, @Param("mode") Long mode, @Param("charge") Long charge, @Param("etatD") Long etatD, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.compagnieId = :compagnie AND sp.modeGestionId = :mode AND sp.chargeId = :charge AND  sp.idEtatDossier = :etatD AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByCompagnieAndModeAndChargeAndEtat(@Param("compagnie") Long compagnie, @Param("mode") Long mode, @Param("charge") Long charge, @Param("etatD") Long etatD, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		@Query("SELECT sp from ViewSuiviDossiers sp WHERE sp.compagnieId = :compagnie AND sp.modeGestionId = :mode AND sp.zoneId = :zoneF AND sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
		List<ViewSuiviDossiers> findAllByCompagnieAndModeAndZone(@Param("compagnie") Long compagnie, @Param("mode") Long mode, @Param("zoneF") Long zoneF, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		*/
}
