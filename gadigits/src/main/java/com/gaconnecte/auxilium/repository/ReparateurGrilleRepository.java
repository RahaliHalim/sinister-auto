package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.ReparateurGrille;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the ReparateurGrille entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReparateurGrilleRepository extends JpaRepository<ReparateurGrille,Long> {

    @Query("select grille_reparateur from ReparateurGrille grille_reparateur where grille_reparateur.grille.id =:grilleId and grille_reparateur.reparateur.id =:reparateurId")
	ReparateurGrille findByGrilleAndReparateur(@Param("grilleId") Long grilleId, @Param("reparateurId") Long reparateurId);

    @Query("select grille_reparateur from ReparateurGrille grille_reparateur where grille_reparateur.reparateur.id =:reparateurId")
	List <ReparateurGrille> findByReparateur(@Param("reparateurId") Long reparateurId);

    @Query("select rg from ReparateurGrille  rg where rg.reparateur.id =:id")
   List<ReparateurGrille> findGrillebyReparateur(@Param("id") Long id);

   @Query("select grille_reparateur from ReparateurGrille grille_reparateur where grille_reparateur.refTypeIntervention.id =:refTypeInterventionId and grille_reparateur.reparateur.id =:reparateurId")
	List<ReparateurGrille> findByTypeInterventionAndReparateur(@Param("refTypeInterventionId") Long refTypeInterventionId, @Param("reparateurId") Long reparateurId);
   
   /**
    * Find by Type Intervention
    * @param refTypeInterventionId
    * @return
    */
   
   @Query("select grille_reparateur from ReparateurGrille grille_reparateur where grille_reparateur.refTypeIntervention.id =:refTypeInterventionId ")
	ReparateurGrille findByTypeIntervention(@Param("refTypeInterventionId") Long refTypeInterventionId);

}
