package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.PersonneMorale;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PersonneMorale entity.
 */
public interface PersonneMoraleSearchRepository extends ElasticsearchRepository<PersonneMorale, Long> {
}
