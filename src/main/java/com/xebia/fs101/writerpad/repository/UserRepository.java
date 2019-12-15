package com.xebia.fs101.writerpad.repository;

import com.xebia.fs101.writerpad.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);
}
