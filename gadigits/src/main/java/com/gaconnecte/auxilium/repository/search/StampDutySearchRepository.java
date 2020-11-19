package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.StampDuty;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StampDuty entity.
 */
public interface StampDutySearchRepository extends ElasticsearchRepository<StampDuty, Long> {
}
