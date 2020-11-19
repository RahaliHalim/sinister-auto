package com.gaconnecte.auxilium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import com.gaconnecte.auxilium.domain.HistoryPartnerRules;

@SuppressWarnings("unused")
@Repository
public interface HistoryPartnerRulesRepository extends JpaRepository<HistoryPartnerRules,Long> {

}
