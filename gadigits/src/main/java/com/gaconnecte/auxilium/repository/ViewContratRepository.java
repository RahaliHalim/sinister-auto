package com.gaconnecte.auxilium.repository;
import com.gaconnecte.auxilium.domain.view.ViewContrat;
import com.gaconnecte.auxilium.domain.view.ViewPolicy;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface ViewContratRepository extends JpaRepository<ViewContrat, Long>{

	 @Query("SELECT sp from ViewContrat sp WHERE sp.dateCreation <= :endDate AND sp.dateCreation >= :startDate")
	    Set<ViewContrat> findAllByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


		@Query("SELECT sp from ViewContrat sp WHERE sp.zoneId = :zoneF AND sp.dateCreation <= :endDate AND sp.dateCreation >= :startDate")
	    Set<ViewContrat> findAllByZone(@Param("zoneF") Long zoneF, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

		@Query("SELECT sp from ViewContrat sp WHERE sp.packId = :nPack AND sp.dateCreation <= :endDate AND sp.dateCreation >= :startDate")
	    Set<ViewContrat> findAllByPack(@Param("nPack") Long nPack, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		@Query("SELECT sp from ViewContrat sp WHERE sp.companyId = :compagnie AND sp.dateCreation <= :endDate AND sp.dateCreation >= :startDate")
	    Set<ViewContrat> findAllByCompagnie(@Param("compagnie") Long compagnie, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		
		

		@Query("SELECT sp from ViewContrat sp WHERE sp.companyId = :compagnie AND sp.zoneId = :zoneF AND sp.dateCreation <= :endDate AND sp.dateCreation >= :startDate")
	    Set<ViewContrat> findAllByAgentAndZone(@Param("compagnie") Long compagnie,@Param("zoneF") Long zoneF ,@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
		

		@Query("SELECT sp from ViewContrat sp WHERE sp.packId = :nPack  AND sp.zoneId = :zoneF AND sp.dateCreation <= :endDate AND sp.dateCreation >= :startDate")
	    Set<ViewContrat> findAllByPackAndZone(@Param("nPack") Long nPack, @Param("zoneF") Long zoneF ,@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

		@Query("SELECT sp from ViewContrat sp WHERE sp.packId = :nPack  AND sp.zoneId = :zoneF AND sp.companyId = :compagnie AND sp.dateCreation <= :endDate AND sp.dateCreation >= :startDate")
	    Set<ViewContrat> findAllByAll(@Param("nPack") Long nPack, @Param("zoneF") Long zoneF, @Param("compagnie") Long compagnie ,@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

		@Query("SELECT sp FROM ViewContrat sp WHERE lower(sp.nomAgentAssurance) like %:filter% OR  lower(sp.usageLabel)  like %:filter%"
	            + " OR  lower(sp.naturePack)  like %:filter% OR  lower(sp.zone)  like %:filter% ")
	    Page<ViewPolicy> findAllWithFilter(@Param("filter") String filter, Pageable pageable);

	    @Query("SELECT count(sp) FROM ViewContrat sp WHERE lower(sp.nomAgentAssurance) like %:filter% OR  lower(sp.usageLabel)  like %:filter%"
	            + " OR  lower(sp.naturePack)  like %:filter% OR  lower(sp.zone)  like %:filter% ")
	    Long countAllWithFilter(@Param("filter") String filter);
		 @Query("SELECT sp from ViewContrat sp WHERE sp.dateCreation <= :endDate AND sp.dateCreation >= :startDate")
		    Page<ViewContrat> findAllByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);


			@Query("SELECT sp from ViewContrat sp WHERE sp.zoneId = :zoneF AND sp.dateCreation <= :endDate AND sp.dateCreation >= :startDate")
			Page<ViewContrat> findAllByZone(@Param("zoneF") Long zoneF, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

			@Query("SELECT sp from ViewContrat sp WHERE sp.packId = :nPack AND sp.dateCreation <= :endDate AND sp.dateCreation >= :startDate")
		    Page<ViewContrat> findAllByPack(@Param("nPack") Long nPack, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);
			
			@Query("SELECT sp from ViewContrat sp WHERE sp.companyId = :compagnie AND sp.dateCreation <= :endDate AND sp.dateCreation >= :startDate")
			Page<ViewContrat> findAllByCompagnie(@Param("compagnie") Long compagnie, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);
			
			

			@Query("SELECT sp from ViewContrat sp WHERE sp.companyId = :compagnie AND sp.zoneId = :zoneF AND sp.dateCreation <= :endDate AND sp.dateCreation >= :startDate")
			Page<ViewContrat> findAllByAgentAndZone(@Param("compagnie") Long compagnie,@Param("zoneF") Long zoneF ,@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);
			

			@Query("SELECT sp from ViewContrat sp WHERE sp.packId = :nPack  AND sp.zoneId = :zoneF AND sp.dateCreation <= :endDate AND sp.dateCreation >= :startDate")
			Page<ViewContrat> findAllByPackAndZone(@Param("nPack") Long nPack, @Param("zoneF") Long zoneF ,@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

			@Query("SELECT sp from ViewContrat sp WHERE sp.packId = :nPack  AND sp.zoneId = :zoneF AND sp.companyId = :compagnie AND sp.dateCreation <= :endDate AND sp.dateCreation >= :startDate")
			Page<ViewContrat> findAllByAll(@Param("nPack") Long nPack, @Param("zoneF") Long zoneF, @Param("compagnie") Long compagnie ,@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);


}

