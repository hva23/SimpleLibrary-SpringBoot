package com.ister.repository.jdbc.template;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableAutoConfiguration
@Repository
public interface BaseJdbcTemplateRepo<T, PK> {
    public boolean create(T entity);
    public boolean update(T entity);
    public boolean delete(PK id);
    public List<T> findAll();
    public T findById(PK id);

}
