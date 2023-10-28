package com.ister.config;


import com.ister.filters.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<UserFilter> registerFilter() {
        FilterRegistrationBean<UserFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        UserFilter userFilter = new UserFilter();

        filterRegistrationBean.setFilter(userFilter);
        filterRegistrationBean.addUrlPatterns("/authors");
        filterRegistrationBean.setOrder(4);
        return filterRegistrationBean;
    }

}