package org.pallete.easelsocialservice.persistence;

import org.springframework.data.neo4j.repository.Neo4jRepository;
public interface HashtagRepository extends Neo4jRepository<Hashtag, String> {
}
