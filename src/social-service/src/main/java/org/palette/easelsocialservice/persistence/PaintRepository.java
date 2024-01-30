package org.palette.easelsocialservice.persistence;

import org.palette.easelsocialservice.persistence.domain.Paint;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaintRepository extends Neo4jRepository<Paint, Long> {
    Optional<Paint> findByPid(Long pid);

    @Query("MATCH path = (a:Paint)-[:REPLIES*]->(b:Paint) " +
            "WHERE a.pid = $pid " +
            "WITH [node in nodes(path) WHERE node:Paint] AS intermediateNodes " +
            "UNWIND intermediateNodes AS intermediateNode " +
            "MATCH (startNode:Paint)<-[r]->(nextNode) " +
            "WHERE startNode = intermediateNode AND type(r) <> 'REPLIES' AND type(r) <> 'REPAINTS' " +
            "RETURN startNode, r, nextNode")
    List<Paint> findAllBeforePaintByPid(@Param("pid")Long pid);

    @Query("MATCH (a:Paint)<-[:REPLIES]-(b:Paint)" +
            "WHERE a.pid = $pid " +
            "WITH b " +
            "MATCH (b)<-[r]->(neighbors) " +
            "WHERE NOT type(r) = 'REPLIES' " +
            "RETURN b, r, neighbors")
    List<Paint> findAllAfterPaintByPid(@Param("pid")Long pid);

    @Query("MATCH (a:Paint)<-[:REPLIES*]-(b:Paint)" +
            "WHERE a.pid = $pid " +
            "WITH b " +
            "MATCH (b)<-[r]->(neighbors) " +
            "WHERE NOT type(r) = 'REPLIES' " +
            "RETURN b, r, neighbors")
    List<Paint> findAllAfterPaintsByPid(@Param("pid")Long pid);

}
