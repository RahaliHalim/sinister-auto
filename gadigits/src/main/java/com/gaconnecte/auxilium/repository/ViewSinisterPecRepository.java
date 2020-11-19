package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.SinisterPec;
import com.gaconnecte.auxilium.domain.ViewSinisterPec;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the ViewSinisterPec entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ViewSinisterPecRepository extends JpaRepository<ViewSinisterPec, Long> {

    @Query("SELECT distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.decision= 'ACCEPTED' and viewSinisterPec.reparateurId is null and viewSinisterPec.step = 11 and viewSinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION')")
    Set<ViewSinisterPec> findAllAcceptedAndNoReparator();

    @Query("SELECT distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.assignedToId =:id and viewSinisterPec.reparateurId is Not null ")
    Set<ViewSinisterPec> findAllAcceptedAndHasReparator(@Param("id") Long id);

    @Query("SELECT distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.reparateurId is Not null ")
    Set<ViewSinisterPec> findAllAcceptedAndHasReparatorForSuperUser();

    @Query("select distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.reparateurId =:id and viewSinisterPec.step IN (15,25)")
    Set<ViewSinisterPec> findAllPecsForReparator(@Param("id") Long id);

    @Query("select distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.step IN (15,25)")
    Set<ViewSinisterPec> findAllPecsForReparatorForSuperUser();

    @Query("SELECT distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.expertId =:id and viewSinisterPec.step IN (18, 19, 20)")
    Set<ViewSinisterPec> findAllPecsForExpertOpinion(@Param("id") Long id);

    @Query("SELECT distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.step IN (18, 19, 20)")
    Set<ViewSinisterPec> findAllPecsForExpertOpinionForSuperUser();

    @Query("SELECT distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where  viewSinisterPec.assignedToId =:id and viewSinisterPec.step = 8")
    Set<ViewSinisterPec> findAllInMissionExpert(@Param("id") Long id);

    @Query("SELECT distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where  viewSinisterPec.step = 8")
    Set<ViewSinisterPec> findAllInMissionExpertForSuperUser();

    @Query("SELECT distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.assignedToId =:id and viewSinisterPec.expertId is Not null")
    Set<ViewSinisterPec> findAllInCancelMissionExpert(@Param("id") Long id);

    @Query("SELECT distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.expertId is Not null")
    Set<ViewSinisterPec> findAllInCancelMissionExpertForSuperUser();

    @Query("SELECT distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.step IN (16, 26)")
    Set<ViewSinisterPec> findAllPecsForValidation();

    @Query("SELECT distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.reparateurId =:id and viewSinisterPec.step = 24 ")
    Set<ViewSinisterPec> findAllPecsForUpdateDevis(@Param("id") Long id);

    @Query("SELECT distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.step = 24 ")
    Set<ViewSinisterPec> findAllPecsForUpdateDevisForSuperUser();

    @Query("SELECT distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.assignedToId =:id and viewSinisterPec.step = 34 ")
    Set<ViewSinisterPec> findAllPecsInRevueValidationDevis(@Param("id") Long id);

    @Query("SELECT distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.step = 34 ")
    Set<ViewSinisterPec> findAllPecsInRevueValidationDevisForSuperUser();

    @Query("SELECT distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.step != null ")
    Set<ViewSinisterPec> findAllSinPecForModificationPrestation();

    @Query("SELECT viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.assignedToId =:assignedToId")
    Set<ViewSinisterPec> findSinisterPecByAssignedId(@Param("assignedToId") Long assignedToId);

    @Query("SELECT viewSinisterPec from ViewSinisterPec viewSinisterPec")
    Set<ViewSinisterPec> findAllSinPecWithDecision();

    @Query("SELECT viewSinisterPec from ViewSinisterPec viewSinisterPec where (viewSinisterPec.decision IN ('REFUSED', 'ACCEPTED', 'CANCELED', 'ACC_WITH_RESRV', 'ACC_WITH_CHANGE_STATUS') and viewSinisterPec.step = 30) or (viewSinisterPec.oldStep = 20 and viewSinisterPec.step = 30) ")
    Set<ViewSinisterPec> findAllSinisterPecToApprove();

    @Query("SELECT viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.step = 33")
    Set<ViewSinisterPec> findAllConfirmRefusedPrestation();

    @Query("SELECT viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.decision IN ('ACCEPTED', 'ACC_WITH_RESRV', 'ACC_WITH_CHANGE_STATUS') and viewSinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION')")
    Set<ViewSinisterPec> findAllRefusedPrestation();

    @Query("SELECT viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.decision IN ('ACCEPTED', 'ACC_WITH_RESRV', 'ACC_WITH_CHANGE_STATUS') and viewSinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION')")
    Set<ViewSinisterPec> findAllCanceledPrestation();

    @Query("SELECT distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.step = 2")
    Set<ViewSinisterPec> findAllPecsDemand();

    @Query("SELECT distinct viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.step = 2")
    Page<ViewSinisterPec> findAllPecsDemand(Pageable pageable);

    @Query("SELECT count(viewSinisterPec) from ViewSinisterPec viewSinisterPec where viewSinisterPec.step = 2")
    Long countAllDemandPecsWithFilter(@Param("filter") String filter);

    @Query("SELECT count(viewSinisterPec) from ViewSinisterPec viewSinisterPec where viewSinisterPec.step = 2")
    Long countAllDemandPecs();

    @Query("SELECT viewSinisterPec from ViewSinisterPec viewSinisterPec")
    Set<ViewSinisterPec> findAllSinisterPecForDerogation();

    @Query("SELECT viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.generatedLetter = false and viewSinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION') and viewSinisterPec.immatriculationTier IS NOT NULL  and (viewSinisterPec.modeLabel IN ('IDA', 'Connexe', 'HIDA  <  7000', 'HIDA  >  7000') OR viewSinisterPec.posGaLabel IN ('Recours'))")
	Set<ViewSinisterPec> findAllPrestationPECForIdaOuverture();
    
    @Query("SELECT viewSinisterPec from ViewSinisterPec viewSinisterPec where (viewSinisterPec.step = 36 or viewSinisterPec.step = 40) ")
	Set<ViewSinisterPec> findAllSinPecForSignatureBonSortie();
    
    @Query("SELECT viewSinisterPec from ViewSinisterPec viewSinisterPec where (viewSinisterPec.decision = 'CANCELED' and viewSinisterPec.step = 9 and viewSinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION')) or (viewSinisterPec.decision = 'REFUSED' and viewSinisterPec.step= 10 and viewSinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION'))")
 	Set<ViewSinisterPec> findAllViewSinisterPecRefusedAndCanceledAndAprouveOrApprvWithModif();
    
    @Query("SELECT viewSinisterPec from ViewSinisterPec viewSinisterPec where viewSinisterPec.decision = 'REFUSED' and viewSinisterPec.step = 10 and viewSinisterPec.approvPec IN ('APPROVE', 'APPROVE_WITH_MODIFICATION')")
	Set<ViewSinisterPec> findAllViewSinisterPecRefusedAndAprouveOrApprvWithModif();
    
    

    
	
}
