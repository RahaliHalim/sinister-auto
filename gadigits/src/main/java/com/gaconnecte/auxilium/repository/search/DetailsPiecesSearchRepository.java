package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.DetailsPieces;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DetailsPieces entity.
 */
public interface DetailsPiecesSearchRepository extends ElasticsearchRepository<DetailsPieces, Long> {
}
