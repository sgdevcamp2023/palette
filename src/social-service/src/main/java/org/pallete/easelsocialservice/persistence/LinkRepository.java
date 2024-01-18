package org.pallete.easelsocialservice.persistence;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface LinkRepository extends Neo4jRepository<Link, String> {
}
