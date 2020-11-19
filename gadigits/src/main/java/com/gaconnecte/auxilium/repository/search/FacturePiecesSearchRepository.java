package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.FacturePieces;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FacturePieces entity.
 */
public interface FacturePiecesSearchRepository extends ElasticsearchRepository<FacturePieces, Long> {
}
