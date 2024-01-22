package org.palette.easelsocialservice.persistence;

import org.palette.easelsocialservice.dto.request.MediaRequest;
import org.palette.easelsocialservice.persistence.domain.Media;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MediaRepository extends Neo4jRepository<Media, String> {
}
