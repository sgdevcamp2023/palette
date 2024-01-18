package org.palette.easelsocialservice.persistence;

import org.palette.easelsocialservice.persistence.domain.Hashtag;
import org.springframework.data.neo4j.repository.Neo4jRepository;
public interface HashtagRepository extends Neo4jRepository<Hashtag, String> {
}
