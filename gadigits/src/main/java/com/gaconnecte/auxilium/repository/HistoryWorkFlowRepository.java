package com.gaconnecte.auxilium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.History;
import com.gaconnecte.auxilium.domain.HistoryWorkFlow;

@SuppressWarnings("unused")
@Repository
public interface HistoryWorkFlowRepository extends JpaRepository<HistoryWorkFlow,Long> {

}
