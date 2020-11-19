package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Piece;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Piece entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PieceRepository extends JpaRepository<Piece, Long> {
	@Query("select distinct piece from  Piece piece where piece.typePiece.id = :typeId")
	List<Piece> findPiecesByType(@Param("typeId") Long typeId);

	@Query("select  piece from  Piece piece where piece.reference = :reference and piece.typePiece.id = :typeId")
	Piece findPiece(@Param("reference") String reference, @Param("typeId") Long typeId);
}
