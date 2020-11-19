package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.ContratAssurance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ContratAssurance entity.
 */
public interface ContratAssuranceSearchRepository extends ElasticsearchRepository<ContratAssurance, Long> {
}
