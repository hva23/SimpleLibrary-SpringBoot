package com.ister.config.security;


import com.ister.common.Roles;
import com.ister.filters.JwtRequestFilter;
import jakarta.annotation.Resource;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    //Uncomment this if you are using jdbcAuthentication
    /*
    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    */

    //Uncomment this if you are using implementation of UserDetailsService and UserDetails
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth
                                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                                //Home page and /* GET methods request doesn't need authentication (just for practicing)
                                .requestMatchers(HttpMethod.GET,"/*").permitAll()
                                //Allow users to authenticate
                                .requestMatchers(HttpMethod.POST, "/authors/authenticate").permitAll()
                                //Only admins can access /authors/* URL
                                .requestMatchers("/authors/*").hasAuthority(Roles.ROLE_ADMIN.toString())
                                //Only admins and authors can access /books/* URL
                                .requestMatchers("/books/*").hasAnyAuthority(Roles.ROLE_ADMIN.toString(), Roles.ROLE_AUTHOR.toString())
                                //Other pages need authenticating
                                //Currently at this state, we don't have any other pages
                                .anyRequest().authenticated()
                        //Retrieve user and password from this.configure(AuthenticationManagerBuilder)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);  //Disable CSRF because of using postman to make requests
        return http.build();
    }


    //You can add some authenticated user here
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Use this authorization for users which are in memory
        /*
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                .roles("ADMIN")
                .and()
                .withUser("author")
                .password("author")
                .roles("AUTHOR");
        */

        //Add these methods to generate automatically tables for authorization
        /*
                .withDefaultSchema()
                .withUser(
                        User.withUsername("user1")
                                .password("pass")
                                .roles("USER")
                )
                .withUser(
                        User.withUsername("admin1")
                                .password("pass")
                                .roles("ADMIN")
                );
        */

        //Use this if you want to add authorization based on your database, and you are not using UserDetailsService
        /*
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT NAME, PASSWORD, ENABLED FROM USERS WHERE NAME = ?")
                .authoritiesByUsernameQuery("SELECT NAME, ROLE FROM USERS WHERE NAME = ?");
        */

        //Use this if you have UserDetails and UserDetailsService implementation
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
