package com.example.demo.filter;

import com.example.demo.common.exception.ToDoException;
import com.example.demo.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.stream.Stream;

@Component
public class UsernameFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri = request.getRequestURI();
        Stream<String> stream = Stream.of("/api/v1/users");
        return stream.anyMatch(uri::contains);
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String username = httpRequest.getHeader("username");
        try {
            if (username == null || !userService.isValidUsername(username)) {
                throw new ToDoException(
                        HttpStatus.UNAUTHORIZED, "USER_UNAUTHORISED", "You are not authorized to access this page");
            }
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
            return;
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
