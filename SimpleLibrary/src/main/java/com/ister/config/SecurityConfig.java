package com.ister.config;


import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth
                                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                                //Home page doesn't need authenticating
                                .requestMatchers("/*").permitAll()
                                //Only admins can access /authors/* URL
                                .requestMatchers("/authors/*").hasRole("ADMIN")
                                //Only admins and authors can access /books/* URL
                                .requestMatchers("/books/*").hasAnyRole("ADMIN", "AUTHOR")
                                //Other pages need authenticating
                                .anyRequest().authenticated()
                        //Retrieve user and password from application.properties for authenticating
                        //Retrieve user and password from this.configure(AuthenticationManagerBuilder)
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);  //Disable CSRF because of using postman to make requests
        return http.build();
    }


    //You can add some authenticated user here
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                .roles("ADMIN")
                .and()
                .withUser("author")
                .password("author")
                .roles("AUTHOR");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
