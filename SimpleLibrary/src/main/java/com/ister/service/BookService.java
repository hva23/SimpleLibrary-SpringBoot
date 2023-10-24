package com.ister.service;

import com.ister.model.Book;
import com.ister.repository.jdbc.template.BookJdbcTemplateRepo;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService {
    private final BookJdbcTemplateRepo repository;

    public BookService(BookJdbcTemplateRepo repo) {
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
    public List<Book> getByAuthorId(String id){
        return repository.findByAuthorId(id);
    }
}
