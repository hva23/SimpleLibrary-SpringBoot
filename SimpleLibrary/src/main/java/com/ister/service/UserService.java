package com.ister.service;

import com.ister.common.Roles;
import com.ister.model.User;
import com.ister.repository.jdbc.template.UserJdbcTemplateRepo;
import com.ister.repository.jpa.hibernate.UserJpaRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    //UserJpaRepo
    //UserJdbcTemplateRepo
    private final UserJpaRepo repository;

    public UserService(UserJpaRepo repo) {
        this.repository = repo;
    }

    public boolean add(User user) {
        if (repository.findByName(user.getName()).isPresent())
            return false;
        else {
            Roles role = user.getRole();
            //If user role isn't any of allowed return false otherwise do the creation
            if (role != Roles.ROLE_ADMIN)
                if (role != Roles.ROLE_AUTHOR)
                    if (role != Roles.ROLE_USER) return false;

            user.setId(UUID.randomUUID().toString());
            return repository.save(user) != null;
        }
    }

    public boolean edit(User user) {
        if (repository.findById(user.getId()).isPresent()) {
            Roles role = user.getRole();
            //If user role isn't any of allowed return false otherwise do the updating
            if (role != Roles.ROLE_ADMIN)
                if (role != Roles.ROLE_AUTHOR)
                    if (role != Roles.ROLE_USER) return false;

            return repository.save(user) != null;
        } else
            return false;
    }

    public boolean delete(String id) {
        repository.deleteById(id);
        return repository.findById(id).isEmpty();
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User getById(String id) {
        return repository.findById(id).orElse(null);
    }
}
