package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.FactureDetailsMo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FactureDetailsMo entity.
 */
public interface FactureDetailsMoSearchRepository extends ElasticsearchRepository<FactureDetailsMo, Long> {
}
