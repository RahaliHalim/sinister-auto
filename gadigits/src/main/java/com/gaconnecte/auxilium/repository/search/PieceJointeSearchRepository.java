package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.PieceJointe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PieceJointe entity.
 */
public interface PieceJointeSearchRepository extends ElasticsearchRepository<PieceJointe, Long> {
}
