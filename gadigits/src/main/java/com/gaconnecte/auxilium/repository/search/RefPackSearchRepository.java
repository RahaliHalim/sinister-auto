package com.gaconnecte.auxilium.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.gaconnecte.auxilium.domain.referential.RefPack;

/**
 * Spring Data Elasticsearch repository for the RefPack entity.
 */
public interface RefPackSearchRepository extends ElasticsearchRepository<RefPack, Long> {
}