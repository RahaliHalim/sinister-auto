package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.PersonnePhysique;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PersonnePhysique entity.
 */
public interface PersonnePhysiqueSearchRepository extends ElasticsearchRepository<PersonnePhysique, Long> {
}
