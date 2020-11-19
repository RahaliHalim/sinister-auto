package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefTypePj;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefTypePj entity.
 */
public interface RefTypePjSearchRepository extends ElasticsearchRepository<RefTypePj, Long> {
}
