package org.pallete.easelsocialservice.persistence;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface PaintRepository extends Neo4jRepository<Paint, String> {
    Optional<Paint> findByPid(String pid);

}
