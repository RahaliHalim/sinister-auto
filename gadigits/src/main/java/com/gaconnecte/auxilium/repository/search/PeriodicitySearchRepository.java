package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Periodicity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Periodicity entity.
 */
public interface PeriodicitySearchRepository extends ElasticsearchRepository<Periodicity, Long> {
}
