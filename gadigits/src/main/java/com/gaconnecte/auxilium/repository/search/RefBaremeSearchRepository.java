package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefBareme;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefBareme entity.
 */
public interface RefBaremeSearchRepository extends ElasticsearchRepository<RefBareme, Long> {
}
