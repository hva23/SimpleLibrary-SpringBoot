package com.ister.repository;


import com.ister.model.Author;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@EnableAutoConfiguration
@Repository
public interface BaseRepo<T, PK> {
    public boolean create(T entity);
    public boolean update(T entity);
    public boolean delete(PK id);
    public List<T> findAll();
    public T findById(PK id);

}
