package com.ister.controllers;


import com.ister.model.User;
import com.ister.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class UserController {
    private final AuthorService authorService;

    public UserController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/add")
    public ResponseEntity<User> createAuthor(@RequestBody User authorBody) {
        try {
            User author = new User();
            author.setId(UUID.randomUUID().toString());
            author.setName(authorBody.getName());
            author.setPassword(authorBody.getPassword());
            author.setRole(authorBody.getRole());

            if (authorService.add(author))
                return new ResponseEntity<>(author, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editAuthor(@RequestBody User authorBody) {
        try {
            User author = new User();
            author.setId(authorBody.getId());
            author.setName(authorBody.getName());
            author.setPassword(authorBody.getPassword());
            author.setRole(authorBody.getRole());

            if (authorService.edit(author))
                return new ResponseEntity<>("Updated", HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAuthor(@RequestParam String id) {
        try {
            if (authorService.delete(id))
                return new ResponseEntity<>("Success", HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAuthor(@RequestParam(defaultValue = "") List<String> id) {
        try {
            if (id.size() == 0) {
                return new ResponseEntity<>(authorService.getAll(), HttpStatus.OK);
            } else {
                List<User> response = new ArrayList<>();
                User author;

                for (String item : id) {
                    author = authorService.getById(item);
                    if (author != null)
                        response.add(author);
                    else
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                    return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
