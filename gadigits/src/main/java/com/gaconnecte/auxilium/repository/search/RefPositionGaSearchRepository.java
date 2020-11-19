package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefPositionGa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefPositionGa entity.
 */
public interface RefPositionGaSearchRepository extends ElasticsearchRepository<RefPositionGa, Long> {
}
