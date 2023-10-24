package com.ister.repository.jdbc.template;

import com.ister.mappers.BookRowMapper;
import com.ister.model.Book;
import com.ister.service.QueryBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

@Repository
public class BookJdbcTemplateRepo implements BaseJdbcTemplateRepo<Book, Long> {

    private final String TABLE_NAME = "BOOKS";

    JdbcTemplate jdbcTemplate;

    TransactionTemplate transactionTemplate;
    QueryBuilder queryBuilder = new QueryBuilder();

    public BookJdbcTemplateRepo(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public Book save(Book book) {
        Long id = book.getId();
        boolean result;
        boolean isPresent = findById(id).isPresent();

        if (isPresent) {
            result = update(book);
        } else {
            result = create(book);
        }

        return result ? book : null;
    }

    @Override
    public void deleteById(Long id) {
        transactionTemplate.execute(tranaction -> {
            Map<String, Object> condition = new HashMap<>();
            condition.put("ID", id);

            String sql = queryBuilder.delete(TABLE_NAME, condition);
            return jdbcTemplate.update(sql);
        });
    }

    @Override
    public List<Book> findAll() {
        return transactionTemplate.execute(tranaction -> {
            String sql = queryBuilder.read(TABLE_NAME, null, null);
            return jdbcTemplate.query(sql, new BookRowMapper());
        });
    }

    @Override
    public Optional<Book> findById(Long id) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("ID", id);

        return getBook(condition);
    }

    @Override
    public Optional<Book> findByName(String name) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("NAME", name);

        return getBook(condition);
    }

    public List<Book> findByAuthorId(String id) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("AUTHOR_ID", id);
        return transactionTemplate.execute(tranaction -> {
            String sql = queryBuilder.read(TABLE_NAME, null, condition);
            return jdbcTemplate.query(sql, new BookRowMapper());
        });
    }

    public Long getLastId() {
        List<Book> userList = transactionTemplate.execute(transaction -> {
            String sql = queryBuilder.readLast(TABLE_NAME, null);
            return jdbcTemplate.query(sql, new BookRowMapper());
        });
        assert userList != null;
        if (userList.size() == 0) return -1L;
        return userList.get(0).getId();
    }


    /* Private Methods */
    private boolean create(Book book) {
        return Boolean.TRUE.equals(transactionTemplate.execute(tranaction -> {
            Object[] values = new Object[]{
                    book.getId(),
                    book.getName(),
                    book.getAuthorId()
            };
            String sql = queryBuilder.create(TABLE_NAME, values);
            return jdbcTemplate.update(sql) > 0;
        }));
    }

    private boolean update(Book book) {
        return Boolean.TRUE.equals(transactionTemplate.execute(tranaction -> {
            Map<String, Object> values = new HashMap<>();
            values.put("NAME", book.getName());
            values.put("AUTHOR_ID", book.getAuthorId());

            Map<String, Object> condition = new HashMap<>();
            condition.put("ID", book.getId());

            String sql = queryBuilder.update(TABLE_NAME, values, condition);
            return jdbcTemplate.update(sql) > 0;
        }));
    }

    Optional<Book> getBook(Map<String, Object> condition) {
        List<Book> userList = transactionTemplate.execute(transaction -> {
            String sql = queryBuilder.read(TABLE_NAME, null, condition);
            return jdbcTemplate.query(sql, new BookRowMapper());
        });
        assert userList != null;
        if (userList.size() == 0) return Optional.empty();
        return Optional.of(userList.get(0));
    }

}
