package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Piece;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Piece entity.
 */
public interface PieceSearchRepository extends ElasticsearchRepository<Piece, Long> {
}
