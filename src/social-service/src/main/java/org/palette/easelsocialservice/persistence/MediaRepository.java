package org.palette.easelsocialservice.persistence;

import org.palette.easelsocialservice.dto.request.MediaRequest;
import org.palette.easelsocialservice.persistence.domain.Media;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MediaRepository extends Neo4jRepository<Media, String> {
    @Query("UNWIND $medias AS media CREATE (m:Media {path: media.path, type: media.type}) RETURN m")
    List<Media> saveAllRequests(@Param("medias") List<MediaRequest> medias);
}
