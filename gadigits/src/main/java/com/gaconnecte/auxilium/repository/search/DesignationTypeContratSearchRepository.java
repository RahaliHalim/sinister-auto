package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.DesignationTypeContrat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DesignationTypeContrat entity.
 */
public interface DesignationTypeContratSearchRepository extends ElasticsearchRepository<DesignationTypeContrat, Long> {
}
