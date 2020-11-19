package com.gaconnecte.auxilium.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.gaconnecte.auxilium.domain.HistoryPec;


@Repository
public interface HistoryPecRepository extends JpaRepository<HistoryPec,Long> {
	
	@Query("select history from HistoryPec history where history.typeDevis IS NOT NULL and history.entityId=:sinisterPecId and history.entityName = 'Quotation'")
	List<HistoryPec> findListDevisByHistory(@Param("sinisterPecId") Long sinisterPecId);	
	
	@Query("select history from HistoryPec history where history.typeAccord IS NOT NULL and history.entityId=:sinisterPecId and history.entityName = 'Appp'")
	List<HistoryPec> findListAccordByHistory(@Param("sinisterPecId") Long sinisterPecId);
	
	
	@Query("select history from HistoryPec history where history.entityId=:entityId and history.entityName=:entityName and history.operationName=:operation")
	HistoryPec findHistoryByEntityAndAssistance(@Param("entityId") Long entityId, @Param("entityName") String entityName, @Param("operation") String operation);

	
	@Query("select history from HistoryPec history where history.typeAccord IS NOT NULL and history.entityId=:sinisterPecId and history.entityName = 'Appp' and history.typeAccord = 'Accord complémentaire' ")
	List<HistoryPec> findListAccordComplByHistory(@Param("sinisterPecId") Long sinisterPecId);
	
	@Query("select history from HistoryPec history where history.entityId=:entityId and history.entityName=:entityName")
	List<HistoryPec> findHistoryByEntity(@Param("entityId") Long entityId, @Param("entityName") String entityName);
	
	@Query("select history from HistoryPec history where  history.entityId=:entityId and history.entityName=:entityName and history.action.id=:actionId  and  history.id = (select MAX(id)  from History history where  history.entityId=:entityId and history.entityName=:entityName and history.action.id=:actionId)")
	HistoryPec findHistoryPecQuotationByAction(@Param("actionId") Long actionId,@Param("entityId") Long entityId, @Param("entityName") String entityName);
}
