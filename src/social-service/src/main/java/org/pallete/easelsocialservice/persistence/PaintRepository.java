package org.pallete.easelsocialservice.persistence;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PaintRepository extends Neo4jRepository<Paint, String> {
}
