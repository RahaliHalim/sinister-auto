package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.AvisExpertPiece;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AvisExpertPiece entity.
 */
public interface AvisExpertPieceSearchRepository extends ElasticsearchRepository<AvisExpertPiece, Long> {
}
