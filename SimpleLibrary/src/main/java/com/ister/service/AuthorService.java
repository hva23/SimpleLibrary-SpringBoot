package com.ister.service;

import com.ister.model.Author;
import com.ister.repository.AuthorRepo;
import com.ister.repository.BaseRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepo repository;

    public AuthorService(AuthorRepo repo) {
        this.repository = repo;
    }

    public boolean add(Author author) {
        return repository.create(author);
    }
    public boolean edit(Author author) {
        return repository.update(author);
    }

    public boolean delete(String id) {
        return repository.delete(id);
    }

    public List<Author> getAll() {
        return repository.findAll();
    }

    public Author getById(String id) {
        return repository.findById(id);
    }
}
