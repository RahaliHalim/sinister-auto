package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefTypePieces;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefTypePieces entity.
 */
public interface RefTypePiecesSearchRepository extends ElasticsearchRepository<RefTypePieces, Long> {
}
