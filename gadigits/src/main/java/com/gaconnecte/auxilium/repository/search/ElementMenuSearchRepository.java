package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.ElementMenu;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ElementMenu entity.
 */
public interface ElementMenuSearchRepository extends ElasticsearchRepository<ElementMenu, Long> {
}
