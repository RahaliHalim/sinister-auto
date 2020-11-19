package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefTypeServicePack;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefTypeService entity.
 */
public interface RefTypeServicePackSearchRepository extends ElasticsearchRepository<RefTypeServicePack, Long> {
}
