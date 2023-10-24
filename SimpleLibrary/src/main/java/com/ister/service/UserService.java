package com.ister.service;

import com.ister.common.Roles;
import com.ister.model.User;
import com.ister.repository.jdbc.template.UserJdbcTemplateRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserJdbcTemplateRepo repository;

    public UserService(UserJdbcTemplateRepo repo) {
        this.repository = repo;
    }

    public boolean add(User user) {
        if (repository.findByName(user.getName()).isPresent())
            return false;
        else {
            String role = user.getRole();
            //If user role isn't any of allowed return false otherwise do the creation
            if (!role.contentEquals(Roles.ROLE_ADMIN.toString()))
                if (!role.contentEquals(Roles.ROLE_AUTHOR.toString()))
                    if (!role.contentEquals(Roles.ROLE_USER.toString())) return false;

            user.setId(UUID.randomUUID().toString());
            return repository.save(user) != null;
        }
    }

    public boolean edit(User user) {
        if (repository.findById(user.getId()).isPresent()) {
            String role = user.getRole();
            //If user role isn't any of allowed return false otherwise do the updating
            if (!role.contentEquals(Roles.ROLE_ADMIN.toString()))
                if (!role.contentEquals(Roles.ROLE_AUTHOR.toString()))
                    if (!role.contentEquals(Roles.ROLE_USER.toString())) return false;

            return repository.save(user) != null;
        } else
            return false;
    }

    public boolean delete(String id) {
        repository.deleteById(id);
        return repository.findById(id).isPresent();
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User getById(String id) {
        return repository.findById(id).orElse(null);
    }
}
