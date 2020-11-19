package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.PieceService;
import com.gaconnecte.auxilium.domain.Piece;
import com.gaconnecte.auxilium.repository.PieceRepository;
import com.gaconnecte.auxilium.repository.search.PieceSearchRepository;
import com.gaconnecte.auxilium.service.dto.PieceDTO;
import com.gaconnecte.auxilium.service.mapper.PieceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.elasticsearch.index.query.QueryBuilders.*;
import java.util.List;

/**
 * Service Implementation for managing Piece.
 */
@Service
@Transactional
public class PieceServiceImpl implements PieceService {

	private final Logger log = LoggerFactory.getLogger(PieceServiceImpl.class);

	private final PieceRepository pieceRepository;

	private final PieceMapper pieceMapper;

	private final PieceSearchRepository pieceSearchRepository;

	public PieceServiceImpl(PieceRepository pieceRepository, PieceMapper pieceMapper,
			PieceSearchRepository pieceSearchRepository) {
		this.pieceRepository = pieceRepository;
		this.pieceMapper = pieceMapper;
		this.pieceSearchRepository = pieceSearchRepository;
	}

	/**
	 * Save a piece.
	 *
	 * @param pieceDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public PieceDTO save(PieceDTO pieceDTO) {
		log.debug("Request to save Piece : {}", pieceDTO);
		Piece piece = pieceMapper.toEntity(pieceDTO);
		piece = pieceRepository.save(piece);
		PieceDTO result = pieceMapper.toDto(piece);
		pieceSearchRepository.save(piece);
		return result;
	}

	/**
	 * Get all the pieces.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<PieceDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Pieces");
		return pieceRepository.findAll(pageable).map(pieceMapper::toDto);
	}

	/**
	 * Get one piece by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public PieceDTO findOne(Long id) {
		log.debug("Request to get Piece : {}", id);
		Piece piece = pieceRepository.findOne(id);
		return pieceMapper.toDto(piece);
	}

	/**
	 * Delete the piece by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Piece : {}", id);
		pieceRepository.delete(id);
		pieceSearchRepository.delete(id);
	}

	/**
	 * Search for the piece corresponding to the query.
	 *
	 * @param query    the query of the search
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<PieceDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of Pieces for query {}", query);
		Page<Piece> result = pieceSearchRepository.search(queryStringQuery(query), pageable);
		return result.map(pieceMapper::toDto);
	}

	@Override
	public List<PieceDTO> findPieceByType(Long id) {
		log.debug("Request to get  all Pieces", id);
		List<Piece> pieces = pieceRepository.findPiecesByType(id);
		return pieceMapper.toDto(pieces);
	}

	@Override
	public Piece findpiece(String reference, Long typeId) {
		log.debug("Request to get Piece : {}", reference);
		Piece piece = pieceRepository.findPiece(reference, typeId);
		return piece;
	}

}
