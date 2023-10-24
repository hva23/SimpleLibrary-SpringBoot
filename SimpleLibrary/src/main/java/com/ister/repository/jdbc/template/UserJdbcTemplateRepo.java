package com.ister.repository.jdbc.template;

import com.ister.mappers.UserRowMapper;
import com.ister.model.User;
import com.ister.service.QueryBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

@Repository
public class UserJdbcTemplateRepo implements BaseJdbcTemplateRepo<User, String> {

    private final String TABLE_NAME = "USERS";
    JdbcTemplate jdbcTemplate;
    TransactionTemplate transactionTemplate;

    QueryBuilder queryBuilder = new QueryBuilder();

    public UserJdbcTemplateRepo(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public User save(User user) {
        String id = user.getId();
        boolean result;
        boolean isPresent = findById(id).isPresent();

        if (isPresent) {
            result = update(user);
        } else {
            result = create(user);
        }

        return result ? user : null;
    }


    @Override
    public void deleteById(String id) {
        transactionTemplate.execute(transaction -> {
            Map<String, Object> condition = new HashMap<>();
            condition.put("ID", id);

            String sql = queryBuilder.delete(TABLE_NAME, condition);
            return jdbcTemplate.update(sql);
        });
    }

    @Override
    public List<User> findAll() {
        return transactionTemplate.execute(transaction -> {
            String sql = queryBuilder.read(TABLE_NAME, null, null);
            return jdbcTemplate.query(sql, new UserRowMapper());
        });
    }

    @Override
    public Optional<User> findById(String id) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("ID", id);

        return getUser(condition);
    }

    @Override
    public Optional<User> findByName(String name) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("NAME", name);

        return getUser(condition);
    }

    /* Private Methods */
    boolean create(User user) {
        return Boolean.TRUE.equals(transactionTemplate.execute(transaction -> {
            Object[] values = new Object[]{
                    user.getId(),
                    user.getName(),
                    user.getPassword(),
                    user.getRole(),
                    "1"
            };
            String sql = queryBuilder.create(TABLE_NAME, values);
            return jdbcTemplate.update(sql) > 0;
        }));
    }

    boolean update(User user) {
        return Boolean.TRUE.equals(transactionTemplate.execute(transaction -> {
            Map<String, Object> values = new HashMap<>();
            values.put("NAME", user.getName());

            Map<String, Object> condition = new HashMap<>();
            condition.put("ID", user.getId());

            String sql = queryBuilder.update(TABLE_NAME, values, condition);
            return jdbcTemplate.update(sql) > 0;
        }));
    }

    Optional<User> getUser(Map<String, Object> condition) {
        List<User> userList = transactionTemplate.execute(transaction -> {
            String sql = queryBuilder.read(TABLE_NAME, null, condition);
            return jdbcTemplate.query(sql, new UserRowMapper());
        });
        assert userList != null;
        if(userList.size() == 0) return Optional.empty();
        return Optional.of(userList.get(0));
    }

}
