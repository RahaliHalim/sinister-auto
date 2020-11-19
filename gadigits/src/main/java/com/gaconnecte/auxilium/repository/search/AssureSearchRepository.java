package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Assure;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Assure entity.
 */
public interface AssureSearchRepository extends ElasticsearchRepository<Assure, Long> {
}
