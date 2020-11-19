package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefTypeIntervention;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefTypeIntervention entity.
 */
public interface RefTypeInterventionSearchRepository extends ElasticsearchRepository<RefTypeIntervention, Long> {
}
