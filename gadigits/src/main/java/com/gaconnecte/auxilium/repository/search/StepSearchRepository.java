package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Step;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Step entity.
 */
public interface StepSearchRepository extends ElasticsearchRepository<Step, Long> {
}
