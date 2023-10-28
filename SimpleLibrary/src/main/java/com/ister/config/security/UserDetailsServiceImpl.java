package com.ister.config.security;

import com.ister.model.User;
import com.ister.repository.jpa.hibernate.UserJpaRepo;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserJpaRepo repository;

    public UserDetailsServiceImpl(UserJpaRepo repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Doing something else in UserDetailsServiceImpl");
        return repository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));
    }
}
