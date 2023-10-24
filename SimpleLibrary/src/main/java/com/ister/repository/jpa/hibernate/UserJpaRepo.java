package com.ister.repository.jpa.hibernate;

import com.ister.model.User;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepo extends JpaRepository<User, String> {
    Optional<User> findByName(String name);

}
