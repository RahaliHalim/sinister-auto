package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RaisonAssistance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RaisonAssistance entity.
 */
public interface RaisonAssistanceSearchRepository extends ElasticsearchRepository<RaisonAssistance, Long> {
}
