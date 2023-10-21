package com.ister.config;


import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth
                                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                                .requestMatchers("/").permitAll()   //Home page doesn't need authenticating
                                .anyRequest().authenticated()         //Other pages need authenticating
                        //Retrieve user and password from application.properties for authenticating
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
