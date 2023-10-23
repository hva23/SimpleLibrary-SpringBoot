package com.ister.mappers;

import com.ister.model.Author;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorRowMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        Author author = new Author();
        author.setId(rs.getString("ID"));
        author.setName(rs.getString("NAME"));
        author.setPassword(rs.getString("PASSWORD"));
        return author;
    }
}
