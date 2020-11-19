package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.ValidationDevis;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ValidationDevis entity.
 */
public interface ValidationDevisSearchRepository extends ElasticsearchRepository<ValidationDevis, Long> {
}
