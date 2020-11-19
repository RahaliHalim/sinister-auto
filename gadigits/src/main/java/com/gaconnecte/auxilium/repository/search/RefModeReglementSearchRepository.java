package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefModeReglement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefModeReglement entity.
 */
public interface RefModeReglementSearchRepository extends ElasticsearchRepository<RefModeReglement, Long> {
}
