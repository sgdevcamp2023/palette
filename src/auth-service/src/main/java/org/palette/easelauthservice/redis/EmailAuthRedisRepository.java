package org.palette.easelauthservice.redis;

import org.springframework.data.repository.CrudRepository;

public interface EmailAuthRedisRepository extends CrudRepository<EmailAuth, String> {
}
