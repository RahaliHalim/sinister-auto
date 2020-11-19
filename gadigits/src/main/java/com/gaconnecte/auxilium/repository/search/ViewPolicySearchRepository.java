package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.view.ViewPolicy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ViewPolicy entity.
 */
public interface ViewPolicySearchRepository extends ElasticsearchRepository<ViewPolicy, Long> {
}
