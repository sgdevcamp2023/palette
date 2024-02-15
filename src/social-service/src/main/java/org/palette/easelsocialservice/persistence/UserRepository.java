package org.palette.easelsocialservice.persistence;

import org.palette.easelsocialservice.persistence.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Neo4jRepository<User, Long> {
    Optional<User> findByUid(Long userId);

    @Query("MATCH (u:User) WHERE u.uid IN $uids RETURN COUNT(u) = size($uids)")
    boolean existsByAllUidsIn(@Param("uids") List<Long> uids);

    @Query("MATCH (u:User) WHERE u.uid IN $uids RETURN u")
    List<User> findAllByUids(List<Long> uids);

    boolean existsByUid(Long uid);

    @Query("MATCH (:User { uid: $uid }) - [r:LIKES] -> (:Paint { pid: $pid }) DELETE r")
    void deleteLikeById(@Param("uid") Long uid, @Param("pid") Long pid);

    @Query("MATCH (:User { uid: $uid })-[r:FOLLOWS]->() RETURN count(r)")
    int countFollowings(@Param("uid") Long uid);

    @Query("MATCH (:User { uid: $uid })<-[r:FOLLOWS]-() RETURN count(r)")
    int countFollowers(@Param("uid") Long uid);

    @Query("MATCH (:User { uid: $uid }) - [r:MARKS] -> (:Paint { pid: $pid }) DELETE r")
    void deleteMarkById(@Param("uid") Long uid, @Param("pid") Long pid);

    @Query("MATCH (u:User) -[:LIKES]-> (:Paint { pid: $pid }) RETURN u")
    List<User> findLikedByPaintId(@Param("pid") Long pid);

    @Query("RETURN EXISTS((:User {uid: $uid}) -[:LIKES]-> (:Paint {pid: $pid}))")
    boolean existsLikesByUidAndPid(@Param("uid") Long uid, @Param("pid") Long pid);

    @Query("MATCH (u:User) -[:REPAINTS]-> (:Paint { pid: $pid }) RETURN u")
    List<User> findRepaintedByPaintId(@Param("pid") Long pid);

    @Query("MATCH (:User {uid: $uid})<-[:FOLLOWS]-(u:User) RETURN u.uid")
    List<Long> findFollowerUidsByUid(@Param("uid") Long uid);
}
