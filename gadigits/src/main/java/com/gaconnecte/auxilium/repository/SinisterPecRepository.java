package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.PrestationPEC;
import com.gaconnecte.auxilium.domain.SinisterPec;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the SinisterPec entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SinisterPecRepository extends JpaRepository<SinisterPec, Long> {

	@Query("SELECT sinisterPec from SinisterPec sinisterPec where (sinisterPec.decision IN ('REFUSED', 'ACCEPTED', 'CANCELED', 'ACC_WITH_RESRV', 'ACC_WITH_CHANGE_STATUS') and sinisterPec.step.id = 30) or (sinisterPec.oldStep = 20 and sinisterPec.step.id = 30) ")
	Set<SinisterPec> findAllSinisterPecToApprove();

	@Query("SELECT sinisterPec from SinisterPec sinisterPec where sinisterPec.decision = 'REFUSED' and sinisterPec.step.id = 10 and sinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION')")
	Set<SinisterPec> findAllSinisterPecRefusedAndAprouveOrApprvWithModif();

	@Query("SELECT sinisterPec from SinisterPec sinisterPec where sinisterPec.step.id = 3 and  sinisterPec.decision IS NULL  ")

	Set<SinisterPec> findAllSinisterPecBeingProcessed();

	@Query("SELECT sinisterPec from SinisterPec sinisterPec where sinisterPec.step.id = 2 ")
	Set<SinisterPec> findAllSinisterPecConsulterDemandePec();

	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.decision= 'ACCEPTED' and sinisterPec.reparateur is null and sinisterPec.step.id = 11 and sinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION')")
	Set<SinisterPec> findAllPrestationPECAcceptedRepNull();

	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.decision= 'ACCEPTED' and sinisterPec.reparateur is Not null and sinisterPec.step.id = 25 and sinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION')")
	Set<SinisterPec> findAllPrestationPECAcceptedRepIsAffected();

	@Query("SELECT sinisterPec from SinisterPec sinisterPec where sinisterPec.step.id = 1")
	Set<SinisterPec> findAllDemandePec();

	@Query("SELECT sinisterPec from SinisterPec sinisterPec where sinisterPec.user.id =:userId")
	Set<SinisterPec> findSinisterPecByUserId(@Param("userId") Long userId);

	@Query("SELECT sinisterPec from SinisterPec sinisterPec where sinisterPec.assignedTo.id =:assignedToId")
	Set<SinisterPec> findSinisterPecByAssignedId(@Param("assignedToId") Long assignedToId);

	@Query("SELECT sinisterPec from SinisterPec sinisterPec where sinisterPec.sinister.id =:sinisterId")
	SinisterPec findBySinisterId(@Param("sinisterId") Long sinisterId);

	@Query("SELECT sinisterPec from SinisterPec sinisterPec where sinisterPec.decision IN ('CANCELED')")
	Set<SinisterPec> findCanceledSinisterPec();

	@Query("SELECT sinisterPec from SinisterPec sinisterPec where sinisterPec.decision IN ('REFUSED')")
	Set<SinisterPec> findRefusededSinisterPec();

	@Query("SELECT sinisterPec from SinisterPec sinisterPec where sinisterPec.decision = 'CANCELED' and sinisterPec.step.id = 9 and sinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION')")
	Set<SinisterPec> findAllSinisterPecCanceledAndAprouveOrApprvWithModif();

	@Query("SELECT sinisterPec from SinisterPec sinisterPec where sinisterPec.decision = 'ACC_WITH_RESRV' and sinisterPec.step.id = 12 and sinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION')")
	Set<SinisterPec> findAllSinisterPecAccWithResrveAndAprouveOrApprvWithModif();

	@Query("select distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.reparateur.id =:id and sinisterPec.step.id = 25")
	Set<SinisterPec> findPrestationPECsByReparateurInReceptionVehicule(@Param("id") Long id);

	@Query("select distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.reparateur.id =:id and sinisterPec.decision = 'ACCEPTED' and sinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION')")
	List<SinisterPec> findPrestationPECsByReparateur(@Param("id") Long id);
	
	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.expert.id =:id ")
	List<SinisterPec> countNumberAffeExpert(@Param("id") Long id);

	@Query("SELECT count(*) from SinisterPec sinisterPec where (sinisterPec.reparateur.id =:id and sinisterPec.step.id != 40) ")
	Long countNumberAffeReparator(@Param("id") Long id);

	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.expert = null and sinisterPec.step.id = 8 ")
	Set<SinisterPec> findAllPrestationPECInCheckSupported();

	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.reparateur.id =:id and sinisterPec.step.id = 21 ")
	Set<SinisterPec> findSinisterPecForDismantling(@Param("id") Long id);
	
	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.step.id = 21 ")
	Set<SinisterPec> findSinisterPecForDismantlingForSuperUser();

	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.reparateur.id =:id and sinisterPec.step.id = 24 ")
	Set<SinisterPec> findSinisterPecForUpdateDevis(@Param("id") Long id);

	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.reparateur.id =:id and sinisterPec.step.id IN (23, 7, 17) ")
	Set<SinisterPec> findSinisterPecInConfirmationDevis(@Param("id") Long id);
	
	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.step.id IN (23, 7, 17) ")
	Set<SinisterPec> findSinisterPecInConfirmationDevisForSuperUser();
	
	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.step.id = 16 ")
	Set<SinisterPec> findSinisterPecInConfirmationDevisComplementaire();
	
	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where  sinisterPec.step.id = 34 ")
	Set<SinisterPec>findSinisterInRevueValidationDevis();
	
	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.expert.id =:id and sinisterPec.step.id IN (18, 19, 20)")
	Set<SinisterPec> findSinisterPecForExpertOpinion(@Param("id") Long id);

	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.step.id = 26")
	Set<SinisterPec> findAllSinisterPecForValidation(); 
	
	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where  sinisterPec.expert is Not null")
	Set<SinisterPec> findAllPrestationPECInNotCancelExpertMission();

	@Query("SELECT sinisterPec from SinisterPec sinisterPec where sinisterPec.decision = 'ACC_WITH_CHANGE_STATUS' and sinisterPec.step.id = 13 and sinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION')")
	Set<SinisterPec> findAllSinisterPecAccWithChangeStatusAndAprouveOrApprvWithModif();

	@Query("SELECT sinisterPec from SinisterPec sinisterPec where (sinisterPec.decision = 'CANCELED' and sinisterPec.step.id = 9 and sinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION')) or (sinisterPec.decision = 'REFUSED' and sinisterPec.step.id = 10 and sinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION'))")
	Set<SinisterPec> findAllSinisterPecRefusedAndCanceledAndAprouveOrApprvWithModif();
	
	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.generatedLetter = false and sinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION')")
	Set<SinisterPec> findAllPrestationPECForIdaOuverture();
	
	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.decision= 'ACCEPTED' and sinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION')")
	Set<SinisterPec> findAllAnnulationPrestation();
	
	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.step.id = 22")
	Set<SinisterPec> findAllConfirmAnnulationPrestation();
	
	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.decision= 'ACCEPTED' and sinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION')")
	Set<SinisterPec> findAllRefusedPrestation();
	
	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.step.id = 33")
	Set<SinisterPec> findAllConfirmRefusedPrestation();
	
	@Query("SELECT distinct sinisterPec from SinisterPec sinisterPec where sinisterPec.decision IN ('ACCEPTED', 'ACC_WITH_RESRV', 'ACC_WITH_CHANGE_STATUS')")
	Set<SinisterPec> findAllSinPecWithDecision();
	
	@Query("SELECT sinisterPec from SinisterPec sinisterPec where sinisterPec.step.id = 35 ")
	Set<SinisterPec> findAllSinPecForVerificationPrinted();
	
	@Query("SELECT sinisterPec from SinisterPec sinisterPec where (sinisterPec.step.id = 36 or sinisterPec.step.id = 40) ")
	Set<SinisterPec> findAllSinPecForSignatureBonSortie();
	
	@Query("SELECT sinisterPec from SinisterPec sinisterPec where sinisterPec.step.id != null ")
	Set<SinisterPec> findAllSinPecForModificationPrestation();
	
	@Query("SELECT sinisterPec from SinisterPec sinisterPec where sinisterPec.step.id = 27 ")
	Set<SinisterPec> findAllSinPecModificationPrix();
	
	@Query("SELECT count(*) from SinisterPec sinisterPec where sinisterPec.id <= :idPec")
	Long findCountSinisterPec(@Param("idPec") Long idPec);
	
	//int countByIdLowerThanEqual(int idPec);
	
}
