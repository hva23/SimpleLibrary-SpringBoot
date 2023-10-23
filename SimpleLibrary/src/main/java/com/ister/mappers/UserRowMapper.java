package com.ister.mappers;

import com.ister.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User author = new User();
        author.setId(rs.getString("ID"));
        author.setName(rs.getString("NAME"));
        author.setPassword(rs.getString("PASSWORD"));
        author.setRole(rs.getString("ROLE"));
        return author;
    }
}
