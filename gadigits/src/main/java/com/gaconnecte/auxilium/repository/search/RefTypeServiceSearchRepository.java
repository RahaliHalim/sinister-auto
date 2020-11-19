package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefTypeService;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefTypeService entity.
 */
public interface RefTypeServiceSearchRepository extends ElasticsearchRepository<RefTypeService, Long> {
}
