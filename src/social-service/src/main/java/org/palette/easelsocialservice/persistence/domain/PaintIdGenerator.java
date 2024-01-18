package org.palette.easelsocialservice.persistence.domain;

import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.schema.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PaintIdGenerator implements IdGenerator<Long> {

    private final Neo4jClient neo4jClient;

    public PaintIdGenerator(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    @Override
    public synchronized Long generateId(String primaryLabel, Object entity) {
        Optional<Long> maxIdOptional = neo4jClient.query("MATCH (n:" + primaryLabel + ") RETURN toInteger(n.pid) as pid ORDER BY n.pid DESC LIMIT 1")
                .fetchAs(Long.class).mappedBy((typeSystem, record) -> record.get("pid").asLong()).one();
        return maxIdOptional.map(aLong -> aLong + 1).orElse(0L);
    }
}
