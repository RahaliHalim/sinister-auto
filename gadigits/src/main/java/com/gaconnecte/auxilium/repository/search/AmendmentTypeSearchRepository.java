package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.AmendmentType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AmendmentType entity.
 */
public interface AmendmentTypeSearchRepository extends ElasticsearchRepository<AmendmentType, Long> {
}
