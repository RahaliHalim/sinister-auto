package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefModeReglement;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Reglement entity.
 */
public interface ReglementSearchRepository extends ElasticsearchRepository<RefModeReglement, Long> {
}
