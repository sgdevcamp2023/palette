package org.pallete.easelsocialservice.persistence;

import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.schema.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PaintIdGenerator implements IdGenerator<String> {

    private final Neo4jClient neo4jClient;

    public PaintIdGenerator(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    @Override
    public synchronized String generateId(String primaryLabel, Object entity) {
        Optional<Long> maxIdOptional = neo4jClient.query("MATCH (n:" + primaryLabel + ") RETURN n.pid as pid ORDER BY n.pid DESC LIMIT 1")
                .fetchAs(Long.class).mappedBy((typeSystem, record) -> Long.parseLong(record.get("pid").asString())).one();
        return maxIdOptional.map(aLong -> String.valueOf(aLong + 1)).orElse("0");
    }
}
