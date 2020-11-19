package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.DesignationUsageContrat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DesignationUsageContrat entity.
 */
public interface DesignationUsageContratSearchRepository extends ElasticsearchRepository<DesignationUsageContrat, Long> {
}
