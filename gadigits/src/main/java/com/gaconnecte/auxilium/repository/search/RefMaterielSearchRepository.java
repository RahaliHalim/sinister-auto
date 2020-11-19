package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefMateriel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefMateriel entity.
 */
public interface RefMaterielSearchRepository extends ElasticsearchRepository<RefMateriel, Long> {
}
