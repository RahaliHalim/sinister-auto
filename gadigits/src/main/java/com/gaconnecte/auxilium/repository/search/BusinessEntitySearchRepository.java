package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.BusinessEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BusinessEntity entity.
 */
public interface BusinessEntitySearchRepository extends ElasticsearchRepository<BusinessEntity, Long> {
}
