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
    public ResponseEntity<User> createUser(@RequestBody User userBody) {
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
    public ResponseEntity<String> editUser(@RequestBody User userBody) {
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
    public ResponseEntity<String> deleteUser(@RequestParam String id) {
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
    public ResponseEntity<List<User>> getUser(@RequestParam(defaultValue = "") List<String> id) {
        try {
            if (id.size() == 0) {
                return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
            } else {
                List<User> response = new ArrayList<>();
                User user;

                for (String item : id) {
                    user = userService.getById(item);
                    if (user != null)
                        response.add(user);
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
