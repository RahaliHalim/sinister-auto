package com.gaconnecte.auxilium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.ViewExtractPiece;


@Repository
public interface ViewExtractPieceRepository extends JpaRepository<ViewExtractPiece, Long> {
	

}
