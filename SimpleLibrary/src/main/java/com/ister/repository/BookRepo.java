package com.ister.repository;

import com.ister.mappers.BookRowMapper;
import com.ister.mappers.BookRowMapper;
import com.ister.model.Book;
import com.ister.model.Book;
import com.ister.service.QueryBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class BookRepo implements BaseRepo<Book, Long> {

    private final String TABLE_NAME = "BOOKS";

    JdbcTemplate jdbcTemplate;
    TransactionTemplate transactionTemplate;
    QueryBuilder queryBuilder = new QueryBuilder();

    public BookRepo(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public boolean create(Book book) {
        return Boolean.TRUE.equals(transactionTemplate.execute(transactionStatus -> {
            Object[] values = new Object[]{
                    book.getId(),
                    book.getName(),
                    book.getAuthorId()
            };
            String sql = queryBuilder.create(TABLE_NAME, values);
            return jdbcTemplate.update(sql) > 0;
        }));
    }

    @Override
    public boolean update(Book book) {
        return Boolean.TRUE.equals(transactionTemplate.execute(tranactionStatus -> {
            Map<String, Object> values = new HashMap<>();
            values.put("NAME", book.getName());
            values.put("AUTHOR_ID", book.getAuthorId());

            Map<String, Object> condition = new HashMap<>();
            condition.put("ID", book.getId());

            String sql = queryBuilder.update(TABLE_NAME, values, condition);
            return jdbcTemplate.update(sql) > 0;
        }));
    }

    @Override
    public boolean delete(Long id) {
        return Boolean.TRUE.equals(transactionTemplate.execute(tranactionStatus -> {
            Map<String, Object> condition = new HashMap<>();
            condition.put("ID", id);

            String sql = queryBuilder.delete(TABLE_NAME, condition);
            return jdbcTemplate.update(sql) > 0;
        }));
    }

    @Override
    public List<Book> findAll() {
        return transactionTemplate.execute(transactionStatus -> {
            String sql = queryBuilder.read(TABLE_NAME, null, null);
            return jdbcTemplate.query(sql, new BookRowMapper());
        });
    }

    @Override
    public Book findById(Long id) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("ID", id);
        return Objects.requireNonNull(transactionTemplate.execute(transactionStatus -> {
            String sql = queryBuilder.read(TABLE_NAME, null, condition);
            return jdbcTemplate.query(sql, new BookRowMapper());
        })).get(0);
    }
}
