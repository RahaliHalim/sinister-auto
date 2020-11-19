package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Functionality;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Functionality entity.
 */
public interface FunctionalitySearchRepository extends ElasticsearchRepository<Functionality, Long> {
}
