package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefFractionnement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefFractionnement entity.
 */
public interface RefFractionnementSearchRepository extends ElasticsearchRepository<RefFractionnement, Long> {
}
