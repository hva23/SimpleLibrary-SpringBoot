package com.ister.service;

import com.ister.model.Author;
import com.ister.model.Book;
import com.ister.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;


@Service
public class BookService {
    private final BookRepo repository;

    public BookService(BookRepo repo) {
        this.repository = repo;
    }

    public boolean add(Book book) {
        return repository.create(book);
    }
    public boolean edit(Book book) {
        return repository.update(book);
    }

    public boolean delete(Long id) {
        return repository.delete(id);
    }

    public List<Book> getAll() {
        return repository.findAll();
    }

    public Book getById(Long id) {
        return repository.findById(id);
    }
}
