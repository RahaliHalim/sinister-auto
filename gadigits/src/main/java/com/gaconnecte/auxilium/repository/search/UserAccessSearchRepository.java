package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.UserAccess;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UserAccess entity.
 */
public interface UserAccessSearchRepository extends ElasticsearchRepository<UserAccess, Long> {
}
