package org.palette.easelsocialservice.persistence;

import org.palette.easelsocialservice.persistence.domain.Media;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface MediaRepository extends Neo4jRepository<Media, String> {
}
