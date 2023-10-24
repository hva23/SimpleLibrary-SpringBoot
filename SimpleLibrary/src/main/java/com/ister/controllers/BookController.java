package com.ister.controllers;


import com.ister.model.Book;
import com.ister.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public ResponseEntity<Book> createBook(@RequestBody Book bookBody) {
        try {
            Book book = new Book();
            book.setId(bookBody.getId());
            book.setName(bookBody.getName());
            book.setAuthorId(bookBody.getAuthorId());

            if (bookService.add(book))
                return new ResponseEntity<>(book, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editBook(@RequestBody Book bookBody) {
        try {
            Book book = new Book();
            book.setId(bookBody.getId());
            book.setName(bookBody.getName());
            book.setAuthorId(bookBody.getAuthorId());

            if (bookService.edit(book))
                return new ResponseEntity<>("Updated", HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteBook(@RequestParam Long id) {
        try {
            if (bookService.delete(id))
                return new ResponseEntity<>("Success", HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBook(@RequestParam(defaultValue = "") List<Object> id) {
        try {
            //Get all books
            if (id.size() == 0) {
                return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);

            } else {
                //Get type of id(Long or String) -> if Long call findById otherwise call findByAuthorId
                List<Book> response;
                if (id.get(0) instanceof Long) {
                    //Get multiple or just a single book
                    response = new ArrayList<>();
                    Book book;

                    for (Object item : id) {
                        book = bookService.getById((Long) item);
                        if (book != null)
                            response.add(book);
                        else
                            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else {
                    response = bookService.getByAuthorId(id.get(0).toString());
                    if (response == null)
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
