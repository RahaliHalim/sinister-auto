package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.DesignationFractionnement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DesignationFractionnement entity.
 */
public interface DesignationFractionnementSearchRepository extends ElasticsearchRepository<DesignationFractionnement, Long> {
}
