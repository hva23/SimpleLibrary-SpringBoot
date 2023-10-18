package com.ister.repository;

import com.ister.mappers.AuthorRowMapper;
import com.ister.model.Author;
import com.ister.service.QueryBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class AuthorRepo implements BaseRepo<Author, String> {

    private final String TABLE_NAME = "AUTHORS";
    JdbcTemplate jdbcTemplate;
    TransactionTemplate transactionTemplate;

    QueryBuilder queryBuilder = new QueryBuilder();

    public AuthorRepo(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public boolean create(Author author) {
        return Boolean.TRUE.equals(transactionTemplate.execute(transactionStatus -> {
            Object[] values = new Object[]{
                    author.getId(),
                    author.getName()
            };
            String sql = queryBuilder.create(TABLE_NAME, values);
            return jdbcTemplate.update(sql) > 0;
        }));
    }

    @Override
    public boolean update(Author author) {
        return Boolean.TRUE.equals(transactionTemplate.execute(tranactionStatus -> {
            Map<String, Object> values = new HashMap<>();
            values.put("NAME", author.getName());

            Map<String, Object> condition = new HashMap<>();
            condition.put("ID", author.getId());

            String sql = queryBuilder.update(TABLE_NAME, values, condition);
            return jdbcTemplate.update(sql) > 0;
        }));
    }

    @Override
    public boolean delete(String id) {
        return Boolean.TRUE.equals(transactionTemplate.execute(tranactionStatus -> {
            Map<String, Object> condition = new HashMap<>();
            condition.put("ID", id);

            String sql = queryBuilder.delete(TABLE_NAME, condition);
            return jdbcTemplate.update(sql) > 0;
        }));
    }

    @Override
    public List<Author> findAll() {
        return transactionTemplate.execute(transactionStatus -> {
           String sql = queryBuilder.read(TABLE_NAME, null, null);
           return jdbcTemplate.query(sql, new AuthorRowMapper());
        });
    }

    @Override
    public Author findById(String id) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("ID", id);
        return Objects.requireNonNull(transactionTemplate.execute(transactionStatus -> {
            String sql = queryBuilder.read(TABLE_NAME, null, condition);
            return jdbcTemplate.query(sql, new AuthorRowMapper());
        })).get(0);
    }
}
