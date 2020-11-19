package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefNatureExpertise;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefNatureExpertise entity.
 */
public interface RefNatureExpertiseSearchRepository extends ElasticsearchRepository<RefNatureExpertise, Long> {
}
