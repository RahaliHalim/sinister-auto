package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefModeGestion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefModeGestion entity.
 */
public interface RefModeGestionSearchRepository extends ElasticsearchRepository<RefModeGestion, Long> {
}
