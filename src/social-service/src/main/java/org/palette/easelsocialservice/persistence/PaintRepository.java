package org.palette.easelsocialservice.persistence;

import org.palette.easelsocialservice.persistence.domain.Paint;
import org.palette.easelsocialservice.persistence.domain.PaintMetrics;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaintRepository extends Neo4jRepository<Paint, Long> {
    Optional<Paint> findByPid(Long pid);

    @Query("MATCH (p:Paint {pid: $pid}) SET p.views = p.views + 1")
    void updatePaintView(@Param("pid") Long pid);

    @Query(
            "MATCH path = (u:User {uid: $uid})-[:CREATES|QUOTES|REPLIES]->(p:Paint) " +
                    "WITH [node in nodes(path) WHERE node:Paint] AS intermediateNodes " +
                    "UNWIND intermediateNodes AS intermediateNode " +
                    "MATCH (startNode:Paint)<-[r]->(nextNode) " +
                    "WHERE startNode = intermediateNode AND type(r) <> 'REPLIES' AND type(r) <> 'REPAINTS' AND type(r) <> 'QUOTES' " +
                    "RETURN startNode, r, nextNode"
    )
    List<Paint> findAllCreatesQuotesRepliesByUid(@Param("uid") Long uid);

    @Query("MATCH path = (u:User {uid: $uid})-[r:MARKS]->(p:Paint) " +
            "WITH [node in nodes(path) WHERE node:Paint] AS intermediateNodes " +
            "UNWIND intermediateNodes AS intermediateNode " +
            "MATCH (startNode:Paint)<-[r]->(nextNode) " +
            "WHERE startNode = intermediateNode AND type(r) <> 'REPLIES' AND type(r) <> 'REPAINTS' AND type(r) <> 'QUOTES' " +
            "RETURN startNode, r, nextNode")
    List<Paint> findAllMarksByUid(@Param("uid") Long uid);

    @Query("MATCH path = (u:User {uid: $uid})-[r:CREATES]->(p:Paint)-[us:USES]->(n) RETURN path")
    List<Paint> findAllContainingMediaByUid(@Param("uid") Long uid);

    @Query("MATCH path = (a:Paint)-[:REPLIES*]->(b:Paint) " +
            "WHERE a.pid = $pid " +
            "WITH [node in nodes(path) WHERE node:Paint] AS intermediateNodes " +
            "UNWIND intermediateNodes AS intermediateNode " +
            "MATCH (startNode:Paint)<-[r]->(nextNode) " +
            "WHERE startNode = intermediateNode AND type(r) <> 'REPLIES' AND type(r) <> 'REPAINTS' AND type(r) <> 'QUOTES' " +
            "RETURN startNode, r, nextNode")
    List<Paint> findAllBeforePaintByPid(@Param("pid") Long pid);

    @Query("MATCH (a:Paint)<-[:REPLIES]-(b:Paint)" +
            "WHERE a.pid = $pid " +
            "WITH b " +
            "MATCH (b)<-[r]->(neighbors) " +
            "WHERE type(r) <> 'REPLIES' AND type(r) <> 'QUOTES' " +
            "RETURN b, r, neighbors")
    List<Paint> findAllAfterPaintByPid(@Param("pid") Long pid);

    @Query("MATCH path=(a:Paint)<-[:REPLIES*]-(b:Paint) " +
            "WHERE a.pid = $pid " +
            "WITH path " +
            "ORDER BY b.pid DESC " +
            "LIMIT 1 " +
            "UNWIND nodes(path) as b " +
            "MATCH (b)<-[r]->(neighbors) " +
            "WHERE type(r) <> 'REPLIES' AND type(r) <> 'REPAINTS' AND type(r) <> 'QUOTES' " +
            "RETURN b, r, neighbors")
    List<Paint> findAllAfterPaintsByPid(@Param("pid") Long pid);


    @Query("MATCH path = (u:User {uid: $uid})-[r:LIKES]->(p:Paint) " +
            "WITH [node in nodes(path) WHERE node:Paint] AS intermediateNodes " +
            "UNWIND intermediateNodes AS intermediateNode " +
            "MATCH (startNode:Paint)<-[r]->(nextNode) " +
            "WHERE startNode = intermediateNode AND type(r) <> 'REPLIES' AND type(r) <> 'REPAINTS' AND type(r) <> 'QUOTES' " +
            "RETURN startNode, r, nextNode")
    List<Paint> findAllLikingByUid(@Param("uid") Long uid);

    @Query("MATCH (a:Paint)-[r:QUOTES]->(b:Paint)<-[r2]->(c) " +
            "WHERE a.pid = $pid AND type(r2) <> 'REPLIES' AND type(r2) <> 'QUOTES' " +
            "RETURN b, r2, c")
    Paint findQuotePaintByPid(@Param("pid") Long pid);

    @Query("MATCH (p:Paint {pid: $pid}) " +
            "OPTIONAL MATCH (p)<-[r:REPLIES]-(reply) " +
            "OPTIONAL MATCH (p)<-[l:LIKES]-(like) " +
            "OPTIONAL MATCH (p)<-[rep:REPAINTS]-(repaint) " +
            "OPTIONAL MATCH (u:User {uid: $uid}) " +
            "RETURN count(reply) AS replyCount, count(like) AS likeCount, count(repaint) AS repaintCount, " +
            "exists((p)<-[:LIKES]-(u)) AS like, exists((p)<-[:REPAINTS]-(u)) AS repainted, exists((p)<-[:REPAINTS]-(u)) AS marked")
    PaintMetrics findMetricsByPidAndUid(@Param("uid") Long uid, @Param("pid") Long pid);

    @Query("MATCH quotings = (a:Paint)<-[:QUOTES]-(b:Paint) " +
            "WHERE a.pid = $pid " +
            "WITH [node in nodes(quotings) WHERE node:Paint] AS intermediateNodes " +
            "UNWIND intermediateNodes AS intermediateNode " +
            "MATCH (startNode:Paint)<-[r]->(nextNode) " +
            "WHERE startNode.pid <> $pid AND startNode = intermediateNode AND type(r) <> 'REPLIES' AND type(r) <> 'REPAINTS' AND type(r) <> 'QUOTES' " +
            "RETURN startNode, r, nextNode")
    List<Paint> findAllQuotePaintByPid(@Param("pid") Long pid);

    @Query("RETURN EXISTS((:User {uid: $uid}) -[:REPAINTS]-> (:Paint {pid: $pid}))")
    boolean existsRepaintsByUidAndPid(@Param("uid") Long uid, @Param("pid") Long pid);

    @Query("MATCH (p:Paint) WHERE p.pid IN $paintIds " +
            "WITH p " +
            "OPTIONAL MATCH (p)<-[r]->(neighbors) " +
            "WHERE type(r) <> 'REPLIES' AND type(r) <> 'REPAINTS' AND type(r) <> 'QUOTES' " +
            "RETURN p, r, neighbors")
    List<Paint> findAllPaintByPids(List<Long> paintIds);
}
