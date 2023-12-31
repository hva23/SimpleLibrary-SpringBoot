package com.ister.service;

import com.ister.model.Book;
import com.ister.repository.jdbc.template.BookJdbcTemplateRepo;
import com.ister.repository.jpa.hibernate.BookJpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService {

    private final BookJpaRepo repository;

    public BookService(BookJpaRepo repo) {
        this.repository = repo;
    }

    public boolean add(Book book) {
        //If using JpaRepository comment until next comment
//        if(book.getId() == null)
//        {
//            book.setId(repository.getLastId() + 1);
//        }
        //until here


        //If using JdbcTemplate comment until next comment
        book.setId(repository.findTopByOrderByIdDesc().getId() + 1);
        //Until here

        if(repository.findByName(book.getName()).isPresent())
            return false;
        else
            return repository.save(book) != null;
    }
    public boolean edit(Book book) {
        if(repository.findById(book.getId()).isPresent())
            return repository.save(book) != null;
        else
            return false;
    }

    public boolean delete(Long id) {
        repository.deleteById(id);
        return repository.findById(id).isEmpty();
    }

    public List<Book> getAll() {
        return repository.findAll();
    }

    public Book getById(Long id) {
        return repository.findById(id).orElse(null);
    }
    public List<Book> getByAuthorId(String id){
        return repository.findByAuthorId(id);
    }
}
