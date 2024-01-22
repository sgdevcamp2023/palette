package org.palette.easelsocialservice.persistence;

import org.palette.easelsocialservice.persistence.domain.Link;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface LinkRepository extends Neo4jRepository<Link, String> {
}
