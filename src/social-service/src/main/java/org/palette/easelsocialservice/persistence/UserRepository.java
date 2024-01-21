package org.palette.easelsocialservice.persistence;

import org.palette.easelsocialservice.persistence.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface UserRepository extends Neo4jRepository<User, Long> {
    Optional<User> findByUid(Long userId);
}
