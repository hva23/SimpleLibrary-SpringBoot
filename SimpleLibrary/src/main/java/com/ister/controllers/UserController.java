package com.ister.controllers;


import com.ister.model.User;
import com.ister.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<User> createAuthor(@RequestBody User userBody) {
        try {
            User user = new User();
            user.setName(userBody.getName());
            user.setPassword(userBody.getPassword());
            user.setRole(userBody.getRole());

            if (userService.add(user))
                return new ResponseEntity<>(user, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editAuthor(@RequestBody User userBody) {
        try {
            User user = new User();
            user.setId(userBody.getId());
            user.setName(userBody.getName());
            user.setPassword(userBody.getPassword());
            user.setRole(userBody.getRole());

            if (userService.edit(user))
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
            if (userService.delete(id))
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
                return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
            } else {
                List<User> response = new ArrayList<>();
                User author;

                for (String item : id) {
                    author = userService.getById(item);
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
