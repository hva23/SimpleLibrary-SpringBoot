package com.ister.service;

import com.ister.common.Roles;
import com.ister.model.User;
import com.ister.repository.jdbc.template.UserJdbcTemplateRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserJdbcTemplateRepo repository;

    public UserService(UserJdbcTemplateRepo repo) {
        this.repository = repo;
    }

    public boolean add(User user) {
        String role = user.getRole();
        //If user role isn't any of allowed return false otherwise do the creation
        if (!role.contentEquals(Roles.ROLE_ADMIN.toString()))
            if (!role.contentEquals(Roles.ROLE_AUTHOR.toString()))
                if (!role.contentEquals(Roles.ROLE_USER.toString())) return false;

        return repository.create(user);
    }

    public boolean edit(User user) {
        String role = user.getRole();
        //If user role isn't any of allowed return false otherwise do the updating
        if (!role.contentEquals(Roles.ROLE_ADMIN.toString()))
            if (!role.contentEquals(Roles.ROLE_AUTHOR.toString()))
                if (!role.contentEquals(Roles.ROLE_USER.toString())) return false;

        return repository.update(user);
    }

    public boolean delete(String id) {
        return repository.delete(id);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User getById(String id) {
        return repository.findById(id);
    }
}
