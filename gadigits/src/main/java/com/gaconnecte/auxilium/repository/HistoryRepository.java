package com.gaconnecte.auxilium.repository;
import com.gaconnecte.auxilium.domain.History;
import com.gaconnecte.auxilium.domain.RefRemorqueur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Spring Data JPA repository for the History entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoryRepository extends JpaRepository<History,Long> {
	
	@Query("select history from History history where  history.entityId=:entityId and history.entityName=:entityName and history.action.id=:actionId  and  history.id = (select MAX(id)  from History history where  history.entityId=:entityId and history.entityName=:entityName and history.action.id=:actionId)")
	History findHistoryQuotationByAction(@Param("actionId") Long actionId,@Param("entityId") Long entityId, @Param("entityName") String entityName);
	@Query("select history from History history where history.entityId=:entityId and history.entityName=:entityName")
	List<History> findHistoryByEntity(@Param("entityId") Long entityId, @Param("entityName") String entityName);
	@Query("select history from History history where history.entityId=:entityId and history.entityName=:entityName and history.operationName=:operation")
	History findHistoryByEntityAndAssistance(@Param("entityId") Long entityId, @Param("entityName") String entityName, @Param("operation") String operation);
	@Query("select history from History history where history.typeDevis IS NOT NULL and history.entityId=:sinisterPecId and history.entityName = 'Quotation'")
	List<History> findListDevisByHistory(@Param("sinisterPecId") Long entityId);
	@Query("select history from History history where history.typeAccord IS NOT NULL and history.entityId=:sinisterPecId and history.entityName = 'Appp'")
	List<History> findListAccordByHistory(@Param("sinisterPecId") Long entityId);
	@Query("select history from History history where history.typeAccord IS NOT NULL and history.entityId=:sinisterPecId and history.entityName = 'Appp' and history.typeAccord = 'Accord complémentaire' ")
	List<History> findListAccordComplByHistory(@Param("sinisterPecId") Long entityId);
}
