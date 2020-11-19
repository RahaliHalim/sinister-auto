package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.AgentGeneral;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AgentGeneral entity.
 */
public interface AgentGeneralSearchRepository extends ElasticsearchRepository<AgentGeneral, Long> {
}
