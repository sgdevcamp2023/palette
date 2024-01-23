package org.palette.easelsocialservice.persistence;

import org.palette.easelsocialservice.persistence.domain.Link;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface LinkRepository extends Neo4jRepository<Link, Long> {
    @Query("MATCH (n: Link) RETURN toInteger(n.lid) as lid ORDER BY n.lid DESC LIMIT 1")
    Long getLastLid();
}
