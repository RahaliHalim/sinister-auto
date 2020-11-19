package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Contact;
import com.gaconnecte.auxilium.domain.ContratAssurance;
import com.gaconnecte.auxilium.domain.VehiculeAssure;
import java.time.LocalDate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Spring Data JPA repository for the ContratAssurance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContratAssuranceRepository extends JpaRepository<ContratAssurance, Long> {

    @Query("SELECT c FROM ContratAssurance c left join c.vehicules vehicules where c.isDelete = false and vehicules.immatriculationVehicule=:immatriculationVehicule"
            + "  and  c.finValidite =(select max(cc.finValidite )  FROM ContratAssurance cc left join cc.vehicules vehicules where vehicules.immatriculationVehicule=:immatriculationVehicule )")
    ContratAssurance findByImmatriculation(@Param("immatriculationVehicule") String immatriculationVehicule);

    @Query("SELECT contrat_assurance from ContratAssurance contrat_assurance ")
    Page<ContratAssurance> findContratAssurancesByVehicule(Pageable pageable, @Param("vehiculeId") Long vehiculeId);

    @Query("select count(*) FROM ContratAssurance contrat_assurance where contrat_assurance.client.id=:compagnieId")
    Long countContratByCompagnie(@Param("compagnieId") Long compagnieId);

    @Query("select distinct contrat_assurance from ContratAssurance contrat_assurance")
    List<ContratAssurance> findAllWithEagerRelationships();

    @Query("select contrat_assurance from ContratAssurance contrat_assurance where contrat_assurance.id =:id")
    ContratAssurance findOneWithEagerRelationships(@Param("id") Long id);

    @Query("SELECT distinct contrat_assurance from ContratAssurance contrat_assurance where contrat_assurance.isDelete= false")
    List<ContratAssurance> findAllContrat();

    @Query("select contratAssurance from ContratAssurance contratAssurance ")
    ContratAssurance findOneByIdVehicule(@Param("vehiculeId") Long id);

    @Query("SELECT v FROM VehiculeAssure v where v.immatriculationVehicule = :immatriculationVehicule and v.contrat.isDelete = false and v.contrat.finValidite >= :currentDate")
    VehiculeAssure findByImmatriculationAndContractDate(@Param("immatriculationVehicule") String immatriculationVehicule, @Param("currentDate") LocalDate endDate);

    @Query("SELECT v FROM VehiculeAssure v where v.immatriculationVehicule = :immatriculationVehicule and v.contrat.isDelete = false order by v.contrat.finValidite desc")
    Set<VehiculeAssure> findByRegistrationNumber(@Param("immatriculationVehicule") String immatriculationVehicule);
    
    ContratAssurance findByNumeroContrat(String pnumber);
    
    @Query("SELECT v FROM VehiculeAssure v where v.immatriculationVehicule = :immatriculationVehicule and v.contrat.isDelete = false ")
    VehiculeAssure findByImmatriculationAndIgnoreContractDate(@Param("immatriculationVehicule") String immatriculationVehicule);
    
    @Query("SELECT c FROM ContratAssurance c where c.numeroContrat = :numeroContrat and c.isDelete = false ")
    ContratAssurance findByContractNum(@Param("numeroContrat") String numeroContrat);
    
    @Query("SELECT c FROM ContratAssurance c LEFT JOIN FETCH c.vehicules where c.id = :id")
    ContratAssurance findOneWithVehicles(@Param("id") Long id);
}
