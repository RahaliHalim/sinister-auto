package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.ServiceAssurance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ServiceAssurance entity.
 */
public interface ServiceAssuranceSearchRepository extends ElasticsearchRepository<ServiceAssurance, Long> {
}
