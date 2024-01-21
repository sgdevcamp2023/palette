package org.palette.easeluserservice.persistence;

import org.palette.easeluserservice.persistence.embed.Email;
import org.palette.easeluserservice.persistence.embed.Username;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(Email email);

    Boolean existsByUsername(Username username);

    Optional<User> findByEmail(Email email);
}
