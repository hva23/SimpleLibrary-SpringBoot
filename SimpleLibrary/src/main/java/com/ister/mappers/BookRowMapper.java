package com.ister.mappers;

import com.ister.model.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("ID"));
        book.setName(rs.getString("NAME"));
        book.setAuthorId(rs.getString("AUTHOR_ID"));
        return book;
    }
}
