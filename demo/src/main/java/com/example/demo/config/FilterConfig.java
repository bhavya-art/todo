package com.example.demo.config;

import com.example.demo.filter.UsernameFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

//    @Bean
//    public FilterRegistrationBean<UsernameFilter> usernameFilterRegistration() {
//        FilterRegistrationBean<UsernameFilter> registrationBean = new FilterRegistrationBean<>();
//
//        registrationBean.setFilter(new UsernameFilter());
//        registrationBean.addUrlPatterns("/api/v1/tasks/*");  // Apply filter to specific URL patterns as needed
//
//        return registrationBean;
//    }
}
