package com.gaconnecte.auxilium.repository.prm;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import com.gaconnecte.auxilium.domain.prm.Rules;
@Repository
public interface RulesRepository extends JpaRepository<Rules,Long> {

    @Query("SELECT rules FROM Rules rules")
    Set<Rules> findAllRules();
    @Query("SELECT rules FROM Rules rules WHERE code =:code")
    Set<Rules> findByCode(@Param("code") String code);
}
