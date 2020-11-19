package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Prestation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Spring Data JPA repository for the Prestation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrestationRepository extends JpaRepository<Prestation,Long> {
    

    @Query("SELECT distinct prestation from Prestation prestation where prestation.isDelete= false")
	Page<Prestation> findAllPrestation(Pageable pageable);

     @Query("select distinct prestation from Prestation prestation where prestation.dossier.id =:dossierId and prestation.isDelete= false")
    Page<Prestation> findPrestationsByDossier(Pageable pageable,@Param("dossierId") Long dossierId);

    @Query("select count(*) FROM Prestation prestation join Dossier dossier on prestation.dossier= dossier.id where prestation.dossier.id=:dossierId")
	Long countPrestationByDossier(@Param("dossierId") Long dossierId);
    
    /*@Query("select distinct prestation from Prestation prestation where prestation.dossier.vehicule.id "
    		+ "IN (select ca.vehicule.id FROM ContratAssurance ca where ca.agence.compagnie.id =:idCompagnie)")
    Page<Prestation> findPrestationsByCompagnie(Pageable pageable,@Param("idCompagnie") Long idCompagnie);*/
    
}
