package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.DesignationNatureContrat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DesignationNatureContrat entity.
 */
public interface DesignationNatureContratSearchRepository extends ElasticsearchRepository<DesignationNatureContrat, Long> {
}
