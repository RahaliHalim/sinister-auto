package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Statement;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Statement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {
    @Query(value="SELECT count(id) + 1 FROM app_statement WHERE extract(year from creation_date) = :year", nativeQuery = true)
    Integer findCountStatement(@Param("year") Integer year);

    @Query(value="select count(*) from app_statement where name = :pdfName and step = 2 and tug_id = :tugId", nativeQuery = true)
    Integer findCountValidStatement(@Param("tugId") Long tug_id, @Param("pdfName") String pdfName);

    List<Statement> findAllByStepNotOrderByCreationDateDesc(Integer step);
    
}
