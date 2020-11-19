package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.AvisExpertMo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AvisExpertMo entity.
 */
public interface AvisExpertMoSearchRepository extends ElasticsearchRepository<AvisExpertMo, Long> {
}
