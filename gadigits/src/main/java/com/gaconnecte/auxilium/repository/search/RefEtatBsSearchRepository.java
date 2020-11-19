package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefEtatBs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefEtatBs entity.
 */
public interface RefEtatBsSearchRepository extends ElasticsearchRepository<RefEtatBs, Long> {
}
