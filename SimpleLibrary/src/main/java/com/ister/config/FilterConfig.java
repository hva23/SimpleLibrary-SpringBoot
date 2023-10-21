package com.ister.config;


import com.ister.filters.AuthorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthorsFilter> registerFilter() {
        FilterRegistrationBean<AuthorsFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        AuthorsFilter authorsFilter = new AuthorsFilter();

        filterRegistrationBean.setFilter(authorsFilter);
        filterRegistrationBean.addUrlPatterns("/authors");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

}