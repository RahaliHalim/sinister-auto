package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.SysActionUtilisateur;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the SysActionUtilisateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysActionUtilisateurRepository extends JpaRepository<SysActionUtilisateur,Long> {

     @Query("select distinct sys_action_utilisateur from SysActionUtilisateur sys_action_utilisateur left join fetch sys_action_utilisateur.motifs")
    List<SysActionUtilisateur> findAllWithEagerRelationships();

    @Query("select sys_action_utilisateur from SysActionUtilisateur sys_action_utilisateur left join fetch sys_action_utilisateur.motifs where sys_action_utilisateur.id =:id")
    SysActionUtilisateur findOneWithEagerRelationships(@Param("id") Long id);

     @Query("select sys_action_utilisateur from SysActionUtilisateur sys_action_utilisateur where sys_action_utilisateur.nom =:nom")
    SysActionUtilisateur findActionByName(@Param("nom") String nom);

    


    
}
