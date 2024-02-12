package org.palette.easelsearchservice.persistence;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchRepository extends ElasticsearchRepository<SearchPaint, Long> {

}
