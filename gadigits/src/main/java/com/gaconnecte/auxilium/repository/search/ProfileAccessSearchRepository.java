package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.ProfileAccess;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProfileAccess entity.
 */
public interface ProfileAccessSearchRepository extends ElasticsearchRepository<ProfileAccess, Long> {
}
