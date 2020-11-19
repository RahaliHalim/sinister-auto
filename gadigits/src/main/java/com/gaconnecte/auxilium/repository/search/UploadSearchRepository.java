package com.gaconnecte.auxilium.repository.search;
import com.gaconnecte.auxilium.domain.Upload;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Upload entity.
 */
public interface UploadSearchRepository extends ElasticsearchRepository<Upload, Long> {
}