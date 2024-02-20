package org.palette.easelsearchservice.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchUserRepository extends ElasticsearchRepository<SearchUser, Long> {

    Page<SearchUser> findByIntroduceContainingOrNicknameContaining(String keyword, PageRequest of);
}
