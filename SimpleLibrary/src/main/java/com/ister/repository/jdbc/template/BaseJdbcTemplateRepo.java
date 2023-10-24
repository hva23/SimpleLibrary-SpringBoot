package com.ister.repository.jdbc.template;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableAutoConfiguration
@Repository
public interface BaseJdbcTemplateRepo<T, PK> {
    T save(T entity);
    void deleteById(PK id);
    List<T> findAll();
    Optional<T> findById(PK id);
    Optional<T> findByName(String name);

}
