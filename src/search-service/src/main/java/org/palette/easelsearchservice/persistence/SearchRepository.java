package org.palette.easelsearchservice.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchRepository extends ElasticsearchRepository<SearchPaint, Long> {

    Page<SearchPaint> findByTextContaining(String keyword, PageRequest of);
}
