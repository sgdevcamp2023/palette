package org.pallete.easelsocialservice.persistence;

import org.pallete.easelsocialservice.persistence.domain.Media;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface MediaRepository extends Neo4jRepository<Media, String> {
}
