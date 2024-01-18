package org.pallete.easelsocialservice.persistence;

import org.pallete.easelsocialservice.persistence.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepository extends Neo4jRepository<User, Long> {
}
