package org.palette.easelsocialservice.persistence;

import org.palette.easelsocialservice.persistence.domain.Hashtag;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HashtagRepository extends Neo4jRepository<Hashtag, String> {

    @Query("UNWIND $tags AS tag MERGE (h:Hashtag {tag: tag}) ON CREATE SET h.created = timestamp() RETURN h")
    List<Hashtag> createHashtags(@Param("tags") List<String> tags);
}
