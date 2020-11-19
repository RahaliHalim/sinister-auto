package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Journal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {

	@Query("select distinct journal from Journal journal left join fetch journal.motifs")
	List<Journal> findAllWithEagerRelationships();

        @Query("select journal from Journal journal left join fetch journal.motifs where journal.id =:id")
	Journal findOneWithEagerRelationships(@Param("id") Long id);

	@Query("select journal from Journal journal where journal.refRemorqueur.id =:remorqueurId and journal.id="
			+ "(select max(journal.id) from Journal journal where journal.refRemorqueur.id =:remorqueurId)")
	Journal findLastJournalByRemorqueur(@Param("remorqueurId") Long remorqueurId);

	@Query("select journal from Journal journal where journal.reparateur.id =:reparateurId and journal.id="
			+ "(select max(journal.id) from Journal journal where journal.reparateur.id =:reparateurId)")
	Journal findLastJournalByReparateur(@Param("reparateurId") Long reparateurId);

	@Query("select journal from Journal journal where journal.prestation.id =:prestationId and journal.id="
			+ "(select max(journal.id) from Journal journal where journal.prestation.id =:prestationId)")
	Journal findLastJournalByPrestationPec(@Param("prestationId") Long prestationId);

	/*@Query("select journal from Journal journal where journal.quotation.id =:id and journal.id="
			+ "(select max(journal.id) from Journal journal where journal.quotation.id =:id)")
	Journal findLastJournalByQuotation(@Param("id") Long id);*/
}
