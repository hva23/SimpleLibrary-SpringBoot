package com.ister.controllers;


import com.ister.config.security.UserDetailsServiceImpl;
import com.ister.model.User;
import com.ister.service.UserService;
import com.ister.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class UserController {
    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody User userBody) {
        final User user = (User) userDetailsService.loadUserByUsername(userBody.getUsername());
        if (!user.getPassword().equals(userBody.getPassword()))
            return new ResponseEntity<>("Incorrect password or username", HttpStatus.FORBIDDEN);
        final String jwt = jwtUtil.generateToken(user);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
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
                return new ResponseEntity<>("Deleted", HttpStatus.OK);
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
