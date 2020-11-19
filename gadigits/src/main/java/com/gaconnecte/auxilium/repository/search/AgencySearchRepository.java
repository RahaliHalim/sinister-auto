package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Agency;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Agency entity.
 */
public interface AgencySearchRepository extends ElasticsearchRepository<Agency, Long> {
}
