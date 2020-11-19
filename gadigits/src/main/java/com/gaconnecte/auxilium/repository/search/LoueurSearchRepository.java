package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Loueur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Loueur entity.
 */
public interface LoueurSearchRepository extends ElasticsearchRepository<Loueur, Long> {
}
